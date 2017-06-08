
import {Routes, RouterModule} from "@angular/router";
import {NgModule} from "@angular/core";
import {UsersComponent} from "./users/profile/users-profile.component";
import {LoginComponent} from "./users/login/login.component";
import {AppComponent} from "./app.component";
import {HomeComponent} from "./home/home.component";
import {SignupComponent} from "./users/login/signup.component";
import {TelephoneDetailComponent} from "./users/telephones/telephone-detail.component";
import {AddressDetailComponent} from "./users/addresses/address-detail.component";
import {UserEditComponent} from "./users/profile/user-profile-edit.component";
import {ChatComponent} from "./chat/chat.component";

const routes: Routes = [
  { path: '', redirectTo: '', pathMatch: 'full', component: HomeComponent},
  { path: 'profile/:id', component: UsersComponent },
  { path: 'profile/me', component: UsersComponent},
  { path: 'login', component: LoginComponent },
  { path: 'signup', component: SignupComponent},
  { path: 'telephone/:id', component: TelephoneDetailComponent},
  { path: 'address/:id', component: AddressDetailComponent},
  { path: 'profile/me/edit', component: UserEditComponent},
  { path: 'chat', component: ChatComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule {}
