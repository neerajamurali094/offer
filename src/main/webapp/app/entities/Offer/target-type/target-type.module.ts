import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { OfferSharedModule } from 'app/shared';
import {
  TargetTypeComponent,
  TargetTypeDetailComponent,
  TargetTypeUpdateComponent,
  TargetTypeDeletePopupComponent,
  TargetTypeDeleteDialogComponent,
  targetTypeRoute,
  targetTypePopupRoute
} from './';

const ENTITY_STATES = [...targetTypeRoute, ...targetTypePopupRoute];

@NgModule({
  imports: [OfferSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    TargetTypeComponent,
    TargetTypeDetailComponent,
    TargetTypeUpdateComponent,
    TargetTypeDeleteDialogComponent,
    TargetTypeDeletePopupComponent
  ],
  entryComponents: [TargetTypeComponent, TargetTypeUpdateComponent, TargetTypeDeleteDialogComponent, TargetTypeDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OfferTargetTypeModule {}
