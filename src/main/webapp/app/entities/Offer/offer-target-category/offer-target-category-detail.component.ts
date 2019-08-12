import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOfferTargetCategory } from 'app/shared/model/Offer/offer-target-category.model';

@Component({
  selector: 'jhi-offer-target-category-detail',
  templateUrl: './offer-target-category-detail.component.html'
})
export class OfferTargetCategoryDetailComponent implements OnInit {
  offerTargetCategory: IOfferTargetCategory;

  constructor(private activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ offerTargetCategory }) => {
      this.offerTargetCategory = offerTargetCategory;
    });
  }

  previousState() {
    window.history.back();
  }
}
