import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPriceRule } from 'app/shared/model/Offer/price-rule.model';

@Component({
  selector: 'jhi-price-rule-detail',
  templateUrl: './price-rule-detail.component.html'
})
export class PriceRuleDetailComponent implements OnInit {
  priceRule: IPriceRule;

  constructor(private activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ priceRule }) => {
      this.priceRule = priceRule;
    });
  }

  previousState() {
    window.history.back();
  }
}
