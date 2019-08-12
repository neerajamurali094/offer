import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { PriceRule } from 'app/shared/model/Offer/price-rule.model';
import { PriceRuleService } from './price-rule.service';
import { PriceRuleComponent } from './price-rule.component';
import { PriceRuleDetailComponent } from './price-rule-detail.component';
import { PriceRuleUpdateComponent } from './price-rule-update.component';
import { PriceRuleDeletePopupComponent } from './price-rule-delete-dialog.component';
import { IPriceRule } from 'app/shared/model/Offer/price-rule.model';

@Injectable({ providedIn: 'root' })
export class PriceRuleResolve implements Resolve<IPriceRule> {
  constructor(private service: PriceRuleService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<PriceRule> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<PriceRule>) => response.ok),
        map((priceRule: HttpResponse<PriceRule>) => priceRule.body)
      );
    }
    return of(new PriceRule());
  }
}

export const priceRuleRoute: Routes = [
  {
    path: 'price-rule',
    component: PriceRuleComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'offerApp.offerPriceRule.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'price-rule/:id/view',
    component: PriceRuleDetailComponent,
    resolve: {
      priceRule: PriceRuleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'offerApp.offerPriceRule.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'price-rule/new',
    component: PriceRuleUpdateComponent,
    resolve: {
      priceRule: PriceRuleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'offerApp.offerPriceRule.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'price-rule/:id/edit',
    component: PriceRuleUpdateComponent,
    resolve: {
      priceRule: PriceRuleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'offerApp.offerPriceRule.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const priceRulePopupRoute: Routes = [
  {
    path: 'price-rule/:id/delete',
    component: PriceRuleDeletePopupComponent,
    resolve: {
      priceRule: PriceRuleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'offerApp.offerPriceRule.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
