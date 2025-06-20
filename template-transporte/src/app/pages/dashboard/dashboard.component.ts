import { Component, OnInit, ElementRef } from '@angular/core';
import { Observable } from 'rxjs';
import * as echarts from 'echarts';
import { ClienteEmpresa, FacturaService} from 'src/app/services/factura.service';
import { da } from 'date-fns/locale';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit  {
  clientesAll$!: Observable<ClienteEmpresa[]>;
  empresa = localStorage.getItem('empresa');

  constructor(private facturaServices:FacturaService) {}

  amounts: any;
  names: any;
  cantidad: any;
  tiempodemora:any;
  fechaSolicitud: any;
  dataCards!: any;
  nomclic!:any;


  getclienteFiltro(){
    if(this.nomclic == 'Todos'){
      this.facturaServices.filtroClienteALL().subscribe(
        (data) => {
          this.dataCards = data
        },
        (error) => {
          console.log(error); 
        }
      );
    }else{
      this.facturaServices.filtroCliente(this.nomclic,this.empresa).subscribe(
        (data) => {
          this.dataCards = data
        },
        (error) => {
          console.log(error); 
        }
      );
    }
    
    this.getPendientes();
  }
  
  ngOnInit(): void{
    this.clientesAll$ = this.facturaServices.getClientes();
    this.facturaServices.filtroClienteALL().subscribe(
      (data) => {
        this.dataCards = data
      },
      (error) => {
        console.log(error); 
      }
    ); 

    this.getPendientes();
  }  

  getPendientes() {
    if(this.nomclic==null || this.nomclic=='') this.nomclic = 'UNILEVER'
    if(this.empresa==null || this.empresa=='') this.empresa = 'Todos'
    
       
    this.facturaServices.getPendientes(this.nomclic, this.empresa).subscribe((data: any[]) => {
      this.cantidad = data.map(item => item.cantidad);
      this.tiempodemora = data.map(item => item.tiempodemora);
 
      const chartElement2 = document.querySelector("#reportChart");
      if (chartElement2 instanceof HTMLElement) {
        const lineChart = echarts.init(chartElement2);
        const options = {
          xAxis: {
            type: 'category',
            data: this.tiempodemora
          },
          yAxis: {
            type: 'value'
          },
          series: [
            {
              data: this.cantidad,
              type: 'bar',
              symbol: 'circle',
              symbolSize: 10,
              lineStyle: {
                color: '#5470C6',
                width: 1,
                type: 'dashed'
              },
              label: {
                show: true,
                position: 'top'
              },
              itemStyle: {
                borderWidth: 2,
                //borderColor: '#EE6666',
                color: 'blue'
              }
            }
          ]
        };
        lineChart.setOption(options);
      }
    });
  }
}
//aaaaa