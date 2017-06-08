import {Injectable} from "@angular/core";
import {HttpService} from "../../http/http.service";
import {Telephone} from "../telephones/telephone";


@Injectable()
export class TelephoneService {
  telephoneUrl = "telephones/";

  constructor(private httpService: HttpService) {
  }

  public getTelephones(): Promise<Telephone[]> {
    return new Promise((resolve, reject) => {
      this.httpService.get(this.telephoneUrl)
        .then(res => {
            let telephonesJSON = res.json();
            let telephones = Array<Telephone>();
            telephonesJSON.forEach(telephoneJSON => {
              telephones.push(this.mapTelephone(telephoneJSON));
            });
            resolve(telephones);
          }
        ).catch((err) => {
        reject(this.handleError(err));
      });
    });
  }

  public deleteTelephone(id: number): Promise<Telephone[]> {
    return new Promise((resolve, reject) => {
      const url = `${this.telephoneUrl}${id}`;
      this.httpService.delete(url)
        .then(res => {
            let telephonesJSON = res.json();
            let telephones = Array<Telephone>();
            telephonesJSON.forEach(telephoneJSON => {
              telephones.push(this.mapTelephone(telephoneJSON));
            });
            resolve(telephones);
          }
        ).catch((err) => {
        reject(this.handleError(err));
      });
    });
  }

  public createTelephone(number: string, type: string): Promise<Telephone[]> {
    return new Promise((resolve, reject) => {
      this.httpService
        .post(this.telephoneUrl, {number: number, type: type})
        .then(res => {
          let telephonesJSON = res.json();
          let telephones = Array<Telephone>();
          telephonesJSON.forEach(telephoneJSON => {
            telephones.push(this.mapTelephone(telephoneJSON));
          });
          resolve(telephones);
        })
        .catch(this.handleError);
    });
  }

  private handleError(error: any): Promise<any> {
    console.error('An error occurred', error);
    return Promise.reject(error.message || error);
  }

  public getTelephone(id: number) {
    const url = `${this.telephoneUrl}${id}`;
    return this.httpService.get(url)
      .then(res => this.mapTelephone(res.json()))
      .catch(this.handleError);
  }

  public modifyTelephone(telephone: Telephone) {
    const url = `${this.telephoneUrl}`;
    let obj = {id: telephone.id, type: telephone.type, number: telephone.number};
    return this.httpService.put(url, obj)
      .then(res => this.mapTelephone(res.json()))
      .catch(this.handleError);
  }

  private mapTelephone(telephoneJSON): Telephone {
    let telephone = new Telephone();
    telephone.id = telephoneJSON.id;
    telephone.number = telephoneJSON.number;
    telephone.type = telephoneJSON.type;
    return telephone;
  }
}
