import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import baserUrl from './helper';

@Injectable({
  providedIn: 'root'
})
export class ArchivosService {

  constructor(private http: HttpClient) { }

  uploadFileControl(file:File):Observable<any>{
      const formData = new FormData();
      formData.append('file', file);
   
      return this.http.post<any>(`${baserUrl}/controlFlota/upload`, formData)
  }

  uploadFileInva(file:File):Observable<any>{
    const formData = new FormData();
    formData.append('file', file);
 
    return this.http.post<any>(`${baserUrl}/inva/upload`, formData)
}
}
