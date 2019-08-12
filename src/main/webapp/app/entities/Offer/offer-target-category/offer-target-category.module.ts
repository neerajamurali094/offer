import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { OfferSharedModule } from 'app/shared';
import {
  OfferTargetCategoryComponent,
  OfferTargetCategoryDetailComponent,
  OfferTargetCategoryUpdateComponent,
  OfferTargetCategoryDeletePopupComponent,
  OfferTargetCategoryDeleteDialogComponent,
  offerTargetCategoryRoute,
  offerTargetCategoryPopupRoute
} from './';

const ENTITY_STATES = [...offerTargetCategoryRoute, ...offerTargetCategoryPopupRoute];

@NgModule({
  imports: [OfferSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    OfferTargetCategoryComponent,
    OfferTargetCategoryDetailComponent,
    OfferTargetCategoryUpdateComponent,
    OfferTargetCategoryDeleteDialogComponent,
    OfferTargetCategoryDeletePopupComponent
  ],
  entryComponents: [
    OfferTargetCategoryComponent,
    OfferTargetCategoryUpdateComponent,
    OfferTargetCategoryDeleteDialogComponent,
    OfferTargetCategoryDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OfferOfferTargetCategoryModule {}
