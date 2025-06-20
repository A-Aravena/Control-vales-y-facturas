package com.transporte.cl.service.impl;

import com.transporte.cl.models.Inva;
import com.transporte.cl.repository.InvaRepository;
import com.transporte.cl.service.InvaService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class InvaServiceImpl implements InvaService {

    @Autowired
    private InvaRepository invaRepository;

    @Override
    public List<Inva> uploadFromExcel(MultipartFile file) throws IOException {
        List<Inva> invas = new ArrayList<>();

        try (InputStream inputStream = file.getInputStream()){
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);

            for(Row row :sheet){
                if(row.getRowNum()==0) continue;

                String Estado = getStringCellValue(row.getCell(0));
                String DT = getStringCellValue(row.getCell(1));

                if (DT != null && DT.length() < 8) {
                    int cerosFaltantes = 8 - DT.length();
                    StringBuilder ceros = new StringBuilder();
                    for (int i = 0; i < cerosFaltantes; i++) {
                        ceros.append("0");
                    }
                    DT = DT + ceros.toString();
                }

                String NroEntrega = (getStringCellValue(row.getCell(2)) != null) ? getStringCellValue(row.getCell(2)).replace("E8", "").replace(".", "") : null;
                String NroLegal = (getStringCellValue(row.getCell(3)) != null) ? getStringCellValue(row.getCell(3)).replace("E11", "").replace(".", "") : null;
                String NroFactura = (getStringCellValue(row.getCell(4)) != null) ? getStringCellValue(row.getCell(4)).replace("E9", "").replace(".", "") : null;
                String FaFatReal = getStringCellValue(row.getCell(5));
                String ConfPOD = getStringCellValue(row.getCell(6));
                String Lote = getStringCellValue(row.getCell(7));
                String Transportista = getStringCellValue(row.getCell(8));
                String CtaProv = getStringCellValue(row.getCell(9));
                String QFact = (getStringCellValue(row.getCell(10)) != null) ? getStringCellValue(row.getCell(10)).replace(".0", "") : null;
                String VNFact = (getStringCellValue(row.getCell(11)) != null) ? getStringCellValue(row.getCell(11)).replace(".0", "") : null;
                String DestMerc = (getStringCellValue(row.getCell(12)) != null) ? getStringCellValue(row.getCell(12)).replace("E7", "").replace(".", "") : null;
                String DescSolicitante = getStringCellValue(row.getCell(13));
                String Poblacion = getStringCellValue(row.getCell(14));
                String Region = getStringCellValue(row.getCell(15));
                String CondPago = getStringCellValue(row.getCell(16));
                String OVta = getStringCellValue(row.getCell(17));
                String ConDiferencias = getStringCellValue(row.getCell(18));



                Inva inva =  new Inva();
                inva.setEstado(Estado);
                inva.setDt(DT);
                inva.setNroEntrega(NroEntrega);
                inva.setNroLegal(NroLegal);
                inva.setNroFactura(NroFactura);
                inva.setFaFatReal(FaFatReal);
                inva.setConfPOD(ConfPOD);
                inva.setLote(Lote);
                inva.setTransportista(Transportista);
                inva.setCtaProv(CtaProv);
                inva.setQFact(QFact);
                inva.setVNFact(VNFact);
                inva.setDestMerc(DestMerc);
                inva.setDescSolicitante(DescSolicitante);
                inva.setPoblacion(Poblacion);
                inva.setRegion(Region);
                inva.setCondPago(CondPago);
                inva.setOVta(OVta);
                inva.setConDiferencias(ConDiferencias);
                inva.setEstadofinal("No Presentado");

                Inva existNroLegal = invaRepository.findByNroLegal(NroLegal);

                if(existNroLegal != null){
                    existNroLegal.setEstado(inva.getEstado());
                    existNroLegal.setDt(inva.getDt());
                    existNroLegal.setNroEntrega(inva.getNroEntrega());
                    existNroLegal.setNroLegal(inva.getNroLegal());
                    existNroLegal.setNroFactura(inva.getNroFactura());
                    existNroLegal.setFaFatReal(inva.getFaFatReal());
                    existNroLegal.setConfPOD(inva.getConfPOD());
                    existNroLegal.setLote(inva.getLote());
                    existNroLegal.setTransportista(inva.getTransportista());
                    existNroLegal.setCtaProv(inva.getCtaProv());
                    existNroLegal.setQFact(inva.getQFact());
                    existNroLegal.setVNFact(inva.getVNFact());
                    existNroLegal.setDestMerc(inva.getDestMerc());
                    existNroLegal.setDescSolicitante(inva.getDescSolicitante());
                    existNroLegal.setPoblacion(inva.getPoblacion());
                    existNroLegal.setRegion(inva.getRegion());
                    existNroLegal.setCondPago(inva.getCondPago());
                    existNroLegal.setOVta(inva.getOVta());
                    existNroLegal.setConDiferencias(inva.getConDiferencias());

                    invaRepository.save(existNroLegal);
                }else{

                    invas.add(inva);
                }
            }
        }catch (IOException e){
            throw new IOException("Error: ", e);
        }
        invaRepository.saveAll(invas);
        return invas;
    }

    @Override
    public List<Inva> getAll() {
        return invaRepository.findAll(Sort.by(Sort.Direction.ASC,"id"));
    }

    @Override
    public Inva actualizarPOD(Long id, String ConfPOD) {
        Inva inva = invaRepository.findById(id).orElse(null);
        if(inva !=null){
            inva.setConfPOD(ConfPOD);
            return  invaRepository.save(inva);
        }
        return null;
    }

    @Override
    public Inva updateEstado(Long id, String Estado) {

        Inva inva = invaRepository.findById(id).orElse(null);
        if(inva !=null){
            switch (Estado){
                case "Incompleto":
                    inva.setEstado("Incompleto");
                    break;
                case "Por Conformar":
                    inva.setEstado("Por Conformar");
                    break;
                case "Conformado":
                    inva.setEstado("Conformado");
                    break;
            }

            inva.setEstado(Estado);
            return invaRepository.save(inva);
        }
        return null;
    }

    private String getStringCellValue(Cell cell) {
        if (cell == null) {
            return null;
        }
        if (cell.getCellType() == CellType.STRING) {
            return cell.getStringCellValue();
        } else if (cell.getCellType() == CellType.NUMERIC) {
            return String.valueOf(cell.getNumericCellValue());
        } else {
            return null;
        }
    }
}
