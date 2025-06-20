package com.transporte.cl.controllers;

import com.transporte.cl.models.Inva;
import com.transporte.cl.service.InvaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/inva")
public class InvaController {
    @Autowired
    private  InvaService invaService;

    @CrossOrigin("*")
    @GetMapping
    public ResponseEntity<List<Inva>> getALL(){
        List<Inva> invas = invaService.getAll();
        return  new ResponseEntity<>(invas, HttpStatus.OK);

    }

    @PostMapping("/upload")
    public ResponseEntity<List<Inva>> uploadExcelFile(@RequestParam("file") MultipartFile file){
        try {
            List<Inva> records = invaService.uploadFromExcel(file);
            return new ResponseEntity<>(records, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }





}
