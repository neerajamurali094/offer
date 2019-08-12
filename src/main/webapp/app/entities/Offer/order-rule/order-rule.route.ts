import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { OrderRule } from 'app/shared/model/Offer/order-rule.model';
import { OrderRuleService } from './order-rule.service';
import { OrderRuleComponent } from './order-rule.component';
import { OrderRuleDetailComponent } from './order-rule-detail.component';
import { OrderRuleUpdateComponent } from './order-rule-update.component';
import { OrderRuleDeletePopupComponent } from './order-rule-delete-dialog.component';
import { IOrderRule } from 'app/shared/model/Offer/order-rule.model';

@Injectable({ providedIn: 'root' })
export class OrderRuleResolve implements Resolve<IOrderRule> {
  constructor(private service: OrderRuleService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<OrderRule> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<OrderRule>) => response.ok),
        map((orderRule: HttpResponse<OrderRule>) => orderRule.body)
      );
    }
    return of(new OrderRule());
  }
}

export const orderRuleRoute: Routes = [
  {
    path: 'order-rule',
    component: OrderRuleComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'offerApp.offerOrderRule.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'order-rule/:id/view',
    component: OrderRuleDetailComponent,
    resolve: {
      orderRule: OrderRuleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'offerApp.offerOrderRule.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'order-rule/new',
    component: OrderRuleUpdateComponent,
    resolve: {
      orderRule: OrderRuleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'offerApp.offerOrderRule.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'order-rule/:id/edit',
    component: OrderRuleUpdateComponent,
    resolve: {
      orderRule: OrderRuleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'offerApp.offerOrderRule.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const orderRulePopupRoute: Routes = [
  {
    path: 'order-rule/:id/delete',
    component: OrderRuleDeletePopupComponent,
    resolve: {
      orderRule: OrderRuleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'offerApp.offerOrderRule.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
