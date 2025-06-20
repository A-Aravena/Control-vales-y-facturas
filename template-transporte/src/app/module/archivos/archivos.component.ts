import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ArchivosService } from 'src/app/services/archivos.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-archivos',
  templateUrl: './archivos.component.html',
  styleUrls: ['./archivos.component.css']
})
export class ArchivosComponent implements OnInit {
 
  selectedFile: File | null = null;

  constructor( private archivoService: ArchivosService) { }

  ngOnInit(): void {
  }


  onFileSelected(event: any) {
    this.selectedFile = event.target.files[0] as File;
  }
 
  uploadFile(nombre: any) {
    if (!this.selectedFile) {
      alert('Please select a file to upload.');
      return;
    }

    if(nombre == 'controlflota'){
      this.archivoService.uploadFileControl(this.selectedFile).subscribe(
        (data) => {
          Swal.fire({
          title: 'Archivo Subido',
          text: 'Archivo Control Flota subido con exito',
          icon: 'success',
          confirmButtonText: 'OK'
          });
        },
        (error) => {
          Swal.fire({
            title: 'Error',
            text: 'Archivo Control Flota subido con filas duplicada y/o formato incorrecto',
            icon: 'error',
            confirmButtonText: 'OK'
            });
        }
      );
    }

    if(nombre == 'inva'){
      this.archivoService.uploadFileInva(this.selectedFile).subscribe(
        (data) => {
          Swal.fire({
            title: 'Archivo Subido',
            text: 'Archivo Inva subido con exito',
            icon: 'success',
            confirmButtonText: 'OK'
          });
    
        },
        (error) => {
          console.error('Error uploading file:', error);
        }
    
      );
    }
  }


}
