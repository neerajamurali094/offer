import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { OfferSharedModule } from 'app/shared';
import {
  OrderRuleComponent,
  OrderRuleDetailComponent,
  OrderRuleUpdateComponent,
  OrderRuleDeletePopupComponent,
  OrderRuleDeleteDialogComponent,
  orderRuleRoute,
  orderRulePopupRoute
} from './';

const ENTITY_STATES = [...orderRuleRoute, ...orderRulePopupRoute];

@NgModule({
  imports: [OfferSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    OrderRuleComponent,
    OrderRuleDetailComponent,
    OrderRuleUpdateComponent,
    OrderRuleDeleteDialogComponent,
    OrderRuleDeletePopupComponent
  ],
  entryComponents: [OrderRuleComponent, OrderRuleUpdateComponent, OrderRuleDeleteDialogComponent, OrderRuleDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OfferOrderRuleModule {}
