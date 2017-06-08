import {TelephoneService} from "../services/telephones.service";
import {Telephone} from "./telephone";
import {OnDestroy, OnInit, Component} from "@angular/core";
import {ActivatedRoute} from "@angular/router";
import {Location}               from '@angular/common';
import {ToastsManager} from "ng2-toastr";

@Component({
  moduleId: module.id,
  selector: 'telephone-detail',
  templateUrl: './telephone-detail.component.html',
  styleUrls: ['../users.component.css']
})

export class TelephoneDetailComponent implements OnInit, OnDestroy {
  telephone: Telephone;
  sub: any;

  constructor(private toastr: ToastsManager, private telephoneService: TelephoneService, private route: ActivatedRoute, private location: Location) {
  }

  ngOnInit(): void {
    this.sub = this.route.params.subscribe(params => {
      this.telephoneService.getTelephone(+params['id']).then(task => {
        this.telephone = task;
      });
    })
  }

  ngOnDestroy(): void {
    this.sub.unsubscribe();
    this.telephone = null;
  }

  public save(): void {
    this.telephoneService.modifyTelephone(this.telephone).then(telephone => {
      this.telephone = telephone;
      this.toastr.info('Telephone updated.');
    });
  }

  goBack(): void {
    this.location.back();
  }
}
