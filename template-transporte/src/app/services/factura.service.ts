import { HttpClient, HttpParams  } from '@angular/common/http';
import { Injectable } from '@angular/core';
import baserUrl from './helper';
import { Observable } from 'rxjs';



export interface Comentarios {
  idControlFlota:string;
 responsable :string;
 comentarios :string;
}

export interface ClienteEmpresa {
  nombre: string;
}
@Injectable({
  providedIn: 'root'
})


export class FacturaService {

  
  constructor(private http: HttpClient) { }

  addcomentario(data: Comentarios):Observable<any>{

    return this.http.post<Comentarios>(`${baserUrl}/controlFlota/addcomentario`,data);
 }
 

  getFactura(dt: string, cliente_final: string ,nro_legal: string , estado_final : string,
    cant_cajas :string, desc_solicitante:string, fa_fat_real:string, confpod:string, f_descarga:string , empresa:any,empresa_:any) : Observable<any> {

      console.log("ESTO ES GET FACTURA inicio!!!")

    const params = new HttpParams().set('dtp', dt).set('clientep',cliente_final).set('nrolegalp',nro_legal).set('estadofinalp',estado_final)
    .set('cantCajasP', cant_cajas).set('descSolicitantep', desc_solicitante).set('fafatrealp', fa_fat_real).set('confpodp', confpod).set('fdescargap', f_descarga)
    .set('empresa', empresa).set('empresa_',empresa_);
    console.log("Esto es params de getFActura:" +params)

    
    return this.http.get<any>(`${baserUrl}/controlFlota/controlInva`, {params});
  }



  uploadFileFactura(file:File, nro_legal: any, cliente: any, usuario:string):Observable<any>{
    
    const formData = new FormData();
    formData.append('imageFile', file);
    console.log("Esto es upload services" + formData)

 
    return this.http.post<any>(`${baserUrl}/controlFlota/uploadFactura/${nro_legal}/${cliente}/${usuario}`, formData);
}



uploadFileValePallet(file:File, nro_legal: any, cliente: any , usuario:string):Observable<any>{
  const formData = new FormData();
  formData.append('imageFile', file);

  return this.http.post<any>(`${baserUrl}/controlFlota/uploadValePallet/${nro_legal}/${cliente}/${usuario}`, formData);
}
    
updatePOD(nro_legal: any,  cliente: string, nuevoPOD: string, usuario:string): Observable<any> {
  const formData = new FormData();
  formData.append('newPOD', nuevoPOD);

  return this.http.put<any>(`${baserUrl}/controlFlota/actualizarPOD/${nro_legal}/${cliente}/${usuario}`,formData);


}
estadoConformar(nro_legal: any, cliente: any, usuario: string): Observable<any> {
  
  return this.http.put<any>(`${baserUrl}/controlFlota/conformar/${nro_legal}/${cliente}/${usuario}`, null);

}


getBitacora(idControlFlota : any){

  return this.http.get<any[]>(`${baserUrl}/controlFlota/bitacora/${idControlFlota}`);

}

getClientes(): Observable<ClienteEmpresa[]>{
  return this.http.get<ClienteEmpresa[]>(`${baserUrl}/controlFlota/clientes`);
}

getEmpresa(): Observable<ClienteEmpresa[]>{
  return this.http.get<ClienteEmpresa[]>(`${baserUrl}/controlFlota/empresa`);
}

filtroCliente(nomclic: string, empresa: any){
  return this.http.get<any[]>(`${baserUrl}/controlFlota/clientes/${nomclic}/${empresa}`);

}

filtroClienteALL(){
  return this.http.get<any[]>(`${baserUrl}/controlFlota/filtroclienteALL`);
}

getPendientes(clientep: string, empresa?: any){
     
  return this.http.get<any[]>(`${baserUrl}/controlFlota/pendiente/${clientep}/${empresa}`);
}
}
