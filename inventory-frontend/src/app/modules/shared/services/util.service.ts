import { Injectable } from '@angular/core';
import { KeycloakService } from 'keycloak-angular';

@Injectable({
  providedIn: 'root'
})
export class UtilService {

  constructor(private keycloakService: KeycloakService) { }

  getRoles(){
    return this.keycloakService.getUserRoles();
  }

  getToken(){
    return this.keycloakService.getToken().then(x => {
      sessionStorage.setItem('token',x);
      this.getUserId();
    });
  }

  isAdmin(){
    let roles = this.keycloakService.getUserRoles().filter( role => role == "admin");
    if (roles.length > 0) 
      return true;
    else 
      return false;
  }

  getUserId(){
    return this.keycloakService.loadUserProfile()
    .then(x => {
      sessionStorage.setItem('userId',x.id? x.id:'');
    })
    .catch(err =>{
      console.log(err);
    });
  }
}
