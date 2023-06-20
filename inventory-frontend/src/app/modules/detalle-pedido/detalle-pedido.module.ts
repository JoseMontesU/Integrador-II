import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DetallePedidoComponent } from './detalle-pedido/detalle-pedido.component';
import { MaterialModule } from '../shared/material.module';
import { FlexLayoutModule } from '@angular/flex-layout';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NewDetallePedidoComponent } from './new-detalle-pedido/new-detalle-pedido.component';



@NgModule({
  declarations: [
    DetallePedidoComponent,
    NewDetallePedidoComponent,
  ],
  imports: [
    CommonModule,
    MaterialModule,
    FlexLayoutModule,
    FormsModule,
    ReactiveFormsModule
  ]
})
export class DetallePedidoModule { }
