import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSnackBar, MatSnackBarRef, SimpleSnackBar } from '@angular/material/snack-bar';
import { MatTableDataSource } from '@angular/material/table';
import { UtilService } from '../../shared/services/util.service';
import { PedidoService } from '../../shared/services/pedido.service';
import { Pedido } from '../../shared/interfaces/pedido.interface';
import { NewPedidoComponent } from '../new-pedido/new-pedido.component';
import { Router } from '@angular/router';

@Component({
  selector: 'app-pedido',
  templateUrl: './pedido.component.html',
  styleUrls: ['./pedido.component.css']
})
export class PedidoComponent implements OnInit{

  isAdmin: any = false;

  constructor(
    private router: Router,
    private pedidoService: PedidoService,
    public dialog: MatDialog,
    private snackBar: MatSnackBar,
    private util: UtilService) { }

  ngOnInit(): void {
    this.isAdmin = this.util.isAdmin();
    this.getPedidos();
  }

  displayedColumns: string[] = ['id', 'userName', 'fechaPedido', 'fechaEntrega','actions'];
  dataSource = new MatTableDataSource<Pedido>();

  @ViewChild(MatPaginator)
  paginator!: MatPaginator;

  getPedidos(){
    this.pedidoService.getPedidos()
    .subscribe({
      next: (data: Pedido[]) =>{
        const dataFiltrada =data.filter(x => x.userId == sessionStorage.getItem('userId'));
        this.isAdmin?
        this.dataSource = new MatTableDataSource<Pedido>(data):
        this.dataSource = new MatTableDataSource<Pedido>(dataFiltrada);
        this.dataSource.paginator = this.paginator;
      },
      error: (err) => {
        console.log("Ocurrio un error con el servicio de pedidos");
      }
    })
  }

  openCategoryDialog(){
    const dialogRef = this.dialog.open(NewPedidoComponent , {
      width: '450px'
    });

    dialogRef.afterClosed().subscribe((result:any) => {
      
      if( result == 1){
        this.openSnackBar("Pedido Agregado", "Exitoso");
        this.getPedidos();
      } else if (result == 2) {
        this.openSnackBar("Se produjo un error al guardar el Pedido", "Error");
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

  edit(id:number, userName: string, userId: string, fechaPedido: Date, fechaEntrega: Date){
    const dialogRef = this.dialog.open(NewPedidoComponent , {
      width: '450px',
      data: {id: id, userName: userName, userId: userId, fechaPedido: fechaPedido, fechaEntrega: fechaEntrega}
    });

    dialogRef.afterClosed().subscribe((result:any) => {
      
      if( result == 1){
        this.openSnackBar("Actualización", "Exitosa");
        this.getPedidos();
      } else if (result == 2) {
        this.openSnackBar("Se produjo un error al actualizar", "Error");
      }
    });
  }

  irDetallePedido(id: number){
    // console.log(id);
    this.router.navigate(['/dashboard','detalle-pedido'],
    {queryParams: {id: id}});
  }

  delete(id: any){
    // const dialogRef = this.dialog.open(ConfirmComponent , {
    //   data: {id: id, module: "category"}
    // });

    // dialogRef.afterClosed().subscribe((result:any) => {
      
    //   if( result == 1){
    //     this.openSnackBar("Categoria Eliminada", "Exitosa");
    //     this.getCategories();
    //   } else if (result == 2) {
    //     this.openSnackBar("Se produjo un error al eliminar categoria", "Error");
    //   }
    // });
  }

}

