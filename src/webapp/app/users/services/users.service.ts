import {Injectable} from "@angular/core";
import {User} from "../user";

import 'rxjs/add/operator/toPromise';
import {HttpService} from "../../http/http.service";
import {Telephone} from "../telephones/telephone";
import {Address} from "../addresses/address";

@Injectable()
export class UserService {
  usersUrl = "users/";

  constructor(private httpService: HttpService) {
  }

  getUsers(): Promise<User[]> {
    return this.httpService.get(this.usersUrl)
      .then(res => {
          <User[]>(res.json());
        }
      ).catch(this.handleError);
  }

  deleteUser(id: number) {
    const url = `${this.usersUrl}${id}`;
    return this.httpService.delete(url)
      .then(res => <User[]>(res.json()))
      .catch(this.handleError);
  }

  createUser(firstName: string, lastName: string, username: string, email: string, password: string): Promise<User[]> {
    return this.httpService
      .post(this.usersUrl, {firstName: firstName, lastName: lastName,username: username, password: password, email: email})
      .then(res => <User[]>(res.json()))
      .catch(this.handleError);
  }

  private handleError(error: any): Promise<any> {
    console.error('An error occurred', error);
    return Promise.reject(error.message || error);
  }

  public getUser(id: number) {
    const url = `${this.usersUrl}${id}`;
    return this.httpService.get(url)
      .then(res => <User>(res.json()))
      .catch(this.handleError);
  }

  public modifyUser(object: any) {
    const url = `/me/edit`;
    return this.httpService.put(url, object)
      .then(res => <User>(res.json()))
      .catch(this.handleError);
  }

  public getMyProfile() {
    const url = `me`;
    return new Promise((resolve, reject) => {
      this.httpService.get(url)
        .then(res => {
          console.log('user : ', res.json());
          let user = this.mapUser(res);
          resolve(user);
        }).catch(reject);
    })
      .catch(this.handleError);
  }

  //map a user object
  private mapUser(res) {
    let obj = res.json();
    let user = new User();
    user.username = obj.username;
    user.firstName = obj.firstName;
    user.lastName = obj.lastName;
    user.email = obj.email;
    user.id = obj.id;
    user.roles = obj.roles;
    console.log(user);
    return user;
  }
}
