package com.transporte.cl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/*
@Repository
public interface SolicitudIngresoRepository extends JpaRepository<SolicitudIngreso, Long> {
    Optional<SolicitudIngreso> findById(Long Id);
    @Query(nativeQuery = true, value = "select d.*,case when d.prioproceso > d.aprobado then true else false end proceso,(case when (CAST((CAST(d.sum AS decimal(7, 5)) / CAST(d.aprobado AS decimal(7, 5))) AS decimal(7, 2))) is null then 0 else (CAST((CAST(d.sum AS decimal(7, 5)) / CAST(d.aprobado AS decimal(7, 5))) AS decimal(7, 2))) end) *100 persol from (select s.id, case when s.cargo = 'OTRO' then s.otrocargo else s.cargo end cargo, s.contrato, s.correosolicitante, s.naceptado, s.npersonas, s.otrocargo, s.prioproceso, s.solicitante, (select u.email from procesosolicitud p inner join users u on p.iduser = u.id where p.idgerencia = s.gerencia and p.prioridad = s.prioproceso) email, (select u.nombres from procesosolicitud p inner join users u on p.iduser = u.id where p.idgerencia = s.gerencia and p.prioridad = s.prioproceso) nombres, case WHEN proceso1 = true and proceso2 = true and proceso3 = true and proceso4 = true and proceso5 = true and proceso6 = true and proceso7 = true THEN 7 WHEN proceso1 = true and proceso2 = true and proceso3 = true and proceso4 = true and proceso5 = true and proceso6 = true THEN 6 WHEN proceso1 = true and proceso2 = true and proceso3 = true and proceso4 = true and proceso5 = true THEN 5 WHEN proceso1 = true and proceso2 = true and proceso3 = true and proceso4 = true THEN 4 WHEN proceso1 = true and proceso2 = true and proceso3 = true THEN 3 WHEN proceso1 = true and proceso2 = true THEN 2 WHEN proceso1 = true THEN 1 else 0 END SUM,CASE WHEN proceso1 = false or proceso2 = false or proceso3 = false or proceso4 = false or proceso5 = false or proceso6 = false or proceso7 = false THEN false ELSE true END enproceso,(SELECT COUNT(DISTINCT(prioridad)) FROM procesosolicitud p where p.idgerencia = s.gerencia and prioridad is not null) AS aprobado from solicitudingreso s inner join direccion d on d.id = s.gerencia) d where d.prioproceso <= d.aprobado and d.prioproceso > 0")
    List<Object[]> getLastValidate();

    @Query(nativeQuery = true, value = "select d.*,case when d.prioridad > d.aprobado then true else false end proceso,(case when (CAST((CAST(d.sum AS decimal(7, 5)) / CAST(d.aprobado AS decimal(7, 5))) AS decimal(7, 2))) is null  then 0 else (CAST((CAST(d.sum AS decimal(7, 5)) / CAST(d.aprobado AS decimal(7, 5))) AS decimal(7, 2))) end) *100  persol from (select s.id, s.motivo, m.nombre nommotivo, s.centrocosto, cc.nombre nomcentro, d.nombre nomdireccion, case when s.cargo = 'OTRO' then s.otrocargo else s.cargo end cargo, s.contrato, s.correosolicitante, s.naceptado, s.npersonas, s.otrocargo, s.prioridad, s.solicitante, (select u.email from procesosolicitud p inner join users u on p.iduser = u.id  where p.idgerencia = s.gerencia and p.prioridad = s.prioridad) email,  (select u.nombres from procesosolicitud p inner join users u on p.iduser = u.id   where p.idgerencia = s.gerencia and p.prioridad = s.prioridad) nombres,  case WHEN paso1 = true and paso2 = true and paso3 = true and paso4 = true and paso5 = true and paso6 = true and paso7 = true and paso8 = true and paso9 = true and paso10 = true THEN 10  WHEN paso1 = true and paso2 = true and paso3 = true and paso4 = true and paso5 = true and paso6 = true and paso7 = true and paso8 = true and paso9 = true THEN 9   WHEN paso1 = true and paso2 = true and paso3 = true and paso4 = true and paso5 = true and paso6 = true and paso7 = true and paso8 = true then 8   WHEN paso1 = true and paso2 = true and paso3 = true and paso4 = true and paso5 = true and paso6 = true and paso7 = true THEN 7 WHEN paso1 = true and paso2 = true and paso3 = true and paso4 = true and paso5 = true and paso6 = true THEN 6  WHEN paso1 = true and paso2 = true and paso3 = true and paso4 = true and paso5 = true THEN 5  WHEN paso1 = true and paso2 = true and paso3 = true and paso4 = true THEN 4  WHEN paso1 = true and paso2 = true and paso3 = true THEN 3  WHEN paso1 = true and paso2 = true THEN 2  WHEN paso1 = true THEN 1  else 0 END SUM,CASE  WHEN paso1 = false or paso2 = false or paso3 = false or paso4 = false or paso5 = false or paso6 = false or paso7 = false or paso8 = false or paso9 = false or paso10 = false  THEN false ELSE true END enproceso,(SELECT COUNT(DISTINCT(prioridad)) FROM procesosolicitud p where p.idgerencia = s.gerencia and prioridad is not null) AS aprobado from solicitudingreso s inner join motivomovimiento m on s.motivo = m.id inner join centrocostos cc on s.centrocosto = cc.id inner join direccion d on d.id = s.gerencia) d where d.prioridad <= d.aprobado")
    List<Object[]> getSolicitudIngreso();

    @Query(nativeQuery = true, value = "select u.email from solicitudingreso s inner join procesoprioridad p on p.idmotivomovimiento = s.motivo inner join users u on u.id = p.iduser where s.id = :id and p.prioridad = :prioridad")
    String mailInicioProceso(Long id, Long prioridad);


    @Query(nativeQuery = true, value = "select s.id, s.naceptado, case when s.cargo = 'OTRO' then s.otrocargo else s.cargo end cargo, s.contrato, s.correo, s.solicitante, EXTRACT(days FROM AGE(s.dateproceso, s.datesolicitud)) diassolicitud, EXTRACT(days FROM AGE(NOW(), s.dateproceso)) diasproceso, c.nombre centrocosto, m.nombre motivomov, d.nombre direccion from solicitudingreso s left join centrocostos c on c.id = s.centrocosto left join motivomovimiento m on m.id = s.motivo left join direccion d on d.id = s.gerencia where cast(s.datesolicitud as date) >= :inicio and cast(s.datesolicitud as date) <= :fin")
    List<Object[]> getHistorialSolicitud(Date inicio, Date fin);


    @Query(nativeQuery = true, value = "select f.id, f.cargo, f.naceptado, f.solicitante, f.prioridad, f.prioproceso, f.idmotivo, f.motivo, f.nombrecentrocosto, f.proceso, f.cantidad, (f.naceptado-f.cantidad) faltante, f.dateproceso from( select d.*, case when d.prioridad > d.aprobado then true else false end proceso from(select s.id, s.cargo, s.naceptado, s.solicitante, s.prioridad, s.prioproceso, s.motivo idmotivo, m.nombre motivo, c.nombre nombrecentrocosto, (select count(id) cantidad from empleado e where e.idsolicitud = s.id) as cantidad, (SELECT count(pp.prioridad) FROM procesoprioridad pp inner join users u on u.id = pp.iduser WHERE idmotivomovimiento = s.motivo) aprobado, dateproceso from solicitudingreso s left join motivomovimiento m on m.id = s.motivo left join centrocostos c on c.id = s.centrocosto) d) f where proceso is true")
    List<Object[]> getProcesoIngreso();

    @Query("SELECT COUNT(id) AS cantidad, solicitante AS solicitante FROM SolicitudIngreso GROUP BY solicitante")
    List<Object[]> getSolicitudGerente();

    @Query(nativeQuery = true, value = "SELECT solicitante, motivo, count(solicitudes) AS solicitudes, cast(sum(cantidad) AS BIGINT) AS cantidad, sum(aprobado) AS aprobado, sum(rechazado) AS rechazado, case when (count(solicitudes) - sum(aprobado) - sum(rechazado)) is null then count(solicitudes) else count(solicitudes) - sum(aprobado) - sum(rechazado) end AS pendiente from (select id AS solicitudes, npersonas AS cantidad, case when (sum(case when paso1 = true then 1 when paso2 = true then 1 when paso3 = true then 1 when paso4 = true then 1 when paso5 = true then 1 when paso6 = true then 1 when paso7 = true then 1 when paso8 = true then 1 when paso9 = true then 1 when paso10 = true then 1 end) >= (select count(distinct(correo)) from mail))=true then 1 else 0 end AS aprobado, case when paso1 = false or paso2 = false or paso3 = false or paso4 = false or paso5 = false or paso6 = false or paso7 = false or paso8 = false or paso9 = false or paso10 = false then 1 end AS rechazado, solicitante, motivo from solicitudingreso group by 1,2,4,5,6) f group by 1,2")
    List<Object[]> getSolicitudDetalle();

    @Query(nativeQuery = true, value ="SELECT COUNT(si.id) AS cantidad, CAST(si.dateSolicitud AS date) AS dateSolicitud FROM SolicitudIngreso si GROUP BY CAST(si.dateSolicitud AS date) ORDER BY CAST(si.dateSolicitud AS date)")
    List<Object[]> getSolicitudFecha();

    @Query(nativeQuery = true, value = "select cast(count(id) as bigint) as solicitudes, cast(sum(rechazado) as bigint) as rechazado, cast(sum(aprobados) as bigint) as aprobado, cast((count(id) - sum(rechazado) - sum(aprobados)) as bigint) as pendiente from (select id, rechazado, case when count_true >= aprobado  then 1 else 0 end aprobados from (SELECT s.id, case WHEN paso1 = true and paso2 = true and paso3 = true and paso4 = true and paso5 = true and paso6 = true and paso7 = true and paso8 = true and paso9 = true and paso10 = true THEN 10 WHEN paso1 = true and paso2 = true and paso3 = true and paso4 = true and paso5 = true and paso6 = true and paso7 = true and paso8 = true and paso9 = true THEN 9 WHEN paso1 = true and paso2 = true and paso3 = true and paso4 = true and paso5 = true and paso6 = true and paso7 = true and paso8 = true then 8 WHEN paso1 = true and paso2 = true and paso3 = true and paso4 = true and paso5 = true and paso6 = true and paso7 = true THEN 7 WHEN paso1 = true and paso2 = true and paso3 = true and paso4 = true and paso5 = true and paso6 = true THEN 6 WHEN paso1 = true and paso2 = true and paso3 = true and paso4 = true and paso5 = true THEN 5 WHEN paso1 = true and paso2 = true and paso3 = true and paso4 = true THEN 4 WHEN paso1 = true and paso2 = true and paso3 = true THEN 3 WHEN paso1 = true and paso2 = true THEN 2 WHEN paso1 = true THEN 1 else 0 END count_true, CASE WHEN paso1 = false or paso2 = false or paso3 = false or paso4 = false or paso5 = false or paso6 = false or paso7 = false or paso8 = false or paso9 = false or paso10 = false THEN 1 ELSE 0 END rechazado, (SELECT COUNT(DISTINCT(email)) FROM users where prioridad > 0) AS aprobado FROM SolicitudIngreso s GROUP BY 1) d) f")
    List<Object[]> getSolicitudCards();


    @Query(value = "select u.email from procesosolicitud p inner join users u on p.iduser = u.id where p.idgerencia = :gerencia and p.prioridad = :prioridad", nativeQuery = true)
    String getMailSend(Long gerencia, Long prioridad);


    @Query("UPDATE SolicitudIngreso s SET s.paso1 = :estado, naceptado = :naceptado WHERE s.id = :id")
    SolicitudIngreso confirmPaso1(Long id, Long naceptado, Boolean estado);
    @Query("UPDATE SolicitudIngreso s SET s.paso2 = :estado, naceptado = :naceptado WHERE s.id = :id")
    SolicitudIngreso confirmPaso2(Long id, Long naceptado, Boolean estado);
    @Query("UPDATE SolicitudIngreso s SET s.paso3 = :estado, naceptado = :naceptado WHERE s.id = :id")
    SolicitudIngreso confirmPaso3(Long id, Long naceptado, Boolean estado);
    @Query("UPDATE SolicitudIngreso s SET s.paso4 = :estado, naceptado = :naceptado WHERE s.id = :id")
    SolicitudIngreso confirmPaso4(Long id, Long naceptado, Boolean estado);
    @Query("UPDATE SolicitudIngreso s SET s.paso5 = :estado, naceptado = :naceptado WHERE s.id = :id")
    SolicitudIngreso confirmPaso5(Long id, Long naceptado, Boolean estado);
    @Query("UPDATE SolicitudIngreso s SET s.paso6 = :estado, naceptado = :naceptado WHERE s.id = :id")
    SolicitudIngreso confirmPaso6(Long id, Long naceptado, Boolean estado);
    @Query("UPDATE SolicitudIngreso s SET s.paso7 = :estado, naceptado = :naceptado WHERE s.id = :id")
    SolicitudIngreso confirmPaso7(Long id, Long naceptado, Boolean estado);
    @Query("UPDATE SolicitudIngreso s SET s.paso8 = :estado, naceptado = :naceptado WHERE s.id = :id")
    SolicitudIngreso confirmPaso8(Long id, Long naceptado, Boolean estado);
    @Query("UPDATE SolicitudIngreso s SET s.paso9 = :estado, naceptado = :naceptado WHERE s.id = :id")
    SolicitudIngreso confirmPaso9(Long id, Long naceptado, Boolean estado);
    @Query("UPDATE SolicitudIngreso s SET s.paso10= :estado, naceptado = :naceptado WHERE s.id = :id")
    SolicitudIngreso confirmPaso10(Long id, Long naceptado, Boolean estado);

    @Query("UPDATE SolicitudIngreso s SET s.proceso1 = :estado WHERE s.id = :id")
    SolicitudIngreso confirmProceso1(Long id, Boolean estado);
    @Query("UPDATE SolicitudIngreso s SET s.proceso2 = :estado WHERE s.id = :id")
    SolicitudIngreso confirmProceso2(Long id, Boolean estado);
    @Query("UPDATE SolicitudIngreso s SET s.proceso3 = :estado WHERE s.id = :id")
    SolicitudIngreso confirmProceso3(Long id, Boolean estado);
    @Query("UPDATE SolicitudIngreso s SET s.proceso4 = :estado WHERE s.id = :id")
    SolicitudIngreso confirmProceso4(Long id, Boolean estado);
    @Query("UPDATE SolicitudIngreso s SET s.proceso5 = :estado WHERE s.id = :id")
    SolicitudIngreso confirmProceso5(Long id, Boolean estado);
    @Query("UPDATE SolicitudIngreso s SET s.proceso6 = :estado WHERE s.id = :id")
    SolicitudIngreso confirmProceso6(Long id, Boolean estado);
    @Query("UPDATE SolicitudIngreso s SET s.proceso7 = :estado WHERE s.id = :id")
    SolicitudIngreso confirmProceso7(Long id, Boolean estado);

}*/

