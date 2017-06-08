import {Component, OnInit} from "@angular/core";
import {ActivatedRoute, Router} from "@angular/router";
import {User} from "../user";
import {UserService} from "../services/users.service";

@Component({
  moduleId: module.id,
  selector: 'users',
  templateUrl: 'users-profile.component.html',
  styleUrls: ['../users.component.css']
})

export class UsersComponent implements OnInit {
  user: User;
  sub: any;
  phoneMenu = false;
  addressMenu = false;


  ngOnInit(): void {
    this.sub = this.route.params.subscribe(params => {
      let id = +params['id'];
      if (id === 0) {
        this.usersService.getMyProfile().then(user => {
          this.user = user;
        }).catch(result => {
          console.log(result);
        });
      } else {
        this.usersService.getUser(id).then(user => {
          this.user = user;
        }).catch(result => {
          console.log(result);
        });
      }
    });
  }

  constructor(private usersService: UserService, private route: ActivatedRoute, private router: Router) {
  }

  ngOnDestroy(): void {
    this.sub.unsubscribe();
    this.user = null;
  }

  phonesMenuEnable(): void {
    this.phoneMenu ? this.phoneMenu = false : this.phoneMenu = true;
  }

  addressMenuEnable(): void{
    this.addressMenu ? this.addressMenu = false : this.addressMenu = true;
  }

  public editProfile(): void{
    this.router.navigate(['/profile/me/edit']);
  }
}
