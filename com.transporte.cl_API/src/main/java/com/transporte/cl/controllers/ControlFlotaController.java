package com.transporte.cl.controllers;


import com.transporte.cl.models.Bitacora;
import com.transporte.cl.models.ControlFlota;
import com.transporte.cl.models.DAO.Bitacorahistorial.Bitacorahistorial;
import com.transporte.cl.models.DAO.ControlFlota.ClienteEmpresa;
import com.transporte.cl.models.DAO.ControlFlota.FiltroCliente;
import com.transporte.cl.models.DAO.ControlFlota.JoinInva;
import com.transporte.cl.models.DAO.ControlFlota.Pendiente;
import com.transporte.cl.repository.BitacoraRepository;
import com.transporte.cl.repository.ControlFlotaRepository;
import com.transporte.cl.service.BitacoraService;
import com.transporte.cl.service.ControlFlotaService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/controlFlota")
public class ControlFlotaController {


    @Autowired
    private ControlFlotaService controlFlotaService;
    @Autowired
    private BitacoraRepository bitacoraRepository;
    @Autowired
    private BitacoraService bitacoraService;

    @CrossOrigin("*")
    @GetMapping
    public ResponseEntity<List<ControlFlota>> getALL(){
        List<ControlFlota> controlFlotas = controlFlotaService.getAll();
        return  new ResponseEntity<>(controlFlotas, HttpStatus.OK);

    }

    @CrossOrigin("*")
    @GetMapping("/clientes")
    public List<ClienteEmpresa> obtenerClientes() {
        List<Object[]> clientes = controlFlotaService.findClienteDistinct();
        return clientes.stream().map(
                row -> new ClienteEmpresa(
                        (String) row[0]
                )).collect(Collectors.toList());
    }

    @CrossOrigin("*")
    @GetMapping("/empresa")
    public List<ClienteEmpresa> obtenerEmpresa() {
        List<Object[]> clientes = controlFlotaService.findEmpresaDistinct();

        return clientes.stream().map(
                row -> new ClienteEmpresa(
                        (String) row[0]
                )).collect(Collectors.toList());
    }



    @CrossOrigin("*")
    @GetMapping("/controlInva")
    public List<JoinInva> getControlFlotaInva(@Param("dtp") String dtp, @Param("clientep") String clientep,@Param("nrolegalp") String nrolegalp
            ,@Param("estadofinalp") String estadofinalp ,@Param("cantCajasP") String cantCajasP ,@Param("descSolicitantep") String descSolicitantep
            ,@Param("fafatrealp") String fafatrealp,@Param("confpodp") String confpodp,@Param("fdescargap") String fdescargap,@Param("empresa") String empresa
            ,@Param("empresa_") String empresa_){


        System.out.println("CONTROLLER EMPRESA " + empresa);
        System.out.println("CONTROLLER EMPRESA_ " + empresa_);
        List<Object[]> controlInva = controlFlotaService.getControlFlotaInva(dtp ,clientep, nrolegalp , estadofinalp ,cantCajasP,descSolicitantep
                ,fafatrealp,confpodp, fdescargap,empresa,empresa_);


        return controlInva.stream().map(
                row -> new JoinInva(
                        (Long) row[0],
                        (String) row[1],
                        (String) row[2],
                        (String) row[3],
                        (String) row[4],
                        (String) row[5],
                        (String) row[6],
                        (String) row[7],
                        (String) row[8],
                        (String) row[9],
                        (String) row[10],
                        (String) row[11],
                        (String) row[12],
                        (String) row[13],
                        (String) row [14]

                )).collect(Collectors.toList());


    }

    @CrossOrigin("*")
    @GetMapping("/bitacora/{idControlFlota}")
    public List<Bitacorahistorial> getbitacora(@PathVariable String idControlFlota){



        List<Object[]> bitacora = bitacoraService.getByidControlFlota( idControlFlota);
        return bitacora.stream().map(
                row -> new Bitacorahistorial(
                        (Long) row[0],
                        (String) row[1],
                        (String) row[2],
                        (String) row[3],
                        (String) row[4],
                        (String) row[5]

                )).collect(Collectors.toList());

    }

    @CrossOrigin("*")
    @GetMapping("/clientes/{nomclic}/{empresa}")
    public List<FiltroCliente> getbfiltro(@PathVariable String nomclic,@PathVariable("empresa") String empresa){
        if(nomclic.contains("UNILEVER")) {
            List<Object[]> filtrocliente = controlFlotaService.getFiltroClienteUnilever(empresa);
            return filtrocliente.stream().map(
                    row -> new FiltroCliente(
                            (BigDecimal) row[0],
                            (BigDecimal) row[1],
                            (BigDecimal) row[2]
                    )).collect(Collectors.toList());
        } else {
            List<Object[]> filtrocliente = controlFlotaService.getFiltroClienteOtros(nomclic,empresa);
            return filtrocliente.stream().map(
                    row -> new FiltroCliente(
                            (BigDecimal) row[0],
                            (BigDecimal) row[1],
                            (BigDecimal) row[2]
                    )).collect(Collectors.toList());
        }
    }

    @CrossOrigin("*")
    @GetMapping("/pendiente/{clientep}/{empresa}")
    public List<Pendiente> getPendiente(@PathVariable String clientep,@PathVariable("empresa") String empresa) {
            List<Object[]> filtrocliente = controlFlotaService.getPendientes(clientep ,empresa);
            return filtrocliente.stream().map(
                    row -> new Pendiente(
                            (String) row[0],
                            (BigDecimal) row[1]
                    )).collect(Collectors.toList());

    }

    @CrossOrigin("*")
    @GetMapping("/filtroclienteALL")
    public List<FiltroCliente> getbfiltro(){

        List<Object[]> filtrocliente = controlFlotaService.getFiltroClienteALL();
        return filtrocliente.stream().map(
                row -> new FiltroCliente(
                        (BigDecimal) row[0],
                        (BigDecimal) row[1],
                        (BigDecimal) row[2]
                )).collect(Collectors.toList());
    }


    @PostMapping("/upload")
    public ResponseEntity<List<ControlFlota>> uploadExcelFile(@RequestParam("file") MultipartFile file){
        try {
            List<ControlFlota> records = controlFlotaService.uploadFromExcel(file);
            return new ResponseEntity<>(records, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/uploadFactura/{nro_legal}/{cliente}/{usuario}")
    public ResponseEntity<String> uploadImage(@PathVariable String nro_legal, @PathVariable String cliente,@PathVariable String usuario, @RequestParam("imageFile") MultipartFile imageFile) {
        try {

            controlFlotaService.imagenFacturaURL(nro_legal,cliente, imageFile, usuario);
            return new ResponseEntity<>("Imagen cargada y ruta actualizada correctamente.", HttpStatus.OK);
        } catch (IOException | EntityNotFoundException e) {
            e.printStackTrace(); // Manejar el error de acuerdo a tus necesidades
            return new ResponseEntity<>("Error al cargar la imagen o actualizar la ruta.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/uploadValePallet/{nro_legal}/{cliente}/{usuario}")
    public ResponseEntity<String> uploadVale(@PathVariable String nro_legal,  @PathVariable String cliente,@PathVariable String usuario, @RequestParam("imageFile") MultipartFile imageFile) {
        try {

            controlFlotaService.imagenValePalletURL(nro_legal,cliente, imageFile, usuario);
            return new ResponseEntity<>("Imagen cargada y ruta actualizada correctamente.", HttpStatus.OK);
        } catch (IOException | EntityNotFoundException e) {
            e.printStackTrace(); // Manejar el error de acuerdo a tus necesidades
            return new ResponseEntity<>("Error al cargar la imagen o actualizar la ruta.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @CrossOrigin("*")
    @PutMapping("/actualizarPOD/{nro_legal}/{cliente}/{usuario}")
    public Bitacora actualizarFechaPOD(@PathVariable String nro_legal, @PathVariable String cliente, @PathVariable String usuario, @RequestParam("newPOD") String newPOD ) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        try {
            if(cliente.contains("UNILEVER")){

                controlFlotaService.actualizarFechaPODinva(nro_legal, newPOD);
                Bitacora bitacora = new Bitacora();

                bitacora.setFecha(LocalDateTime.now().format(formatter));
                bitacora.setResponsable(usuario);
                bitacora.setActividad("Cambio de POD");
                bitacora.setIdControlFlota(nro_legal);
                bitacora.setComentarios("");



                return  bitacoraRepository.save(bitacora);
            }
            else{

                controlFlotaService.actualizarFechaPODCF(nro_legal, newPOD);
                Bitacora bitacora = new Bitacora();

                bitacora.setFecha(LocalDateTime.now().format(formatter));
                bitacora.setResponsable(usuario);
                bitacora.setActividad("Cambio de POD");
                bitacora.setIdControlFlota(nro_legal);
                bitacora.setComentarios("");

                System.out.println(bitacora);

                return  bitacoraRepository.save(bitacora);
            }
            //return new ResponseEntity<>("Fecha POD actualizada correctamente.", HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
           // return new ResponseEntity<>("Error al actualizar la fecha POD.", HttpStatus.INTERNAL_SERVER_ERROR);

        }
        return null;
    }


    @CrossOrigin("*")
    @PutMapping("/conformar/{nro_legal}/{cliente}/{usuario}")
    public Bitacora actualizarConformar(@PathVariable String nro_legal, @PathVariable String cliente, @PathVariable String usuario) {
        System.out.println("CLIENTE???" + cliente);
        try {
            if(cliente.contains("UNILEVER")){

                controlFlotaService.Conformarinva(nro_legal,usuario);

                }
            else{
                System.out.println("CF" + cliente);
                controlFlotaService.ConformarCF(nro_legal, usuario);

            }
            return null;
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            return null;

        }
    }
    @PostMapping("/addcomentario")
    public ResponseEntity<String> addcomentario(@RequestBody Bitacora bitacora ) {
        try {
            bitacora.setActividad("Comentario agregado");

            bitacoraService.createBitacora(bitacora);

            return null;
        } catch (Exception ex) {
            System.out.println(ex); // Manejar el error de acuerdo a tus necesidades
            return null;
        }
    }


}
