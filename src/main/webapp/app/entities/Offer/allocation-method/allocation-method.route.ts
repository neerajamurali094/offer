import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { AllocationMethod } from 'app/shared/model/Offer/allocation-method.model';
import { AllocationMethodService } from './allocation-method.service';
import { AllocationMethodComponent } from './allocation-method.component';
import { AllocationMethodDetailComponent } from './allocation-method-detail.component';
import { AllocationMethodUpdateComponent } from './allocation-method-update.component';
import { AllocationMethodDeletePopupComponent } from './allocation-method-delete-dialog.component';
import { IAllocationMethod } from 'app/shared/model/Offer/allocation-method.model';

@Injectable({ providedIn: 'root' })
export class AllocationMethodResolve implements Resolve<IAllocationMethod> {
  constructor(private service: AllocationMethodService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<AllocationMethod> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<AllocationMethod>) => response.ok),
        map((allocationMethod: HttpResponse<AllocationMethod>) => allocationMethod.body)
      );
    }
    return of(new AllocationMethod());
  }
}

export const allocationMethodRoute: Routes = [
  {
    path: 'allocation-method',
    component: AllocationMethodComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'offerApp.offerAllocationMethod.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'allocation-method/:id/view',
    component: AllocationMethodDetailComponent,
    resolve: {
      allocationMethod: AllocationMethodResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'offerApp.offerAllocationMethod.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'allocation-method/new',
    component: AllocationMethodUpdateComponent,
    resolve: {
      allocationMethod: AllocationMethodResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'offerApp.offerAllocationMethod.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'allocation-method/:id/edit',
    component: AllocationMethodUpdateComponent,
    resolve: {
      allocationMethod: AllocationMethodResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'offerApp.offerAllocationMethod.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const allocationMethodPopupRoute: Routes = [
  {
    path: 'allocation-method/:id/delete',
    component: AllocationMethodDeletePopupComponent,
    resolve: {
      allocationMethod: AllocationMethodResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'offerApp.offerAllocationMethod.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
