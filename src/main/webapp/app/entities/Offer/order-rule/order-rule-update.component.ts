import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IOrderRule } from 'app/shared/model/Offer/order-rule.model';
import { OrderRuleService } from './order-rule.service';

@Component({
  selector: 'jhi-order-rule-update',
  templateUrl: './order-rule-update.component.html'
})
export class OrderRuleUpdateComponent implements OnInit {
  orderRule: IOrderRule;
  isSaving: boolean;

  constructor(private orderRuleService: OrderRuleService, private activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ orderRule }) => {
      this.orderRule = orderRule;
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    if (this.orderRule.id !== undefined) {
      this.subscribeToSaveResponse(this.orderRuleService.update(this.orderRule));
    } else {
      this.subscribeToSaveResponse(this.orderRuleService.create(this.orderRule));
    }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<IOrderRule>>) {
    result.subscribe((res: HttpResponse<IOrderRule>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  private onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  private onSaveError() {
    this.isSaving = false;
  }
}
