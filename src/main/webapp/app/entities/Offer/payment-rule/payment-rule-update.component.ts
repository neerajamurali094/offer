import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IPaymentRule } from 'app/shared/model/Offer/payment-rule.model';
import { PaymentRuleService } from './payment-rule.service';

@Component({
  selector: 'jhi-payment-rule-update',
  templateUrl: './payment-rule-update.component.html'
})
export class PaymentRuleUpdateComponent implements OnInit {
  paymentRule: IPaymentRule;
  isSaving: boolean;

  constructor(private paymentRuleService: PaymentRuleService, private activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ paymentRule }) => {
      this.paymentRule = paymentRule;
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    if (this.paymentRule.id !== undefined) {
      this.subscribeToSaveResponse(this.paymentRuleService.update(this.paymentRule));
    } else {
      this.subscribeToSaveResponse(this.paymentRuleService.create(this.paymentRule));
    }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<IPaymentRule>>) {
    result.subscribe((res: HttpResponse<IPaymentRule>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  private onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  private onSaveError() {
    this.isSaving = false;
  }
}
