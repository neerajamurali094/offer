import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IOffer } from 'app/shared/model/Offer/offer.model';
import { OfferService } from './offer.service';
import { IPriceRule } from 'app/shared/model/Offer/price-rule.model';
import { PriceRuleService } from 'app/entities/Offer/price-rule';
import { IPaymentRule } from 'app/shared/model/Offer/payment-rule.model';
import { PaymentRuleService } from 'app/entities/Offer/payment-rule';
import { IOrderRule } from 'app/shared/model/Offer/order-rule.model';
import { OrderRuleService } from 'app/entities/Offer/order-rule';

@Component({
  selector: 'jhi-offer-update',
  templateUrl: './offer-update.component.html'
})
export class OfferUpdateComponent implements OnInit {
  offer: IOffer;
  isSaving: boolean;

  pricerules: IPriceRule[];

  paymentrules: IPaymentRule[];

  orderrules: IOrderRule[];
  createdDate: string;
  updatedDate: string;

  constructor(
    private jhiAlertService: JhiAlertService,
    private offerService: OfferService,
    private priceRuleService: PriceRuleService,
    private paymentRuleService: PaymentRuleService,
    private orderRuleService: OrderRuleService,
    private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ offer }) => {
      this.offer = offer;
      this.createdDate = this.offer.createdDate != null ? this.offer.createdDate.format(DATE_TIME_FORMAT) : null;
      this.updatedDate = this.offer.updatedDate != null ? this.offer.updatedDate.format(DATE_TIME_FORMAT) : null;
    });
    this.priceRuleService.query({ filter: 'offer-is-null' }).subscribe(
      (res: HttpResponse<IPriceRule[]>) => {
        if (!this.offer.priceRuleId) {
          this.pricerules = res.body;
        } else {
          this.priceRuleService.find(this.offer.priceRuleId).subscribe(
            (subRes: HttpResponse<IPriceRule>) => {
              this.pricerules = [subRes.body].concat(res.body);
            },
            (subRes: HttpErrorResponse) => this.onError(subRes.message)
          );
        }
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
    this.paymentRuleService.query({ filter: 'offer-is-null' }).subscribe(
      (res: HttpResponse<IPaymentRule[]>) => {
        if (!this.offer.paymentRuleId) {
          this.paymentrules = res.body;
        } else {
          this.paymentRuleService.find(this.offer.paymentRuleId).subscribe(
            (subRes: HttpResponse<IPaymentRule>) => {
              this.paymentrules = [subRes.body].concat(res.body);
            },
            (subRes: HttpErrorResponse) => this.onError(subRes.message)
          );
        }
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
    this.orderRuleService.query({ filter: 'offer-is-null' }).subscribe(
      (res: HttpResponse<IOrderRule[]>) => {
        if (!this.offer.orderRuleId) {
          this.orderrules = res.body;
        } else {
          this.orderRuleService.find(this.offer.orderRuleId).subscribe(
            (subRes: HttpResponse<IOrderRule>) => {
              this.orderrules = [subRes.body].concat(res.body);
            },
            (subRes: HttpErrorResponse) => this.onError(subRes.message)
          );
        }
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    this.offer.createdDate = this.createdDate != null ? moment(this.createdDate, DATE_TIME_FORMAT) : null;
    this.offer.updatedDate = this.updatedDate != null ? moment(this.updatedDate, DATE_TIME_FORMAT) : null;
    if (this.offer.id !== undefined) {
      this.subscribeToSaveResponse(this.offerService.update(this.offer));
    } else {
      this.subscribeToSaveResponse(this.offerService.create(this.offer));
    }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<IOffer>>) {
    result.subscribe((res: HttpResponse<IOffer>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  private onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  private onSaveError() {
    this.isSaving = false;
  }

  private onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackPriceRuleById(index: number, item: IPriceRule) {
    return item.id;
  }

  trackPaymentRuleById(index: number, item: IPaymentRule) {
    return item.id;
  }

  trackOrderRuleById(index: number, item: IOrderRule) {
    return item.id;
  }
}
