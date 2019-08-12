import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { PaymentRule } from 'app/shared/model/Offer/payment-rule.model';
import { PaymentRuleService } from './payment-rule.service';
import { PaymentRuleComponent } from './payment-rule.component';
import { PaymentRuleDetailComponent } from './payment-rule-detail.component';
import { PaymentRuleUpdateComponent } from './payment-rule-update.component';
import { PaymentRuleDeletePopupComponent } from './payment-rule-delete-dialog.component';
import { IPaymentRule } from 'app/shared/model/Offer/payment-rule.model';

@Injectable({ providedIn: 'root' })
export class PaymentRuleResolve implements Resolve<IPaymentRule> {
  constructor(private service: PaymentRuleService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<PaymentRule> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<PaymentRule>) => response.ok),
        map((paymentRule: HttpResponse<PaymentRule>) => paymentRule.body)
      );
    }
    return of(new PaymentRule());
  }
}

export const paymentRuleRoute: Routes = [
  {
    path: 'payment-rule',
    component: PaymentRuleComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'offerApp.offerPaymentRule.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'payment-rule/:id/view',
    component: PaymentRuleDetailComponent,
    resolve: {
      paymentRule: PaymentRuleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'offerApp.offerPaymentRule.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'payment-rule/new',
    component: PaymentRuleUpdateComponent,
    resolve: {
      paymentRule: PaymentRuleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'offerApp.offerPaymentRule.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'payment-rule/:id/edit',
    component: PaymentRuleUpdateComponent,
    resolve: {
      paymentRule: PaymentRuleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'offerApp.offerPaymentRule.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const paymentRulePopupRoute: Routes = [
  {
    path: 'payment-rule/:id/delete',
    component: PaymentRuleDeletePopupComponent,
    resolve: {
      paymentRule: PaymentRuleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'offerApp.offerPaymentRule.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
