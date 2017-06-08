import {Component} from "@angular/core";
import {Router} from "@angular/router";
import {ToastsManager} from "ng2-toastr";
import {UserService} from "../services/users.service";
import {FormBuilder, FormControl, Validators} from "@angular/forms";

@Component({
  moduleId: module.id,
  selector: 'signup',
  templateUrl: 'signup.component.html',
  styleUrls: ['../users.component.css']
})

export class SignupComponent {

  constructor(private toastr: ToastsManager, private userService: UserService, private router: Router, private formBuilder: FormBuilder) {
  }

  emailRegex = '^[a-z0-9]+(\.[_a-z0-9]+)*@[a-z0-9-]+(\.[a-z0-9-]+)*(\.[a-z]{2,15})$';

  public signupForm = this.formBuilder.group({
    firstName: new FormControl("", Validators.required),
    lastName: new FormControl("", Validators.required),
    username: new FormControl("", Validators.required),
    email: new FormControl("", [Validators.required, Validators.pattern(this.emailRegex)]),
    password: new FormControl("", Validators.required),
    repeatPassword: new FormControl("", Validators.required)
  });

  public signup(event): void {
    let formData = this.signupForm.value;
    if(formData.password != formData.repeatPassword){
        this.toastr.error('Passwords must be the same');
    } else {
      this.userService.createUser(formData.firstName, formData.lastName, formData.username, formData.email, formData.password).then( result => {
        if (result) {
          this.router.navigate(['/']).then(() => {
            this.toastr.info('You have successfully signed up.');
          });
        }
      }).catch(error => {
        this.toastr.error(error);
      });
    }
  }

}
