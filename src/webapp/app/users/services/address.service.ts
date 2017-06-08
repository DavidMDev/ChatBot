import {Injectable} from "@angular/core";
import {HttpService} from "../../http/http.service";
import {Address} from "../addresses/address";

@Injectable()
export class AddressService {
  addressUrl = "addresses/";

  constructor(private httpService: HttpService) {
  }

  public getAddresss(): Promise<Address[]> {
    return new Promise((resolve, reject) => {
      this.httpService.get(this.addressUrl)
        .then(res => {
            let addressesJSON = res.json();
            let addresses = Array<Address>();
            addressesJSON.forEach(addressJSON => {
              addresses.push(this.mapAddress(addressJSON));
            });
            resolve(addresses);
          }
        ).catch((err) => {
        reject(this.handleError(err));
      });
    });
  }

  public deleteAddress(id: number): Promise<Address[]> {
    return new Promise((resolve, reject) => {
      const url = `${this.addressUrl}${id}`;
      this.httpService.delete(url)
        .then(res => {
            let addressesJSON = res.json();
            let addresses = Array<Address>();
            addressesJSON.forEach(addressJSON => {
              addresses.push(this.mapAddress(addressJSON));
            });
            resolve(addresses);
          }
        ).catch((err) => {
        reject(this.handleError(err));
      });
    });
  }

  public createAddress(postcode: string,
                       streetName: string,
                       addressDetails: string,
                       houseNumber: number,
                       city: string): Promise<Address[]> {
    return new Promise((resolve, reject) => {
      this.httpService
        .post(this.addressUrl, {
          streetName: streetName,
          houseNumber: houseNumber,
          city: city,
          postcode: postcode,
          addressDetails: addressDetails
        })
        .then(res => {
          let addressesJSON = res.json();
          let addresses = Array<Address>();
          addressesJSON.forEach(addressJSON => {
            addresses.push(this.mapAddress(addressJSON));
          });
          resolve(addresses);
        })
        .catch(this.handleError);
    });
  }

  private handleError(error: any): Promise<any> {
    console.error('An error occurred', error);
    return Promise.reject(error.message || error);
  }

  public getAddress(id: number) {
    const url = `${this.addressUrl}${id}`;
    return this.httpService.get(url)
      .then(res => this.mapAddress(res.json()))
      .catch(this.handleError);
  }

  public modifyAddress(address: Address) {
    const url = `${this.addressUrl}`;
    let obj = {
      id: address.id,
      streetName: address.streetName,
      houseNumber: address.houseNumber,
      city: address.city,
      postcode: address.postcode,
      addressDetails: address.addressDetails
    };
    return this.httpService.put(url, obj)
      .then(res => this.mapAddress(res.json()))
      .catch(this.handleError);
  }

  private mapAddress(addressJSON): Address {
    let address = new Address();
    address.id = addressJSON.id;
    address.streetName = addressJSON.streetName;
    address.houseNumber = addressJSON.houseNumber;
    address.city = addressJSON.city;
    address.postcode = addressJSON.postcode;
    address.addressDetails = addressJSON.addressDetails;
    return address;
  }
}
