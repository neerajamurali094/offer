import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { DeductionValueType } from 'app/shared/model/Offer/deduction-value-type.model';
import { DeductionValueTypeService } from './deduction-value-type.service';
import { DeductionValueTypeComponent } from './deduction-value-type.component';
import { DeductionValueTypeDetailComponent } from './deduction-value-type-detail.component';
import { DeductionValueTypeUpdateComponent } from './deduction-value-type-update.component';
import { DeductionValueTypeDeletePopupComponent } from './deduction-value-type-delete-dialog.component';
import { IDeductionValueType } from 'app/shared/model/Offer/deduction-value-type.model';

@Injectable({ providedIn: 'root' })
export class DeductionValueTypeResolve implements Resolve<IDeductionValueType> {
  constructor(private service: DeductionValueTypeService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<DeductionValueType> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<DeductionValueType>) => response.ok),
        map((deductionValueType: HttpResponse<DeductionValueType>) => deductionValueType.body)
      );
    }
    return of(new DeductionValueType());
  }
}

export const deductionValueTypeRoute: Routes = [
  {
    path: 'deduction-value-type',
    component: DeductionValueTypeComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'offerApp.offerDeductionValueType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'deduction-value-type/:id/view',
    component: DeductionValueTypeDetailComponent,
    resolve: {
      deductionValueType: DeductionValueTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'offerApp.offerDeductionValueType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'deduction-value-type/new',
    component: DeductionValueTypeUpdateComponent,
    resolve: {
      deductionValueType: DeductionValueTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'offerApp.offerDeductionValueType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'deduction-value-type/:id/edit',
    component: DeductionValueTypeUpdateComponent,
    resolve: {
      deductionValueType: DeductionValueTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'offerApp.offerDeductionValueType.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const deductionValueTypePopupRoute: Routes = [
  {
    path: 'deduction-value-type/:id/delete',
    component: DeductionValueTypeDeletePopupComponent,
    resolve: {
      deductionValueType: DeductionValueTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'offerApp.offerDeductionValueType.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
