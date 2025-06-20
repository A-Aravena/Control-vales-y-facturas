package com.transporte.cl.controllers;

import com.transporte.cl.models.CentroCosto;
import com.transporte.cl.service.CentroCostoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/centrocosto")
public class CentroCostoController {
    @Autowired
    private CentroCostoService centroCostoService;

    @CrossOrigin("*")
    @GetMapping
    public ResponseEntity<List<CentroCosto>> getAll() {
        List<CentroCosto> ccosto = centroCostoService.getAll();
        return new ResponseEntity<>(ccosto, HttpStatus.OK);
    }

    @CrossOrigin("*")
    @GetMapping("/{id}")
    public ResponseEntity<CentroCosto> getById(@PathVariable Long id) {
        Optional<CentroCosto> ccosto = Optional.ofNullable(centroCostoService.getById(id));
        return ccosto.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @CrossOrigin("*")
    @PostMapping
    public ResponseEntity<CentroCosto> create(@RequestBody CentroCosto ccosto) {
        CentroCosto createdCCosto = centroCostoService.create(ccosto);
        return new ResponseEntity<>(createdCCosto, HttpStatus.CREATED);
    }

    @CrossOrigin("*")
    @PutMapping("/{id}")
    public ResponseEntity<CentroCosto> update(@PathVariable Long id, @RequestBody CentroCosto ccosto) {
        CentroCosto updatedCCosto = centroCostoService.update(id, ccosto);
        return updatedCCosto != null ? new ResponseEntity<>(updatedCCosto, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @CrossOrigin("*")
    @PutMapping("/estado/{id}")
    public ResponseEntity<CentroCosto> updateEstado(@PathVariable Long id) {
        CentroCosto updatedCCosto = centroCostoService.updateEstado(id);
        return updatedCCosto != null ? new ResponseEntity<>(updatedCCosto, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @CrossOrigin("*")
    @GetMapping("/habilitados")
    public ResponseEntity<List<CentroCosto>> getHabilitados() {
        List<CentroCosto> ccosto = centroCostoService.getEnable();
        return new ResponseEntity<>(ccosto, HttpStatus.OK);
    }
}
