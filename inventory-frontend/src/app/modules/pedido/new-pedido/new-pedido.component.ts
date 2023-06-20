import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { PedidoService } from '../../shared/services/pedido.service';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Pedido } from '../../shared/interfaces/pedido.interface';
import { UtilService } from '../../shared/services/util.service';
import { User } from '../../shared/interfaces/user.interface';
import { UserService } from '../../shared/services/user.service';

@Component({
  selector: 'app-new-pedido',
  templateUrl: './new-pedido.component.html',
  styleUrls: ['./new-pedido.component.css']
})
export class NewPedidoComponent implements OnInit {

  public pedidoForm: FormGroup;
  estadoFormulario: string = "";
  isAdmin: boolean = false;
  users: User[] = [];
  userId: string ='';

  constructor(
    private fb: FormBuilder,
    private pedidoService: PedidoService,
    private userService: UserService,
    private utilService: UtilService,
    private dialogRef: MatDialogRef<NewPedidoComponent>, 
    @Inject(MAT_DIALOG_DATA) public data: any
  ) { 
    console.log(data);
    this.estadoFormulario = "Agregar";
    this.getUsers();

    this.pedidoForm = this.fb.group( {
      fechaPedido: this.getDateNow(),
      fechaEntrega: ['', Validators.required],
      userId: [''],
    });

    if (data != null ){
      this.updateForm(data);
      this.estadoFormulario = "Actualizar";
    }
  }

  ngOnInit(): void {
    this.isAdmin = this.utilService.isAdmin();
  }

  onSave(){

    let data: Pedido = {
      id: 0,
      userName: this.pedidoForm.get('userName')?.value,
      userId: this.isAdmin? this.pedidoForm.get('userId')?.value : sessionStorage.getItem('userId'),
      fechaPedido: this.pedidoForm.get('fechaPedido')?.value,
      fechaEntrega: this.pedidoForm.get('fechaEntrega')?.value
    }

    if (this.data != null ){
      //update registry
      this.pedidoService.updatePedido(data, this.data.id)
              .subscribe( (data: any) =>{
                this.dialogRef.close(1);
              }, (error:any) =>{
                this.dialogRef.close(2);
              })
    } else {
      //create new registry
      this.pedidoService.savePedido(data)
          .subscribe( (data : any) => {
            console.log(data);
            this.dialogRef.close(1);
          }, (error: any) => {
            this.dialogRef.close(2);
          })
    }
    
  }

  onCancel(){
    this.dialogRef.close(3);

  }

  updateForm(data: Pedido){
    this.pedidoForm = this.fb.group( {
      userId: [data.userId, Validators.required],
      fechaPedido: [data.fechaPedido, Validators.required],
      fechaEntrega: [data.fechaEntrega, Validators.required]
    });
  }

  getDateNow(): string{
    const today = new Date();
    const year = today.getFullYear();
    let month: number | string = today.getMonth() + 1;
    let day: number | string = today.getDate();
    if(day < 10) day = '0'+ day;
    if(month < 10) month = '0'+ month;
    const formattedToday = `${year}-${month}-${day}`;
    console.log(formattedToday);
    return formattedToday;
  }

  getUserId(): any{
    let userId: string | null ='';
    if(this.isAdmin){
      
    }else{
      userId = sessionStorage.getItem('userId');
    }
    return userId;
  }

  getUsers(){
    this.userService.getUsers().subscribe({
      next: (data) =>{
        console.log(data);
        this.users = data;
      },
      error(err) {
          console.log('Ocorrio un error al leer los usuarios del servicio')
      },
    })
  }

}
