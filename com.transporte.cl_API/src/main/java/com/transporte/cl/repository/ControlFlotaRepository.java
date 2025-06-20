package com.transporte.cl.repository;

import com.transporte.cl.models.ControlFlota;
import com.transporte.cl.models.Inva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ControlFlotaRepository extends JpaRepository<ControlFlota,Long>{

    ControlFlota findByshipment(String shipment);


    @Query(nativeQuery = true, value = "SELECT cf.id,case when cf.cliente = 'UNILEVER' then i.estadofinal  else cf.estado_final  END AS estado_final,cf.cliente,cf.shipment as dt ,i.nro_legal ,cf.cliente_final,case when cf.cliente = 'UNILEVER' then i.q_fact else cf.cant_cajas END AS Cantidad_Cajas,concat(cf.direccion ,'-', cf.comuna  ) as  direccion, i.desc_solicitante , i.fa_fat_real , case when cf.cliente = 'UNILEVER' then i.confpod else cf.confpod  end as confpod,cf.f_descarga,case when cf.cliente = 'UNILEVER' then i.facturaurl else cf.facturaurl  end as facturaurl,case when cf.cliente = 'UNILEVER' then i.vale_palleturl else cf.vale_palleturl  end as vale_palleturl, cf.empresa FROM control_flota cf LEFT JOIN Inva i ON i.dt  = cf.shipment WHERE ((:dtp = '' OR cf.shipment = :dtp)and (:clientep = '' or cf.cliente = :clientep) and (:nrolegalp = '' or i.nro_legal = :nrolegalp) and (:estadofinalp = '' or case when cf.cliente = 'UNILEVER' then i.estadofinal  else cf.estado_final  END = :estadofinalp)and (:cantCajasP = '' or case when cf.cliente = 'UNILEVER' then TRIM(i.q_fact) else TRIM(cf.cant_cajas) end = :cantCajasP) and (:descSolicitantep = '' or i.desc_solicitante = :descSolicitantep) and (:fafatrealp = '' or i.fa_fat_real >= :fafatrealp) and (:confpodp = '' or i.confpod >= :confpodp)  and (:empresa_ = '' or cf.empresa  = :empresa_) and (:fdescargap = '' or cf.f_descarga  >= :fdescargap)) and ((cf.empresa = :empresa) or (:empresa = 'Todos'))")
    List<Object[]> getControlFlotaInva(String dtp, String clientep ,String nrolegalp , String estadofinalp, String cantCajasP, String descSolicitantep, String fafatrealp ,String confpodp ,String fdescargap ,String empresa ,String empresa_);



    @Query(nativeQuery = true, value = "update inva  set estadofinal ='Conformado' where nro_legal  = :nro_legal")
    ControlFlota cambioEstadoinva(String nro_legal);

    @Query(nativeQuery = true, value = "update inva  set estadofinal ='Incompleto' where nro_legal  = :nro_legal")
    ControlFlota cambioestadoinvaI(String nro_legal);
    @Query(nativeQuery = true, value = "update control_flota  set estado_final  ='Conformado' where shipment  = :nro_legal")
    ControlFlota cambioEstadoCF(String nro_legal);

    @Query(nativeQuery = true, value = "update control_flota  set estado_final  ='Incompleto' where shipment  = :nro_legal")
    ControlFlota cambioestadoCFI(String nro_legal);

    @Query(nativeQuery = true, value = "UPDATE inva SET confpod=:newpod  WHERE nro_legal =:nro_legal")
    void cambioPODINVA(String nro_legal, String newpod);

    @Query(nativeQuery = true, value = "UPDATE control_flota cf SET confpod=:newpod  WHERE cf.shipment =:nro_legal")
    void  cambioPODCF(String nro_legal, String newpod);

    @Query(nativeQuery = true, value = "UPDATE inva SET facturaurl = :facturaUrl ,estadofinal ='Incompleto' WHERE nro_legal = :nro_legal")
    ControlFlota updateFacturaUrlinva(@Param("nro_legal") String nro_legal, @Param("facturaUrl") String facturaUrl);

    @Query(nativeQuery = true, value = "UPDATE control_flota  SET facturaurl = :facturaUrl ,estado_final ='Incompleto' WHERE shipment = :nro_legal")
    ControlFlota updateFacturaUrlCF(@Param("nro_legal") String nro_legal, @Param("facturaUrl") String facturaUrl);

    @Query(nativeQuery = true, value = "UPDATE inva SET vale_palleturl = :facturaUrl ,estadofinal ='Incompleto' WHERE nro_legal = :nro_legal")
    ControlFlota updateValePalletUrlinva(@Param("nro_legal") String nro_legal, @Param("facturaUrl") String facturaUrl);

    @Query(nativeQuery = true, value = "UPDATE control_flota  SET vale_palleturl= :facturaUrl , estado_final  ='Incompleto' WHERE shipment  = :nro_legal")
    ControlFlota updateValePalletUrlCF(@Param("nro_legal") String nro_legal, @Param("facturaUrl") String facturaUrl);

    @Query(nativeQuery = true, value = "select distinct(cf.cliente) nombre from control_flota cf")
     List<Object[]> findClienteDistinct();

    @Query(nativeQuery = true, value = "select distinct(cf.empresa) nombre from control_flota cf where cf.empresa is not null")
    List<Object[]> findEmpresaDistinct();

    @Query(nativeQuery = true, value = "select sum(conformado) conformado, sum(incompleto) incompleto, sum(nopresentado) nopresentado from(select sum(conformado) conformado, sum(incompleto) incompleto, sum(nopresentado) nopresentado from (select case when estadofinal  = 'Conformado' then count(estadofinal) end conformado, case when estadofinal  = 'Incompleto' then count(estadofinal) end incompleto, case when estadofinal  = 'No Presentado' then count(estadofinal) end nopresentado from inva group by estadofinal ) f union all select sum(conformado) conformado, sum(incompleto) incompleto, sum(nopresentado) nopresentado from(select case when estado_final  = 'Conformado' then count(estado_final) end conformado, case when estado_final  = 'Incompleto' then count(estado_final) end incompleto, case when estado_final  = 'No Presentado' then count(estado_final) end nopresentado from control_flota group by estado_final) f)d")
    List<Object[]> getFiltroClienteALL();

    @Query(nativeQuery = true, value = "select sum(conformado) conformado, sum(incompleto) incompleto, sum(nopresentado) nopresentado from (select case when estado_final  = 'Conformado' then count(estado_final) else 0 end conformado, case when estado_final  = 'Incompleto' then count(estado_final) else 0 end incompleto, case when estado_final  = 'No Presentado' then count(estado_final) else 0  end nopresentado from control_flota where( cliente = :nomclic)and((empresa = :empresa) or (:empresa = 'null')) group by estado_final) f")
    List<Object[]> getFiltroClienteOtros(String nomclic, String empresa);

    @Query(nativeQuery = true, value = "select sum(conformado) conformado, sum(incompleto) incompleto, sum(nopresentado) nopresentado from (select case when estadofinal  = 'Conformado' then count(estadofinal) else 0 end conformado, case when estadofinal  = 'Incompleto'then count(estadofinal) else 0 end incompleto, case when i.estadofinal  = 'No Presentado' then count(estadofinal) else 0  end nopresentado, cf.empresa from inva i LEFT JOIN control_flota cf ON i.dt  = cf.shipment where ((cf.empresa = :empresa) or (:empresa = 'null')) group by i.estadofinal,cf.empresa)f")
    List<Object[]> getFiltroClienteUnilever(String empresa);

    @Query(nativeQuery = true, value = "select tiempodemora, sum(cantidad) cantidad from (SELECT case when current_date - TO_DATE(f_descarga, 'DD-MM-YYYY') between 0 and 7 then '0 a 7 dias' when current_date - TO_DATE(f_descarga, 'DD-MM-YYYY') between 8 and 15 then '8 a 15 dias' when current_date - TO_DATE(f_descarga, 'DD-MM-YYYY') between 16 and 30 then '16 a 30 dias' when current_date - TO_DATE(f_descarga, 'DD-MM-YYYY') between 31 and 60 then 'mas de 1 mes' when current_date - TO_DATE(f_descarga, 'DD-MM-YYYY') between 61 and 90 then 'mas de 2 meses' else 'mas de 3 meses' end tiempodemora, count(cf.id) cantidad FROM control_flota cf LEFT JOIN Inva i ON i.dt  = cf.shipment WHERE (cf.cliente = :clientep and (cf.estado_final not in ('Conformado') or i.estadofinal not in ('Conformado')))and((cf.empresa = :empresa) or (:empresa = 'null')) group by 1) d group by 1")
    List<Object[]> getPendientes(String clientep ,String empresa);

    @Query(nativeQuery = true, value = "select tiempodemora, sum(cantidad) cantidad from ( SELECT case when current_date - TO_DATE(f_descarga, 'DD-MM-YYYY') between 0 and 7 then '0 a 7 dias'  when current_date - TO_DATE(f_descarga, 'DD-MM-YYYY')  between 8 and 15 then '8 a 15 dias'  when current_date - TO_DATE(f_descarga, 'DD-MM-YYYY') between 16 and 30 then '16 a 30 dias'  when current_date - TO_DATE(f_descarga, 'DD-MM-YYYY') between 31 and 60 then 'mas de 1 mes' when current_date - TO_DATE(f_descarga, 'DD-MM-YYYY') between 61 and 90 then 'mas de 2 meses' else 'mas de 3 meses' end tiempodemora, count(cf.id) cantidad  FROM control_flota cf  WHERE ((cf.empresa = :empresa) or (:empresa = 'null')) group by 1 union all  SELECT case when current_date - TO_DATE(fa_fat_real, 'DD-MM-YYYY') between 0 and 7 then '0 a 7 dias' when current_date - TO_DATE(fa_fat_real, 'DD-MM-YYYY')  between 8 and 15 then '8 a 15 dias'  when current_date - TO_DATE(fa_fat_real, 'DD-MM-YYYY') between 16 and 30 then '16 a 30 dias'  when current_date - TO_DATE(fa_fat_real, 'DD-MM-YYYY') between 31 and 60 then 'mas de 1 mes' when current_date - TO_DATE(fa_fat_real, 'DD-MM-YYYY') between 61 and 90 then 'mas de 2 meses' else 'mas de 3 meses' end tiempodemora, count(i.id) cantidad  FROM inva i  group by 1) d group by 1")
    List<Object[]> getPendientesAll(String empresa);

}