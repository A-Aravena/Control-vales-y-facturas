package com.transporte.cl.repository;

import com.transporte.cl.models.Bitacora;
import com.transporte.cl.models.ControlFlota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Objects;

@Repository
public interface BitacoraRepository extends JpaRepository <Bitacora, Long>{
    @Query(nativeQuery = true, value = "select b.id ,b.actividad , b.id_control_flota , b.fecha ,b.responsable ,b.comentarios  from bitacora b  where b.id_control_flota = :idControlFlota ORDER BY b.id DESC")
    List<Object[]> getHistorialBitacora(String idControlFlota);


}
