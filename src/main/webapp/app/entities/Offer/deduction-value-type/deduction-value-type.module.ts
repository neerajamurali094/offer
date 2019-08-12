import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { OfferSharedModule } from 'app/shared';
import {
  DeductionValueTypeComponent,
  DeductionValueTypeDetailComponent,
  DeductionValueTypeUpdateComponent,
  DeductionValueTypeDeletePopupComponent,
  DeductionValueTypeDeleteDialogComponent,
  deductionValueTypeRoute,
  deductionValueTypePopupRoute
} from './';

const ENTITY_STATES = [...deductionValueTypeRoute, ...deductionValueTypePopupRoute];

@NgModule({
  imports: [OfferSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    DeductionValueTypeComponent,
    DeductionValueTypeDetailComponent,
    DeductionValueTypeUpdateComponent,
    DeductionValueTypeDeleteDialogComponent,
    DeductionValueTypeDeletePopupComponent
  ],
  entryComponents: [
    DeductionValueTypeComponent,
    DeductionValueTypeUpdateComponent,
    DeductionValueTypeDeleteDialogComponent,
    DeductionValueTypeDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OfferDeductionValueTypeModule {}
