import {Component} from "@angular/core";
import {HttpService} from "../../http/http.service";
import {Router} from "@angular/router";
import {ToastsManager} from "ng2-toastr";
import {FormBuilder, Validators, FormGroup, FormControl} from "@angular/forms";

@Component({
  moduleId: module.id,
  selector: 'login',
  templateUrl: 'login.component.html',
  styleUrls: ['../users.component.css']
})

export class LoginComponent {

  constructor(private toastr: ToastsManager, private httpService: HttpService, private router: Router, private formBuilder: FormBuilder) {
  }

  public loginForm = this.formBuilder.group({
    username: new FormControl("", Validators.required),
    password: new FormControl("", Validators.required)
  });

  public login(event) {
    let formData = this.loginForm.value;
    this.httpService.login(formData.username, formData.password).then(result => {
      if (result) {
        this.router.navigate(['/']).then(() => {
          this.toastr.info('You have successfully logged in.');
        });
      }
    }).catch(error => {
      this.toastr.error(error);
    });
  }
}
