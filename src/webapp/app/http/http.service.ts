import {Http, Headers} from "@angular/http";
import {Injectable} from "@angular/core";
import {Router} from "@angular/router";
import {LocalStorageService} from 'angular-2-local-storage';
import {ToastsManager} from "ng2-toastr";
import { environment } from "../../environments/environment";


@Injectable()
export class HttpService {
  contentTypeHeader = 'Content-Type';
  contentTypeValue = 'application/json';
  csrfHeader = 'X-XSRF-TOKEN';
  serverURL = environment.apiHost + '/api/';
  public userLogged = false;

  constructor(private toastr: ToastsManager, private http: Http, private router: Router, private localStorageService: LocalStorageService) {
    this.localStorageService.set('csrf_token', '');
    this.localStorageService.set('logged', false);

  }

  get(url: string): Promise<any> {
    let headers = new Headers();
    headers.set(this.contentTypeHeader, this.contentTypeValue);
    let token = '' + this.localStorageService.get('csrf_token');
    headers.set(this.csrfHeader, token);
    return new Promise((resolve, reject) => {
      this.http.get(this.serverURL + url, {headers: headers}).toPromise().then(result => {
        resolve(result);
      }).catch(error => {
        this.handleError(reject, error);
      });
    });
  }

  put(url: string, obj: Object): Promise<any> {
    let headers = new Headers();
    headers.set(this.contentTypeHeader, this.contentTypeValue);
    let token = '' + this.localStorageService.get('csrf_token');
    headers.set(this.csrfHeader, token);
    return new Promise((resolve, reject) => {
      this.http.put(this.serverURL + url, JSON.stringify(obj), {headers: headers})
        .toPromise().then(result => {
        resolve(result);
      }).catch(error => {
        this.handleError(reject, error);
      });
    });
  }

  post(url: string, obj: Object): Promise<any> {
    let headers = new Headers();
    headers.set(this.contentTypeHeader, this.contentTypeValue);
    let token = '' + this.localStorageService.get('csrf_token');
    headers.set(this.csrfHeader, token);
    return new Promise((resolve, reject) => {
      this.http.post(this.serverURL + url, JSON.stringify(obj), {headers: headers})
        .toPromise().then(result => {
        resolve(result);
      }).catch(error => {
        this.handleError(reject, error);
      });
    });
  }

  delete(url: string): Promise<any> {
    let headers = new Headers();
    headers.set(this.contentTypeHeader, this.contentTypeValue);
    let token = '' + this.localStorageService.get('csrf_token');
    headers.set(this.csrfHeader, token);
    return new Promise((resolve, reject) => {
      this.http.delete(this.serverURL + url, {headers: headers}).toPromise().then(result => {
        resolve(result);
      }).catch(error => {
        this.handleError(reject, error);
      });
    });
  }

  login(username: string, password: string): Promise<any> {
    return new Promise((resolve, reject) => {
      if (username === '' || password === '') {
        reject('Username or password cannot be empty.');
      }
      const authHeader = 'Authorization';
      let authValue = 'Basic ' + btoa(username + ':' + password);
      let headers = new Headers();
      headers.set('X-Requested-With', 'XMLHttpRequest');
      headers.set(this.contentTypeHeader, this.contentTypeValue);
      headers.set(authHeader, authValue);
      let url = this.serverURL + 'token';
      this.http.get(url, {headers: headers}).toPromise().then(result => {
        let body = result.json();
        this.localStorageService.set('csrf_token', body.token);
        this.userLogged = true;
        resolve(true);
      }).catch(error => {
        if (error.status == 401) {
          reject('Bad password or username.');
        }
      });
    });
  }

  logOut() {
    this.userLogged = false;
    this.localStorageService.set('csrf_token', '');
  }

  handleError(reject, error) {

    if (error.status === 403) {
      if (this.userLogged) {
        this.toastr.error('You have been logged out.');
      } else {
        this.toastr.error('You need to be logged in to do that.');
      }
      this.userLogged = false;
      this.localStorageService.set('csrf_token', '');
      this.router.navigate(['/login']).then(() => {
        reject(error);
      });
    }
  }
}
