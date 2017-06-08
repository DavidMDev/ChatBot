import {Component, OnInit, OnDestroy} from "@angular/core";
import {Address} from "./address";
import {ActivatedRoute} from "@angular/router";
import {AddressService} from "../services/address.service";
import {Location}               from '@angular/common';
import {ToastsManager} from "ng2-toastr";

@Component({
  moduleId: module.id,
  selector: 'address-detail',
  templateUrl: './address-detail.component.html',
  styleUrls: ['../users.component.css']
})

export class AddressDetailComponent implements OnInit, OnDestroy {
  address: Address;
  sub: any;

  constructor(private toastr: ToastsManager, private addressService: AddressService, private route: ActivatedRoute, private location: Location) {
  }

  ngOnInit(): void {
    this.sub = this.route.params.subscribe(params => {
      this.addressService.getAddress(+params['id']).then(task => {
        this.address = task;
      });
    })
  }

  ngOnDestroy(): void {
    this.sub.unsubscribe();
    this.address = null;
  }

  public save(): void {
    this.addressService.modifyAddress(this.address).then(address => {
      this.address = address;
      this.toastr.info('Address updated.');
    });
  }

  goBack(): void {
    this.location.back();
  }
}
