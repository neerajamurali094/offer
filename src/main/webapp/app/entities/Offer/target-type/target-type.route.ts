import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { TargetType } from 'app/shared/model/Offer/target-type.model';
import { TargetTypeService } from './target-type.service';
import { TargetTypeComponent } from './target-type.component';
import { TargetTypeDetailComponent } from './target-type-detail.component';
import { TargetTypeUpdateComponent } from './target-type-update.component';
import { TargetTypeDeletePopupComponent } from './target-type-delete-dialog.component';
import { ITargetType } from 'app/shared/model/Offer/target-type.model';

@Injectable({ providedIn: 'root' })
export class TargetTypeResolve implements Resolve<ITargetType> {
  constructor(private service: TargetTypeService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<TargetType> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<TargetType>) => response.ok),
        map((targetType: HttpResponse<TargetType>) => targetType.body)
      );
    }
    return of(new TargetType());
  }
}

export const targetTypeRoute: Routes = [
  {
    path: 'target-type',
    component: TargetTypeComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'offerApp.offerTargetType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'target-type/:id/view',
    component: TargetTypeDetailComponent,
    resolve: {
      targetType: TargetTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'offerApp.offerTargetType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'target-type/new',
    component: TargetTypeUpdateComponent,
    resolve: {
      targetType: TargetTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'offerApp.offerTargetType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'target-type/:id/edit',
    component: TargetTypeUpdateComponent,
    resolve: {
      targetType: TargetTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'offerApp.offerTargetType.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const targetTypePopupRoute: Routes = [
  {
    path: 'target-type/:id/delete',
    component: TargetTypeDeletePopupComponent,
    resolve: {
      targetType: TargetTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'offerApp.offerTargetType.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
