import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { OfferTarget } from 'app/shared/model/Offer/offer-target.model';
import { OfferTargetService } from './offer-target.service';
import { OfferTargetComponent } from './offer-target.component';
import { OfferTargetDetailComponent } from './offer-target-detail.component';
import { OfferTargetUpdateComponent } from './offer-target-update.component';
import { OfferTargetDeletePopupComponent } from './offer-target-delete-dialog.component';
import { IOfferTarget } from 'app/shared/model/Offer/offer-target.model';

@Injectable({ providedIn: 'root' })
export class OfferTargetResolve implements Resolve<IOfferTarget> {
  constructor(private service: OfferTargetService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<OfferTarget> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<OfferTarget>) => response.ok),
        map((offerTarget: HttpResponse<OfferTarget>) => offerTarget.body)
      );
    }
    return of(new OfferTarget());
  }
}

export const offerTargetRoute: Routes = [
  {
    path: 'offer-target',
    component: OfferTargetComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'offerApp.offerOfferTarget.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'offer-target/:id/view',
    component: OfferTargetDetailComponent,
    resolve: {
      offerTarget: OfferTargetResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'offerApp.offerOfferTarget.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'offer-target/new',
    component: OfferTargetUpdateComponent,
    resolve: {
      offerTarget: OfferTargetResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'offerApp.offerOfferTarget.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'offer-target/:id/edit',
    component: OfferTargetUpdateComponent,
    resolve: {
      offerTarget: OfferTargetResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'offerApp.offerOfferTarget.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const offerTargetPopupRoute: Routes = [
  {
    path: 'offer-target/:id/delete',
    component: OfferTargetDeletePopupComponent,
    resolve: {
      offerTarget: OfferTargetResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'offerApp.offerOfferTarget.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
