import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewPedidoComponent } from './new-pedido.component';
import {FormBuilder, ReactiveFormsModule} from "@angular/forms";
import {BrowserDynamicTestingModule} from "@angular/platform-browser-dynamic/testing";
import {HttpClientTestingModule} from "@angular/common/http/testing";
import {KeycloakAngularModule, KeycloakService} from "keycloak-angular";
import {MAT_DIALOG_DATA, MatDialogModule, MatDialogRef} from "@angular/material/dialog";
import {UtilService} from "../../shared/services/util.service";

describe('NewPedidoComponent', () => {
  const mockDialogRef = {
    close: jasmine.createSpy('close')
  };
  let component: NewPedidoComponent;
  let fixture: ComponentFixture<NewPedidoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NewPedidoComponent ],
      imports: [
          ReactiveFormsModule,
          HttpClientTestingModule,

      ],
      providers: [
        { provide: MAT_DIALOG_DATA, useValue: {} },
        {
          provide: MatDialogRef,
          useValue: mockDialogRef
        },
        {
          provide: KeycloakService,
          useValue: KeycloakService
        },
        {
          provide: UtilService,
          useValue: UtilService
        },
      ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NewPedidoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
