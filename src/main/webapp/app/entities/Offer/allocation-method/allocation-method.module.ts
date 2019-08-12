import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { OfferSharedModule } from 'app/shared';
import {
  AllocationMethodComponent,
  AllocationMethodDetailComponent,
  AllocationMethodUpdateComponent,
  AllocationMethodDeletePopupComponent,
  AllocationMethodDeleteDialogComponent,
  allocationMethodRoute,
  allocationMethodPopupRoute
} from './';

const ENTITY_STATES = [...allocationMethodRoute, ...allocationMethodPopupRoute];

@NgModule({
  imports: [OfferSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    AllocationMethodComponent,
    AllocationMethodDetailComponent,
    AllocationMethodUpdateComponent,
    AllocationMethodDeleteDialogComponent,
    AllocationMethodDeletePopupComponent
  ],
  entryComponents: [
    AllocationMethodComponent,
    AllocationMethodUpdateComponent,
    AllocationMethodDeleteDialogComponent,
    AllocationMethodDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OfferAllocationMethodModule {}
