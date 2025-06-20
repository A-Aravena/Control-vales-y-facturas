package com.transporte.cl.repository;

import com.transporte.cl.models.ControlFlota;
import com.transporte.cl.models.Inva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface InvaRepository extends JpaRepository<Inva, Long> {
    Inva findByNroLegal(String nroLegal);





}
