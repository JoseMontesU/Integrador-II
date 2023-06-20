import { Component, OnInit, ViewChild } from '@angular/core';
import { DetallePedidoService } from '../../shared/services/detalle-pedido.service';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar, MatSnackBarRef, SimpleSnackBar } from '@angular/material/snack-bar';
import { UtilService } from '../../shared/services/util.service';
import { DetallePedido } from '../../shared/interfaces/detalle-pedido.interface';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { NewDetallePedidoComponent } from '../new-detalle-pedido/new-detalle-pedido.component';
import { ConfirmComponent } from '../../shared/components/confirm/confirm.component';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-detalle-pedido',
  templateUrl: './detalle-pedido.component.html',
  styleUrls: ['./detalle-pedido.component.css']
})
export class DetallePedidoComponent implements OnInit {

  isAdmin: any = true;
  pedidoId: number=0;
  detallePedidos: DetallePedido[] = [];

  constructor(
    private activatedRoute: ActivatedRoute,
    private detallePedidoService: DetallePedidoService,
    public dialog: MatDialog,
    private snackBar: MatSnackBar,
    private util: UtilService) { }

  ngOnInit(): void {
    this.readParam();
    this.getDetallePedidos();
  }

  displayedColumns: string[] = ['id', 'pedidoId', 'productoName','cantidad','actions'];
  dataSource = new MatTableDataSource<DetallePedido>();

  @ViewChild(MatPaginator)
  paginator!: MatPaginator;

  getDetallePedidos(){
    if(this.pedidoId == undefined){
      this.detallePedidoService.getDetallePedidos()
    .subscribe({
      next: (data: DetallePedido[]) =>{
        this.detallePedidos = data;
        console.log("respuesta pedidos: ",data);
        this.dataSource = new MatTableDataSource<DetallePedido>(data);
        this.dataSource.paginator = this.paginator;
      },
      error: (err) => {
        console.log("Ocurrio un error con el servicio de pedidos");
      }
    })
    }else {
      this.detallePedidoService.getDetallePedidosByPedidoId(this.pedidoId)
      .subscribe({
        next: (data: DetallePedido[]) =>{
          this.detallePedidos = data;
          console.log("respuesta pedidos: ",data);
          this.dataSource = new MatTableDataSource<DetallePedido>(data);
          this.dataSource.paginator = this.paginator;
        },
        error: (err) => {
          console.log("Ocurrio un error con el servicio de pedidos");
        }
      })
    }
  }

  openDetallePedidoDialog(){
    const dialogRef = this.dialog.open(NewDetallePedidoComponent , {
      width: '450px'
    });

    dialogRef.afterClosed().subscribe((result:any) => {
      
      if( result == 1){
        this.openSnackBar("Producto Agregado", "Exitoso");
        this.getDetallePedidos();
      } else if (result == 2) {
        this.openSnackBar("Se produjo un error al agregar el Producto", "Error");
      }
    });
  }

  openSnackBar(message: string, action: string) : MatSnackBarRef<SimpleSnackBar>{
    return this.snackBar.open(message, action, {
      duration: 2000
    })
  }

  buscar( termino: string){
    // if( termino.length === 0){
    //   return this.getCategories();
    // }

    // // this.categoryService.getCategorieById(termino)
    // this.categoryService.getCategoryByName(termino)
    //         .subscribe( (resp: any) => {
    //           this.processCategoriesResponse(resp);
    //         })
  }

  exportExcel(){

    // this.categoryService.exportCategories()
    //     .subscribe( (data: any) => {
    //       let file = new Blob([data], {type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'});
    //       let fileUrl = URL.createObjectURL(file);
    //       var anchor = document.createElement("a");
    //       anchor.download = "categories.xlsx";
    //       anchor.href = fileUrl;
    //       anchor.click();

    //       this.openSnackBar("Archivo exportado correctamente", "Exitosa");
    //     }, (error: any) =>{
    //       this.openSnackBar("No se pudo exportar el archivo", "Error");
    //     })

  }

  edit(id:number, pedidoId: number, productoId: number, productoName: string, cantidad: number){
    const dialogRef = this.dialog.open(NewDetallePedidoComponent , {
      width: '450px',
      data: {id: id, pedidoId: pedidoId, productoId: productoId, productoName: productoName, cantidad: cantidad}
    });

    dialogRef.afterClosed().subscribe((result:any) => {
      
      if( result == 1){
        this.openSnackBar("Actualización", "Exitosa");
        this.getDetallePedidos();
      } else if (result == 2) {
        this.openSnackBar("Se produjo un error al actualizar categoria", "Error");
      }
    });
  }

  delete(id: any){
    const dialogRef = this.dialog.open(ConfirmComponent , {
      data: {id: id, module: "detalle-pedido"}
    });

    dialogRef.afterClosed().subscribe((result:any) => {
      
      if( result == 1){
        this.openSnackBar("Producto Eliminado", "Exitoso");
        this.getDetallePedidos();
      } else if (result == 2) {
        this.openSnackBar("Se produjo un error al eliminar el producto", "Error");
      }
    });
  }

  readParam(){
    this.activatedRoute.queryParams
    .subscribe(params =>{
      this.pedidoId = params['id'];
      console.log(`parametro: ${this.pedidoId}`);
    });
  }

}
