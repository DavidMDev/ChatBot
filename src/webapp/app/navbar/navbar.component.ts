// core/navbar.component.ts
import { Component } from '@angular/core';
import {HttpService} from "../http/http.service";
import {ToastsManager} from "ng2-toastr";
@Component({
  selector: 'component-navbar',
  templateUrl: './navbar.component.html'
})
export class NavbarComponent {

  constructor(private toastr: ToastsManager, private httpService: HttpService){
  }

  isIn = false;   // store state
  public toggleState() { // click handler
    let bool = this.isIn;
    this.isIn = bool === false ? true : false;
  }

  public isLogged(){
    return this.httpService.userLogged;
  }

  public disconnect(){
    this.httpService.logOut();
    this.toastr.info('You have successfully logged out.');
  }
}
