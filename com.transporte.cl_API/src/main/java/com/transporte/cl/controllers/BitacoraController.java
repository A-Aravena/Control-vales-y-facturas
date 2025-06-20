package com.transporte.cl.controllers;

import com.transporte.cl.models.Bitacora;
import com.transporte.cl.models.DAO.Bitacorahistorial.Bitacorahistorial;
import com.transporte.cl.service.BitacoraService;
import com.transporte.cl.repository.BitacoraRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/bitacora")
public class BitacoraController{
    @Autowired
    private BitacoraService bitacoraService;


}
