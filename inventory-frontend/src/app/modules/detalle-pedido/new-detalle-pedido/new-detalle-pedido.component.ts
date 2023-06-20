import { Component, OnInit, Inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { DetallePedidoService } from '../../shared/services/detalle-pedido.service';
import { UtilService } from '../../shared/services/util.service';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { DetallePedido } from '../../shared/interfaces/detalle-pedido.interface';
import { ProductService } from '../../shared/services/product.service';
import { Category, ProductElement, Producto } from '../../shared/interfaces/producto.interface';
import { CategoryService } from '../../shared/services/category.service';
import { ActivatedRoute } from '@angular/router';


@Component({
  selector: 'app-new-detalle-pedido',
  templateUrl: './new-detalle-pedido.component.html',
  styleUrls: ['./new-detalle-pedido.component.css']
})
export class NewDetallePedidoComponent implements OnInit {

  public detallePedidoForm: FormGroup;
  estadoFormulario: string = "";
  products: ProductElement[] = [];
  categories: Category[] = [];
  categoryId: number = 0;
  pedidoId: number = 0;
  isAdmin: boolean = false;

  constructor(
    private fb: FormBuilder,
    private activatedRoute:ActivatedRoute,
    private detallePedidoService: DetallePedidoService,
    private utilService: UtilService,
    private productService: ProductService,
    private categoryService: CategoryService,
    private dialogRef: MatDialogRef<NewDetallePedidoComponent>, 
    @Inject(MAT_DIALOG_DATA) public data: any
  ) { 
    console.log(data);
    this.readParam();
    this.estadoFormulario = "Agregar";

    this.detallePedidoForm = this.fb.group( {
      pedidoId: this.pedidoId == undefined? ['', Validators.required]: this.pedidoId,
      productoId: ['', Validators.required],
      // productoName: ['', Validators.required],
      cantidad: ['', Validators.required],
    });

    if (data != null ){
      this.updateForm(data);
      this.estadoFormulario = "Actualizar";
    }
  }

  onSave(){

    let data: DetallePedido = {
      id: 0,
      pedidoId: this.detallePedidoForm.get('pedidoId')?.value,
      productoId: this.detallePedidoForm.get('productoId')?.value,
      productoName: '',
      cantidad: this.detallePedidoForm.get('cantidad')?.value
    }

    if (this.data != null ){
      //update registry
      this.detallePedidoService.updateDetallePedido(data, this.data.id)
              .subscribe( (data: any) =>{
                this.dialogRef.close(1);
              }, (error:any) =>{
                this.dialogRef.close(2);
              })
    } else {
      //create new registry
      this.detallePedidoService.saveDetallePedido(data)
          .subscribe( (data : any) => {
            console.log(data);
            this.dialogRef.close(1);
          }, (error: any) => {
            this.dialogRef.close(2);
          })
    }
    
  }

  ngOnInit(): void {
    // this.getProductos();
    this.getCategories();
    this.isAdmin = this.utilService.isAdmin();
  }
  
  onCancel(){
    this.dialogRef.close(3);

  }

  updateForm(data: DetallePedido){
    this.detallePedidoForm = this.fb.group( {
      pedidoId: [data.pedidoId, Validators.required],
      productoId: [data.productoId, Validators.required],
      // productoName: [data.productoName, Validators.required],
      cantidad: [data.cantidad, Validators.required]
    });
  }

  getProductos(categoryId: number){
    this.productService.getProducts()
        .subscribe({
          next: (x: any) =>{
            this.products = x.product.products;
            this.products = this.products.filter(x => 
              x.category.id == categoryId
            );
            console.log(this.products);
          },
          error: (err) =>{
            console.log("error al consultar productos");
          }
        })
  }
  getCategories(){
    this.categoryService.getCategories()
        .subscribe({
          next: (x: any) =>{
            this.categories = x.categoryResponse.category;
            console.log(this.categories);
          },
          error: (err) =>{
              console.log("error al consultar categorias");
          },
        })
  }

  onChangeCategory(value: any){
    console.log(value.id);
    this.getProductos(value.id)
  }

  readParam(){
    this.activatedRoute.queryParams
    .subscribe(params =>{
      this.pedidoId = params['id'];
      // console.log(`hola: ${this.pedidoId}`);
    });
  }

}
