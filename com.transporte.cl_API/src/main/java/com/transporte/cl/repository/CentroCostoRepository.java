package com.transporte.cl.repository;

import com.transporte.cl.models.CentroCosto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CentroCostoRepository extends JpaRepository<CentroCosto, Long> {
    @Query(value = "select id, nombre, estado from centrocostos where estado = true", nativeQuery = true)
    List<CentroCosto> getEnable();
}
