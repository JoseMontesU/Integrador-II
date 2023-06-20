import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CategoryComponent } from '../category/components/category/category.component';
import { ProductComponent } from '../product/product/product.component';
import { HomeComponent } from './components/home/home.component';
import { PedidoComponent } from '../pedido/pedido/pedido.component';
import { DetallePedidoComponent } from '../detalle-pedido/detalle-pedido/detalle-pedido.component';


const childRoutes: Routes = [
    { path: '', component: HomeComponent },
    { path: 'home', component: HomeComponent },
    { path: 'category', component: CategoryComponent },
    { path: 'product', component: ProductComponent },
    { path: 'pedido', component: PedidoComponent },
    { path: 'detalle-pedido', component: DetallePedidoComponent },
]

@NgModule({
    imports: [RouterModule.forChild(childRoutes)],
    exports: [RouterModule]
})
export class RouterChildModule { }
