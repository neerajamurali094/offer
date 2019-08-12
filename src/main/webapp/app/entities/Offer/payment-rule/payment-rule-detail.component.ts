import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPaymentRule } from 'app/shared/model/Offer/payment-rule.model';

@Component({
  selector: 'jhi-payment-rule-detail',
  templateUrl: './payment-rule-detail.component.html'
})
export class PaymentRuleDetailComponent implements OnInit {
  paymentRule: IPaymentRule;

  constructor(private activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ paymentRule }) => {
      this.paymentRule = paymentRule;
    });
  }

  previousState() {
    window.history.back();
  }
}
