import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { OfferTargetCategory } from 'app/shared/model/Offer/offer-target-category.model';
import { OfferTargetCategoryService } from './offer-target-category.service';
import { OfferTargetCategoryComponent } from './offer-target-category.component';
import { OfferTargetCategoryDetailComponent } from './offer-target-category-detail.component';
import { OfferTargetCategoryUpdateComponent } from './offer-target-category-update.component';
import { OfferTargetCategoryDeletePopupComponent } from './offer-target-category-delete-dialog.component';
import { IOfferTargetCategory } from 'app/shared/model/Offer/offer-target-category.model';

@Injectable({ providedIn: 'root' })
export class OfferTargetCategoryResolve implements Resolve<IOfferTargetCategory> {
  constructor(private service: OfferTargetCategoryService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<OfferTargetCategory> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<OfferTargetCategory>) => response.ok),
        map((offerTargetCategory: HttpResponse<OfferTargetCategory>) => offerTargetCategory.body)
      );
    }
    return of(new OfferTargetCategory());
  }
}

export const offerTargetCategoryRoute: Routes = [
  {
    path: 'offer-target-category',
    component: OfferTargetCategoryComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'offerApp.offerOfferTargetCategory.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'offer-target-category/:id/view',
    component: OfferTargetCategoryDetailComponent,
    resolve: {
      offerTargetCategory: OfferTargetCategoryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'offerApp.offerOfferTargetCategory.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'offer-target-category/new',
    component: OfferTargetCategoryUpdateComponent,
    resolve: {
      offerTargetCategory: OfferTargetCategoryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'offerApp.offerOfferTargetCategory.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'offer-target-category/:id/edit',
    component: OfferTargetCategoryUpdateComponent,
    resolve: {
      offerTargetCategory: OfferTargetCategoryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'offerApp.offerOfferTargetCategory.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const offerTargetCategoryPopupRoute: Routes = [
  {
    path: 'offer-target-category/:id/delete',
    component: OfferTargetCategoryDeletePopupComponent,
    resolve: {
      offerTargetCategory: OfferTargetCategoryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'offerApp.offerOfferTargetCategory.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
