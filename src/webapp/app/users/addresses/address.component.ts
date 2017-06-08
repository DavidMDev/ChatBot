import {Component, OnInit} from "@angular/core";
import {ActivatedRoute, Router} from "@angular/router";
import {ToastsManager} from "ng2-toastr";
import {AddressService} from "../services/address.service";
import {Address} from "./address";

@Component({
  moduleId: module.id,
  selector: 'address-manager',
  templateUrl: 'address.component.html',
  styleUrls: ['../users.component.css']
})

export class AddressComponent implements OnInit {
  sub: any;
  addresses: Array<Address>;

  ngOnInit(): void {
    this.addresses = [];
    this.sub = this.route.params.subscribe(() => {
      this.addressService.getAddresss().then(addresses => {
        this.addresses = addresses;
      });
    });
  }

  ngOnDestroy(): void {
    this.sub.unsubscribe();
    this.addresses = null;
  }

  constructor(private toastr: ToastsManager, private addressService: AddressService, private route: ActivatedRoute, private router: Router) {
  }

  delete(phone: Address): void {
    this.addressService.deleteAddress(phone.id).then((addresses) => {
      this.addresses = addresses;
      this.toastr.info('Phone deleted');
    });
  }

  add(postcode: string,
      streetName: string,
      addressDetails: string,
      houseNumber: number,
      city: string): void {
    //check to see if the number string only contains digits
    this.addressService.createAddress(postcode,
      streetName,
      addressDetails,
      houseNumber,
      city).then(addresses => {
      this.addresses = addresses;
      this.toastr.info('Address created.');
    });
  }

  goto(address: Address): void {
    this.router.navigate(['/address', address.id]);
  }
}
