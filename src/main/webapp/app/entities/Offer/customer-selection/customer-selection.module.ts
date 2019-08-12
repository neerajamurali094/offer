import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { OfferSharedModule } from 'app/shared';
import {
  CustomerSelectionComponent,
  CustomerSelectionDetailComponent,
  CustomerSelectionUpdateComponent,
  CustomerSelectionDeletePopupComponent,
  CustomerSelectionDeleteDialogComponent,
  customerSelectionRoute,
  customerSelectionPopupRoute
} from './';

const ENTITY_STATES = [...customerSelectionRoute, ...customerSelectionPopupRoute];

@NgModule({
  imports: [OfferSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    CustomerSelectionComponent,
    CustomerSelectionDetailComponent,
    CustomerSelectionUpdateComponent,
    CustomerSelectionDeleteDialogComponent,
    CustomerSelectionDeletePopupComponent
  ],
  entryComponents: [
    CustomerSelectionComponent,
    CustomerSelectionUpdateComponent,
    CustomerSelectionDeleteDialogComponent,
    CustomerSelectionDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OfferCustomerSelectionModule {}
