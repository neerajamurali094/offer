import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOfferTarget } from 'app/shared/model/Offer/offer-target.model';

@Component({
  selector: 'jhi-offer-target-detail',
  templateUrl: './offer-target-detail.component.html'
})
export class OfferTargetDetailComponent implements OnInit {
  offerTarget: IOfferTarget;

  constructor(private activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ offerTarget }) => {
      this.offerTarget = offerTarget;
    });
  }

  previousState() {
    window.history.back();
  }
}
