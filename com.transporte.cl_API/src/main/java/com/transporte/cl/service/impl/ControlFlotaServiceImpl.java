package com.transporte.cl.service.impl;

import com.transporte.cl.models.Bitacora;
import com.transporte.cl.models.CentroCosto;
import com.transporte.cl.models.ControlFlota;
import com.transporte.cl.models.Inva;
import com.transporte.cl.repository.BitacoraRepository;
import com.transporte.cl.repository.ControlFlotaRepository;
import com.transporte.cl.repository.InvaRepository;
import com.transporte.cl.service.ControlFlotaService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class ControlFlotaServiceImpl implements ControlFlotaService {


    @Value("${facturas.upload.path}")
    private String facturasUploadPath;
    @Value("${facturasTarget.upload.path}")

    private String facturasTargetUploadPath;
    @Value("${valepallet.upload.path}")
    private String valepallepUploadPath;
    @Value("${valepalletTarget.upload.path}")
    private String valepallepTargetUploadPath;


    @Autowired
    private  ControlFlotaRepository controlFlotaRepository;

    @Autowired
    private BitacoraRepository bitacoraRepository;


    @Autowired
    private  InvaRepository invaRepository;
    @Override
    public List<ControlFlota> uploadFromExcel(MultipartFile file) throws IOException {

        List<ControlFlota> controlFlotas = new ArrayList<>();

       try (InputStream inputStream = file.getInputStream()){
           Workbook workbook = new XSSFWorkbook(inputStream);
           Sheet sheet = workbook.getSheetAt(0);

           for (Row row:sheet){
               if(row.getRowNum()== 0) continue;
               String cliente = getStringCellValue(row.getCell(0));
               String shipment = null;

               String originalValue = getStringCellValue(row.getCell(1));
               if(originalValue.contains(";")) {
                   String[] values = originalValue.split(";");
                   for (String shipmentn : values) {
                       ControlFlota controlFlota = createControlFlotaFromRow(row);
                       controlFlota.setShipment(shipmentn.trim());

                       if (shipmentn.trim() != null && shipmentn.trim().length() < 8) {
                           int cerosFaltantes = 8 - shipmentn.trim().length();
                           StringBuilder ceros = new StringBuilder();
                           for (int i = 0; i < cerosFaltantes; i++) {
                               ceros.append("0");
                           }
                           shipmentn = shipmentn + ceros.toString();
                       }
                       ControlFlota existShipment = controlFlotaRepository.findByshipment(shipmentn.trim());
                       controlFlota.setEstadoFinal("No Presentado");
                       if (existShipment == null) controlFlotas.add(controlFlota);
                   }
               }else{
                   shipment = (getStringCellValue(row.getCell(1)) != null) ? getStringCellValue(row.getCell(1)).replace(".0", "").replace("E7", "").replace("E8", "").replace(".", "") : null;

                   if (shipment != null && shipment.length() < 8) {
                       int cerosFaltantes = 8 - shipment.length();
                       StringBuilder ceros = new StringBuilder();
                       for (int i = 0; i < cerosFaltantes; i++) {
                           ceros.append("0");
                       }
                       shipment = shipment + ceros.toString();
                   }
               }

               SimpleDateFormat formatoEntrada = new SimpleDateFormat("dd/MM/yyyy HH:mm");
               SimpleDateFormat formatoSalida = new SimpleDateFormat("dd-MM-yyyy");
               Date fechaDescarga = null;

               String tipo = getStringCellValue(row.getCell(2));
               String clienteFinal = getStringCellFormula(row.getCell(3));
               String fRetiro = getStringCellFormula2(row.getCell(4));
               String fDescarga = getStringCellFormula2(row.getCell(5));

               try {
                   if (fDescarga != null && !fDescarga.isEmpty()) {
                       fechaDescarga = formatoEntrada.parse(fDescarga);
                       fDescarga = formatoSalida.format(fechaDescarga);
                   }
               } catch (ParseException e) {
                   e.printStackTrace(); // Manejar la excepción según sea necesario
               }

               String comuna = getStringCellValue(row.getCell(6));
               String direccion = getStringCellValue(row.getCell(7));
               String cantPallet = (getStringCellValue(row.getCell(8)) != null) ? getStringCellValue(row.getCell(8)).replaceFirst("\\.0.*", "").replace(".0", "") : null;
               String cantCajas = (getStringCellValue(row.getCell(9)) != null) ? getStringCellValue(row.getCell(9)).replaceFirst("\\.0.*", "").replace(".0", "") : null;

               String tipoCamion = getStringCellValue(row.getCell(10));
               String nombreChofer = getStringCellValue(row.getCell(11));
               String numeroTelefono = getStringCellValue(row.getCell(12));
               String empresa = getStringCellValue(row.getCell(13));
               String patenteCamion = getStringCellValue(row.getCell(14));
               String patenteRampla = getStringCellValue(row.getCell(15));
               String tara = getStringCellValue(row.getCell(16));
               String estado = getStringCellValue(row.getCell(17));
               String observacion = getStringCellValue(row.getCell(18));
               String peonetaCantidad = getStringCellValue(row.getCell(19));
               String repaletizado = getStringCellValue(row.getCell(20));
               String sobreestadia = getStringCellValue(row.getCell(21));
               String almacenamiento = getStringCellValue(row.getCell(22));
               String feriado = getStringCellValue(row.getCell(23));
               String retorno = getStringCellValue(row.getCell(24));
               String redespacho = getStringCellValue(row.getCell(25));
               String reagenda = getStringCellValue(row.getCell(26));

               ControlFlota controlFlota = new ControlFlota();
               controlFlota.setCliente(cliente);
               controlFlota.setShipment(shipment);
               controlFlota.setTipo(tipo);
               controlFlota.setClienteFinal(clienteFinal);
               controlFlota.setFRetiro(fRetiro);
               controlFlota.setFDescarga(fDescarga);
               controlFlota.setComuna(comuna);
               controlFlota.setDireccion(direccion);
               controlFlota.setCantPallet(cantPallet);
               controlFlota.setCantCajas(cantCajas);
               controlFlota.setTipoCamion(tipoCamion);
               controlFlota.setNombreChofer(nombreChofer);
               controlFlota.setNumeroTelefono(numeroTelefono);
               controlFlota.setEmpresa(empresa);
               controlFlota.setPatenteCamion(patenteCamion);
               controlFlota.setPatenteRampla(patenteRampla);
               controlFlota.setTara(tara);
               controlFlota.setEstado(estado);
               controlFlota.setObservacion(observacion);
               controlFlota.setPeonetaCantidad(peonetaCantidad);
               controlFlota.setRepaletizado(repaletizado);
               controlFlota.setSobreestadia(sobreestadia);
               controlFlota.setAlmacenamiento(almacenamiento);
               controlFlota.setFeriado(feriado);
               controlFlota.setRetorno(retorno);
               controlFlota.setRedespacho(redespacho);
               controlFlota.setReagenda(reagenda);
               controlFlota.setEstadoFinal("No Presentado");

               ControlFlota existShipment = controlFlotaRepository.findByshipment(shipment);

               if(existShipment != null){

                   existShipment.setCliente(controlFlota.getCliente());
                   existShipment.setShipment(controlFlota.getShipment());
                   existShipment.setTipo(controlFlota.getTipo());
                   existShipment.setClienteFinal(controlFlota.getClienteFinal());
                   existShipment.setFRetiro(controlFlota.getFRetiro());
                   existShipment.setFDescarga(controlFlota.getFDescarga());
                   existShipment.setComuna(controlFlota.getComuna());
                   existShipment.setDireccion(controlFlota.getDireccion());
                   existShipment.setCantPallet(controlFlota.getCantPallet());
                   existShipment.setCantCajas(controlFlota.getCantCajas());
                   existShipment.setTipoCamion(controlFlota.getTipoCamion());
                   existShipment.setNombreChofer(controlFlota.getNombreChofer());
                   existShipment.setNumeroTelefono(controlFlota.getNumeroTelefono());
                   existShipment.setEmpresa(controlFlota.getEmpresa());
                   existShipment.setPatenteCamion(controlFlota.getPatenteCamion());
                   existShipment.setPatenteRampla(controlFlota.getPatenteRampla());
                   existShipment.setTara(controlFlota.getTara());
                   existShipment.setEstado(controlFlota.getEstado());
                   existShipment.setObservacion(controlFlota.getObservacion());
                   existShipment.setPeonetaCantidad(controlFlota.getPeonetaCantidad());
                   existShipment.setRepaletizado(controlFlota.getRepaletizado());
                   existShipment.setSobreestadia(controlFlota.getSobreestadia());
                   existShipment.setAlmacenamiento(controlFlota.getAlmacenamiento());
                   existShipment.setFeriado(controlFlota.getFeriado());
                   existShipment.setRetorno(controlFlota.getRetorno());
                   existShipment.setRedespacho(controlFlota.getRedespacho());
                   existShipment.setReagenda(controlFlota.getReagenda());
                   controlFlotaRepository.save(existShipment);
               }else {

                   if (controlFlota.getShipment() != null) {
                       controlFlotas.add(controlFlota);
                   }
               }
           }

       }catch (IOException e){
           throw new IOException("Error: ", e);

       }
       controlFlotaRepository.saveAll(controlFlotas);

       return controlFlotas;
    }
    private ControlFlota createControlFlotaFromRow(Row row) {
        SimpleDateFormat formatoEntrada = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        SimpleDateFormat formatoSalida = new SimpleDateFormat("dd-MM-yyyy");
        Date fechaDescarga = null;


        String cliente = getStringCellValue(row.getCell(0));
        String shipment = getStringCellValue(row.getCell(1));
        if (shipment != null && shipment.length() < 8) {
            int cerosFaltantes = 8 - shipment.length();
            StringBuilder ceros = new StringBuilder();
            for (int i = 0; i < cerosFaltantes; i++) {
                ceros.append("0");
            }
            shipment = shipment + ceros.toString();
        }
        String tipo = getStringCellValue(row.getCell(2));
        String clienteFinal = getStringCellFormula(row.getCell(3));
        String fRetiro = getStringCellFormula2(row.getCell(4));
        String fDescarga = getStringCellFormula2(row.getCell(5));

        try {
            if (fDescarga != null && !fDescarga.isEmpty()) {
                fechaDescarga = formatoEntrada.parse(fDescarga);
                fDescarga = formatoSalida.format(fechaDescarga);
            }
        } catch (ParseException e) {
            e.printStackTrace(); // Manejar la excepción según sea necesario
        }

        String comuna = getStringCellValue(row.getCell(6));

        String direccion = getStringCellValue(row.getCell(7));
        String cantPallet = (getStringCellValue(row.getCell(8)) != null) ? getStringCellValue(row.getCell(8)).replaceFirst("\\.0.*", "").replace(".0", "") : null;
        String cantCajas = (getStringCellValue(row.getCell(9)) != null) ? getStringCellValue(row.getCell(9)).replaceFirst("\\.0.*", "").replace(".0", "") : null;


        String tipoCamion = getStringCellValue(row.getCell(10));
        String nombreChofer = getStringCellValue(row.getCell(11));
        String numeroTelefono = getStringCellValue(row.getCell(12));
        String empresa = getStringCellValue(row.getCell(13));
        String patenteCamion = getStringCellValue(row.getCell(14));
        String patenteRampla = getStringCellValue(row.getCell(15));
        String tara = getStringCellValue(row.getCell(16));
        String estado = getStringCellValue(row.getCell(17));
        String observacion = getStringCellValue(row.getCell(18));
        String peonetaCantidad = getStringCellValue(row.getCell(19));
        String repaletizado = getStringCellValue(row.getCell(20));
        String sobreestadia = getStringCellValue(row.getCell(21));
        String almacenamiento = getStringCellValue(row.getCell(22));
        String feriado = getStringCellValue(row.getCell(23));
        String retorno = getStringCellValue(row.getCell(24));
        String redespacho = getStringCellValue(row.getCell(25));
        String reagenda = getStringCellValue(row.getCell(26));

        ControlFlota controlFlota = new ControlFlota();
        controlFlota.setCliente(cliente);
        controlFlota.setTipo(tipo);
        controlFlota.setClienteFinal(clienteFinal);
        controlFlota.setFRetiro(fRetiro);
        controlFlota.setFDescarga(fDescarga);
        controlFlota.setComuna(comuna);
        controlFlota.setDireccion(direccion);
        controlFlota.setCantPallet(cantPallet);
        controlFlota.setCantCajas(cantCajas);
        controlFlota.setTipoCamion(tipoCamion);
        controlFlota.setNombreChofer(nombreChofer);
        controlFlota.setNumeroTelefono(numeroTelefono);
        controlFlota.setEmpresa(empresa);
        controlFlota.setPatenteCamion(patenteCamion);
        controlFlota.setPatenteRampla(patenteRampla);
        controlFlota.setTara(tara);
        controlFlota.setEstado(estado);
        controlFlota.setObservacion(observacion);
        controlFlota.setPeonetaCantidad(peonetaCantidad);
        controlFlota.setRepaletizado(repaletizado);
        controlFlota.setSobreestadia(sobreestadia);
        controlFlota.setAlmacenamiento(almacenamiento);
        controlFlota.setFeriado(feriado);
        controlFlota.setRetorno(retorno);
        controlFlota.setRedespacho(redespacho);
        controlFlota.setReagenda(reagenda);
        controlFlota.setEstadoFinal("No Presentado");

        return controlFlota;
    }
    @Override
    public List<ControlFlota> getAll() {
        return controlFlotaRepository.findAll(Sort.by(Sort.Direction.ASC,"id"));

    }

    @Override
    public List<Object[]> findClienteDistinct() {return controlFlotaRepository.findClienteDistinct(); }

    @Override
    public List<Object[]> findEmpresaDistinct() {return controlFlotaRepository.findEmpresaDistinct(); }

    @Override
    public List<Object[]> getFiltroClienteALL() {
        return controlFlotaRepository.getFiltroClienteALL();
    }


    @Override
    public List<Object[]> getControlFlotaInva(String dtp,String clientep , String nrolegalp , String estadofinalp, String cantCajasP , String descSolicitantep
                                              ,String fafatrealp, String confpodp , String fdescargap, String empresa, String empresa_) {

        System.out.println("ESTO ES EL clientep: " + clientep);
        System.out.println("ESTO ES EL empresa_ : " + empresa_);
        System.out.println("ESTO ES EL empresa usuario : " + empresa);
        return controlFlotaRepository.getControlFlotaInva(dtp, clientep , nrolegalp, estadofinalp, cantCajasP,descSolicitantep , fafatrealp,confpodp , fdescargap, empresa,empresa_);
    }

    @Override
    public List<Object[]> getFiltroClienteUnilever(String empresa) {
        return controlFlotaRepository.getFiltroClienteUnilever(empresa);
    }

    @Override
    public List<Object[]> getFiltroClienteOtros(String nomclic, String empresa) {
        return controlFlotaRepository.getFiltroClienteOtros(nomclic,empresa);
    }

    @Override
    public List<Object[]> getPendientes(String clientep, String empresa) {
        if (clientep.equals("Todos") ){
            return controlFlotaRepository.getPendientesAll(empresa);
        }else{
            return controlFlotaRepository.getPendientes(clientep ,empresa);
        }

    }



    @Override
    public List<Object[]> getBitacora(String idControlFlota) {
        return bitacoraRepository.getHistorialBitacora(idControlFlota);
    }

    @Override
    public void imagenFacturaURL(String nro_legal, String cliente, MultipartFile imageFile , String usuario) throws IOException{

        try{

            LocalDateTime fechaActual = LocalDateTime.now();

            String nombreArchivo = "factura" + fechaActual.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + ".pdf";
            Path filePath = Paths.get(facturasUploadPath, nombreArchivo);
            Path filePathtarget = Paths.get(facturasTargetUploadPath, nombreArchivo);


            Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            Files.copy(imageFile.getInputStream(), filePathtarget, StandardCopyOption.REPLACE_EXISTING);




            String facturaUrl = "http://localhost:8092/images/facturas/" + nombreArchivo;


            HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
            if(response != null) {
                response.setHeader("Cache-Control","no-store, no-cache, must-revalidate, max-age=0" );
                response.setHeader("Pragma","no-cache");
                response.setHeader("Expires","0");
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

            if(cliente.contains("UNILEVER")){

                Bitacora bitacora = new Bitacora();

                bitacora.setFecha(LocalDateTime.now().format(formatter));
                bitacora.setResponsable(usuario);
                bitacora.setActividad("Subida de archivo Factura");
                bitacora.setIdControlFlota(nro_legal);
                bitacoraRepository.save(bitacora);

                Inva invaExist = invaRepository.findByNroLegal(nro_legal);
                if(invaExist!=null) {
                    System.out.println("URL: " + facturaUrl);
                    invaExist.setEstadofinal("Incompleto");
                    invaExist.setFacturaURL(facturaUrl);
                    invaRepository.save(invaExist);
                }

            }else{
                Bitacora bitacora = new Bitacora();
                bitacora.setFecha(LocalDateTime.now().format(formatter));
                bitacora.setResponsable(usuario);
                bitacora.setActividad("subida de archivo Factura");
                bitacora.setIdControlFlota(nro_legal);
                bitacoraRepository.save(bitacora);

                ControlFlota b = controlFlotaRepository.updateFacturaUrlCF(nro_legal, facturaUrl);

            }

        }catch (Exception ex){System.out.println( "ERROR" + ex);}


    }
    @Override
    public void imagenValePalletURL(String nro_legal, String cliente, MultipartFile imageFile ,String usuario) throws IOException {
        LocalDateTime fechaActual = LocalDateTime.now();
        String nombreArchivo = "ValePallet" + fechaActual.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + ".pdf";
        Path filePath = Paths.get(valepallepUploadPath, nombreArchivo);
        Path filePathtarget = Paths.get(valepallepTargetUploadPath, nombreArchivo);


        Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        Files.copy(imageFile.getInputStream(), filePathtarget, StandardCopyOption.REPLACE_EXISTING);

        String facturaUrl = "http://localhost:8092/images/valePallets/" + nombreArchivo;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");




        if(cliente.contains("UNILEVER")){
            Bitacora bitacora = new Bitacora();
            bitacora.setFecha(LocalDateTime.now().format(formatter));
            bitacora.setResponsable(usuario);
            bitacora.setActividad("Subida archivo Vale Pallet");
            bitacora.setIdControlFlota(nro_legal);
            bitacoraRepository.save(bitacora);

            Inva invaExist = invaRepository.findByNroLegal(nro_legal);
            if(invaExist!=null) {
                System.out.println("URL: " + facturaUrl);
                invaExist.setEstadofinal("Incompleto");
                invaExist.setValePalletURL(facturaUrl);
                invaRepository.save(invaExist);
            }
            //ControlFlota b = controlFlotaRepository.updateValePalletUrlinva(nro_legal, facturaUrl);

        }else{
            Bitacora bitacora = new Bitacora();
            bitacora.setFecha(LocalDateTime.now().format(formatter));
            bitacora.setResponsable(usuario);
            bitacora.setActividad("Subida archivo Vale Pallet");
            bitacora.setIdControlFlota(nro_legal);
            bitacoraRepository.save(bitacora);

           ControlFlota b =controlFlotaRepository.updateValePalletUrlCF(nro_legal, facturaUrl);

        }

    }

   @Override
    public void actualizarFechaPODinva(String nro_legal, String newPOD) {
        try{
                controlFlotaRepository.cambioPODINVA(nro_legal, newPOD);
        }catch(Exception ex){
            System.out.println(ex);
       }
    }
    @Override
    public void actualizarFechaPODCF(String dt, String newPOD) {
        try{
            controlFlotaRepository.cambioPODCF(dt, newPOD);
        }catch(Exception ex){
            System.out.println(ex);
        }
    }

    @Override
    public void Conformarinva(String nro_legal ,String usuario) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        Bitacora bitacora = new Bitacora();
        bitacora.setFecha(LocalDateTime.now().format(formatter));
        bitacora.setResponsable(usuario);
        bitacora.setActividad("Cambio de estado a 'Conformado'");
        bitacora.setIdControlFlota(nro_legal);
        bitacoraRepository.save(bitacora);

        Inva invaExist = invaRepository.findByNroLegal(nro_legal);
        System.out.println("COnformar??" + nro_legal);
        if(invaExist!=null) {

            invaExist.setEstadofinal("Conformado");
            invaRepository.save(invaExist);

        }
        //controlFlotaRepository.cambioEstadoinva(nro_legal);

           }

    @Override
    public void ConformarCF(String nro_legal, String usuario) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        Bitacora bitacora = new Bitacora();
        bitacora.setFecha(LocalDateTime.now().format(formatter));
        bitacora.setResponsable(usuario);
        bitacora.setActividad("Cambio de estado a 'Conformado'");
        bitacora.setIdControlFlota(nro_legal);
        bitacoraRepository.save(bitacora);

        controlFlotaRepository.cambioEstadoCF(nro_legal);

    }

    @Override
    public void actualizarestadoinva(String nro_legal) {
        controlFlotaRepository.cambioestadoinvaI(nro_legal);
    }

    @Override
    public void actualizarestadoCF(String nro_legal) {
        controlFlotaRepository.cambioestadoCFI(nro_legal);

    }

    @Override
    public ControlFlota updateEstado(Long id) {
        Optional<ControlFlota> optionalControlFlota = controlFlotaRepository.findById(id);
        if(optionalControlFlota.isPresent()) {
            ControlFlota controlFlota = optionalControlFlota.get();
            controlFlota.setEstadoFinal("Conformado");
            return controlFlotaRepository.save(controlFlota);
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
    private String getStringCellFormula(Cell cell) {
        if (cell != null) {
            if (cell.getCellType() == CellType.FORMULA) {
                return  cell.getStringCellValue();
            } else {
                return cell.getStringCellValue();
            }
        } else {
            return null;
        }
    }
    private String getStringCellFormula2(Cell cell) {
        if (cell == null) {
            return null;
        }

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case FORMULA:
                // Manejar celdas de fórmula
                if (cell.getCachedFormulaResultType() == CellType.STRING) {
                    return cell.getRichStringCellValue().getString();
                } else if (DateUtil.isCellDateFormatted(cell)) {
                    Date date = cell.getDateCellValue();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                    return dateFormat.format(date);
                } else {
                    return String.valueOf(cell.getNumericCellValue());
                }
            case NUMERIC:
                // Manejar celdas numéricas
                if (DateUtil.isCellDateFormatted(cell)) {
                    Date date = cell.getDateCellValue();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                    return dateFormat.format(date);
                } else {
                    return String.valueOf(cell.getNumericCellValue());
                }
            default:
                return null;
        }
    }

}


