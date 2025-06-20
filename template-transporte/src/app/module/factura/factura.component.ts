import { Component, OnInit  } from '@angular/core';
import { FacturaService, Comentarios } from 'src/app/services/factura.service';
import Swal from 'sweetalert2';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';





@Component({
  selector: 'app-factura',
  templateUrl: './factura.component.html',
  styleUrls: ['./factura.component.css']
})
export class FacturaComponent implements OnInit {

  facturas: any[] = [];
  bitacora!: any;
  roles: string[] = JSON.parse(localStorage.getItem('roles') || '[]');
  selectedFactura: any;
  selectedbitacora: any;

  id!: any;
  estado_final!: any;
  dt!: any;
  nro_legal!: any;
  cliente_final!: any;
  cliente!:any;
  empresa_!:any;
  cantidad_cajas!: any;
  direccion!: any;
  desc_solicitante!: any;
  fa_fat_real!: any;
  confpod!: any;
  f_descarga!: any;
  usuario!: any;
  vale_palleturl!: any;
  facturaurl!: any;
  nombres!: string | null;
  comentario!: any;
  actividad!: any;
  fechabitacora!: any;
  responsable!: any;

  empresa = localStorage.getItem('empresa');
  token = localStorage.getItem('accessToken');


  constructor(private factura:FacturaService, private httpClient: HttpClient, private sanitizer: DomSanitizer) { }
  
 
  hasRole(role: string): boolean {
    return this.roles.includes(role);
  }


  verDetallesFactura(factura: any) {
    this.id = factura.id;
   
    this.selectedFactura = factura;
    this.selectedbitacora = factura;  
    this.cliente = factura.cliente;
    if(this.cliente === 'UNILEVER') this.nro_legal = factura.nro_legal;
    else this.nro_legal = factura.dt;
    this.dt = factura.dt;
    this.facturaurl = this.sanitizer.bypassSecurityTrustResourceUrl(factura.facturaurl);
    this.vale_palleturl=this.sanitizer.bypassSecurityTrustResourceUrl(factura.vale_palleturl);
    //this.facturaurl = factura.facturaurl;
    console.log("token desde verDetallesFactura: " + this.token)
  }
  saveComentario(){
    this.usuario = localStorage.getItem('nombres');


    if(this.cliente == "UNILEVER"){

      this.nro_legal = this.nro_legal;
    }else {
      this.nro_legal =this.dt;
    }
   
     const createComentario:Comentarios={
      idControlFlota:this.nro_legal,
      responsable :this.usuario,
      comentarios :this.comentario
     }

    
  this.factura.addcomentario(createComentario).subscribe(
    (data) => {
      Swal.fire({
        title: 'Comentario agregado',
        text: 'Comentario agregado con éxito',
        icon: 'success',
        confirmButtonText: 'OK'            
        
      });
    },
    (error) => {
      console.log(error);
      Swal.fire({
         
        title: 'Error',
        text: 'Error al agregar Comentario',
        icon: 'error',
        confirmButtonText: 'OK'     
        
      });
    }

        );


  }


  Conformar(factura: any){

    this.id = factura.id;
    this.cliente = factura.cliente;
    this.nro_legal = factura.nro_legal;
    this.dt = factura.dt;
    this.usuario = localStorage.getItem('nombres');

     if(this.cliente == "UNILEVER"){

      this.nro_legal = this.nro_legal;
    }else {
      this.nro_legal =this.dt;
    }

    Swal.fire({
      title: '¿Desea conformar?',
      text: " ",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Conformar'
    }).then((result) => {
    if (result.isConfirmed) {
      this.factura.estadoConformar(this.nro_legal, this.cliente, this.usuario).subscribe(
        (data) => {
        }
      );
      Swal.fire(
      'Factura Conformada',
      'Estado cambiado a Conformado',
      'success'
    )
  }     
  })
    this.getHistorial()
  }
  
  selectedFile: File | null = null;

  ngOnInit(): void {
  
  }

 

  savePOD(){
      const fechaFormateada = this.formatoFecha(this.confpod);
      
      this.confpod= fechaFormateada;
      this.usuario = localStorage.getItem('nombres');
      

        if(this.cliente == "UNILEVER"){

          this.nro_legal = this.nro_legal;
        }else {
          this.nro_legal =this.dt;
        }
        
        this.factura.updatePOD(this.nro_legal, this.cliente, fechaFormateada , this.usuario).subscribe(
          (data) => {
            Swal.fire({
              title: 'POD actualizado',
              text: 'Fecha POD actualizada con éxito',
              icon: 'success',
              confirmButtonText: 'OK'            
              
            });
          },
          (error) => {
            console.log(error);
            Swal.fire({
               
              title: 'Error',
              text: 'Error al actualizar la fecha POD',
              icon: 'error',
              confirmButtonText: 'OK'     
              
            });
          }
        );

        this.getHistorial()

  }
  
  onFileSelected(event: any) {
    this.selectedFile = event.target.files[0] as File;
  }

  uploadFile(nombre: any) {
    this.usuario = localStorage.getItem('nombres');
    if (!this.selectedFile) {
      alert('Please select a file to upload.');
      return;
    }
    
    if(nombre == 'cargaFactura'){
     
      if(this.cliente == "UNILEVER"){

        this.nro_legal = this.nro_legal;
      }else {
        this.nro_legal =this.dt;
      }
  

      this.factura.uploadFileFactura(this.selectedFile, this.nro_legal, this.cliente ,this.usuario).subscribe(
        (data) => {
         
          Swal.fire({
            title: 'Error',
            text: 'Imagen Factura ya subida y/o formato incorrecto',
            icon: 'error',
            confirmButtonText: 'OK'
          });
        },
        (error) => {
          console.log("Este es el error --->",JSON.stringify(error));
         
          Swal.fire({          
            
            
            title: 'Factura subida',
            text: 'Imagen Factura subido con exito',
            icon: 'success',
            confirmButtonText: 'OK'
            });
            console.log("Este es el error --->"+error)
        }
        
      );
      
    }

    if(nombre == 'cargaValePallet'){

      if(this.cliente == "UNILEVER"){

        this.nro_legal = this.nro_legal;
      }else {
        this.nro_legal =this.dt;
      }
  
      this.factura.uploadFileValePallet(this.selectedFile, this.nro_legal, this.cliente,this.usuario).subscribe(
        (data) => {
          Swal.fire({
            title: 'Error',
            text: 'Vale Pallet ya subida y/o formato incorrecto',
            icon: 'error',
            confirmButtonText: 'OK'
          });
        },
        (error) => {
          
          Swal.fire({          
            
            
            title: 'Factura subida',
            text: 'Vale Pallet subido con exito',
            icon: 'success',
            confirmButtonText: 'OK'
            });
        }
    
      );
    }
  }

  getBitacora(){
    this.factura.getBitacora(this.nro_legal).subscribe(
      (data) => {
        this.bitacora = data;

      },
      (error) => {
        console.log(error);
      }
    );

  }
   

  getHistorial(){
    
    if (this.estado_final == '' || this.estado_final == null) this.estado_final = ''; 
    if (this.dt == '' || this.dt == null) this.dt = ''; else this.dt = this.dt.trim();
    if (this.cliente_final == '' || this.cliente_final == null) this.cliente_final = ''; else this.cliente_final = this.cliente_final.trim().toUpperCase();
    if (this.nro_legal == '' || this.nro_legal == null) this.nro_legal = ''; else this.nro_legal = this.nro_legal.trim();
    if (this.cantidad_cajas == '' || this.cantidad_cajas == null) this.cantidad_cajas = ''; else this.cantidad_cajas = this.cantidad_cajas.trim();
    if (this.desc_solicitante == '' || this.desc_solicitante == null) this.desc_solicitante = ''; else this.desc_solicitante = this.desc_solicitante.trim().toUpperCase();
    if (this.empresa_ == '' || this.empresa_ == null) this.empresa_ = ''; else this.empresa_ = this.empresa_.trim().toUpperCase();

          
    if (this.fa_fat_real != '' && this.fa_fat_real != null && this.fa_fat_real != undefined) {
      
      const fechaFormateada = this.formatoFecha(this.fa_fat_real);
      this.fa_fat_real = fechaFormateada;
    }else{
      this.fa_fat_real= ''
    }

    if (this.confpod != '' && this.confpod != null && this.confpod != undefined) { 
      const fechaFormateada = this.formatoFecha(this.confpod); 
      this.confpod = fechaFormateada;
    } else{
      this.confpod=''
    }

    
    if (this.f_descarga != '' && this.f_descarga != null && this.f_descarga != undefined) { 
      
      const fechaFormateada = this.formatoFecha(this.f_descarga);
      this.f_descarga = fechaFormateada;
    }else{
      this.f_descarga= ''
    }

    if(this.empresa == 'null' || this.empresa== ''){
      this.empresa = 'Todos';
    }

      
    //Prueba de valores entregador por usuario
    console.log("estado: " + this.estado_final)
    console.log("cliente_final: "+ this.cliente_final);
    console.log("Empresa: " + this.empresa_);
    console.log("dt: "+this.dt); 
    console.log("nro_legal: "+this.nro_legal);
    console.log("cant_cajas: "+this.cantidad_cajas);
    console.log("desc_solicitante: "+ this.desc_solicitante);
    console.log("fa_fat_real: "+this.fa_fat_real);
    console.log("confPOD: "+ this.confpod);
    console.log("f_descarga: "+this.f_descarga);
    console.log("empresa usuario: "+ this.empresa);
    
    
    this.factura.getFactura(this.dt , this.cliente_final, this.nro_legal, this.estado_final, this.cantidad_cajas,
      this.desc_solicitante, this.fa_fat_real , this.confpod, this.f_descarga, this.empresa, this.empresa_).subscribe(
      response => {

        this.facturas = response;
      },
      error => {
        console.log(error);
      }
    );

    this.fa_fat_real= undefined
    this.confpod= undefined
    this.f_descarga= undefined
    
  }


  formatoFecha(fecha: string): string {
    
     if(fecha == '' || fecha == null || fecha == undefined){


       // return '1990-01-01';
        return ' ';

     }
    const partes = fecha.split('-');
    return `${partes[2]}-${partes[1]}-${partes[0]}`;
  }

}
