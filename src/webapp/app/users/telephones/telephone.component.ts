import {Component, OnInit} from "@angular/core";
import {ActivatedRoute, Router} from "@angular/router";
import {ToastsManager} from "ng2-toastr";
import {TelephoneService} from "../services/telephones.service";
import {Telephone} from "./telephone";

@Component({
  moduleId: module.id,
  selector: 'phone-manager',
  templateUrl: 'telephone.component.html',
  styleUrls: ['../users.component.css']
})

export class TelephoneComponent implements OnInit {
  sub: any;
  telephones: Array<Telephone>;

  ngOnInit(): void {
    this.telephones = [];
    this.sub = this.route.params.subscribe(() => {
      this.telephoneService.getTelephones().then(telephones => {
        this.telephones = telephones;
      });
    });
  }

  ngOnDestroy(): void {
    this.sub.unsubscribe();
    this.telephones = null;
  }

  constructor(private toastr: ToastsManager, private telephoneService: TelephoneService, private route: ActivatedRoute, private router: Router) {
  }

  delete(phone: Telephone): void {
    this.telephoneService.deleteTelephone(phone.id).then((telephones) => {
      this.telephones = telephones;
      this.toastr.info('Phone deleted');
    });
  }

  add(number: string, type: string): void {
    //check to see if the number string only contains digits
    if (/^\d+$/.test(number)) {
      this.telephoneService.createTelephone(number, type).then(telephones => {
        this.telephones = telephones;
        this.toastr.info('Telephone created.');
      });
    } else {
      this.toastr.error('Phone number must not contain letters');
    }
  }

  goto(telephone: Telephone): void {
    this.router.navigate(['/telephone', telephone.id]);
  }
}
