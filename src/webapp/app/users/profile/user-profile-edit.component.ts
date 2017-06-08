import {Component, OnInit} from "@angular/core";
import {ActivatedRoute} from "@angular/router";
import {User} from "../user";
import {UserService} from "../services/users.service";
import { Location }               from '@angular/common';
import {ToastsManager} from "ng2-toastr";

@Component({
  moduleId: module.id,
  templateUrl: 'user-profile-edit.component.html',
  styleUrls: ['../users.component.css']
})

export class UserEditComponent implements OnInit {
  user: User;
  sub: any;


  ngOnInit(): void {
    this.sub = this.route.params.subscribe(params => {
        this.usersService.getMyProfile().then(user => {
          this.user = user;
        }).catch(result => {
          console.log(result);
        });
    });
  }

  constructor(private usersService: UserService, private route: ActivatedRoute, private location: Location, private toastr: ToastsManager) {
  }

  ngOnDestroy(): void {
    this.sub.unsubscribe();
    this.user = null;
  }

  public save(user:User, password, repeatPassword): void{
    if(password){
      if(password == repeatPassword){
        let requestObject = {password:'', lastName: user.lastName, firstName:user.firstName, username: user.username};
        requestObject.password = password;
        this.usersService.modifyUser(requestObject).then((result) => {
          this.toastr.info('Profile saved');
        }).catch((err)=>{
          this.toastr.warning(err.message);
        });
      } else {
        this.toastr.warning('Both passwords must match');
      }
    } else {
      let requestObject = {lastName: user.lastName, firstName:user.firstName, username: user.username};
      this.usersService.modifyUser(requestObject).then((result) => {
        this.toastr.info('Profile saved');
      }).catch((err)=>{
        this.toastr.warning(err.message);
      });
    }

  }

  public back(): void{
    this.location.back();
  }
}
