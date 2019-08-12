import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { OfferSharedModule } from 'app/shared';
import {
  OfferTargetComponent,
  OfferTargetDetailComponent,
  OfferTargetUpdateComponent,
  OfferTargetDeletePopupComponent,
  OfferTargetDeleteDialogComponent,
  offerTargetRoute,
  offerTargetPopupRoute
} from './';

const ENTITY_STATES = [...offerTargetRoute, ...offerTargetPopupRoute];

@NgModule({
  imports: [OfferSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    OfferTargetComponent,
    OfferTargetDetailComponent,
    OfferTargetUpdateComponent,
    OfferTargetDeleteDialogComponent,
    OfferTargetDeletePopupComponent
  ],
  entryComponents: [OfferTargetComponent, OfferTargetUpdateComponent, OfferTargetDeleteDialogComponent, OfferTargetDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OfferOfferTargetModule {}
