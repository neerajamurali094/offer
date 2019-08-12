import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CustomerSelection } from 'app/shared/model/Offer/customer-selection.model';
import { CustomerSelectionService } from './customer-selection.service';
import { CustomerSelectionComponent } from './customer-selection.component';
import { CustomerSelectionDetailComponent } from './customer-selection-detail.component';
import { CustomerSelectionUpdateComponent } from './customer-selection-update.component';
import { CustomerSelectionDeletePopupComponent } from './customer-selection-delete-dialog.component';
import { ICustomerSelection } from 'app/shared/model/Offer/customer-selection.model';

@Injectable({ providedIn: 'root' })
export class CustomerSelectionResolve implements Resolve<ICustomerSelection> {
  constructor(private service: CustomerSelectionService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<CustomerSelection> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<CustomerSelection>) => response.ok),
        map((customerSelection: HttpResponse<CustomerSelection>) => customerSelection.body)
      );
    }
    return of(new CustomerSelection());
  }
}

export const customerSelectionRoute: Routes = [
  {
    path: 'customer-selection',
    component: CustomerSelectionComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'offerApp.offerCustomerSelection.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'customer-selection/:id/view',
    component: CustomerSelectionDetailComponent,
    resolve: {
      customerSelection: CustomerSelectionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'offerApp.offerCustomerSelection.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'customer-selection/new',
    component: CustomerSelectionUpdateComponent,
    resolve: {
      customerSelection: CustomerSelectionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'offerApp.offerCustomerSelection.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'customer-selection/:id/edit',
    component: CustomerSelectionUpdateComponent,
    resolve: {
      customerSelection: CustomerSelectionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'offerApp.offerCustomerSelection.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const customerSelectionPopupRoute: Routes = [
  {
    path: 'customer-selection/:id/delete',
    component: CustomerSelectionDeletePopupComponent,
    resolve: {
      customerSelection: CustomerSelectionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'offerApp.offerCustomerSelection.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
