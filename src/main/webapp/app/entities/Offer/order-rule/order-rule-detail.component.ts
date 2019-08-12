import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOrderRule } from 'app/shared/model/Offer/order-rule.model';

@Component({
  selector: 'jhi-order-rule-detail',
  templateUrl: './order-rule-detail.component.html'
})
export class OrderRuleDetailComponent implements OnInit {
  orderRule: IOrderRule;

  constructor(private activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ orderRule }) => {
      this.orderRule = orderRule;
    });
  }

  previousState() {
    window.history.back();
  }
}
