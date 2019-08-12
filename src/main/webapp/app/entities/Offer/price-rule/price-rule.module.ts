import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { OfferSharedModule } from 'app/shared';
import {
  PriceRuleComponent,
  PriceRuleDetailComponent,
  PriceRuleUpdateComponent,
  PriceRuleDeletePopupComponent,
  PriceRuleDeleteDialogComponent,
  priceRuleRoute,
  priceRulePopupRoute
} from './';

const ENTITY_STATES = [...priceRuleRoute, ...priceRulePopupRoute];

@NgModule({
  imports: [OfferSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    PriceRuleComponent,
    PriceRuleDetailComponent,
    PriceRuleUpdateComponent,
    PriceRuleDeleteDialogComponent,
    PriceRuleDeletePopupComponent
  ],
  entryComponents: [PriceRuleComponent, PriceRuleUpdateComponent, PriceRuleDeleteDialogComponent, PriceRuleDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OfferPriceRuleModule {}
