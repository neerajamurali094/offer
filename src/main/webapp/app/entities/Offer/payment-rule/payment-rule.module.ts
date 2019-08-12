import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { OfferSharedModule } from 'app/shared';
import {
  PaymentRuleComponent,
  PaymentRuleDetailComponent,
  PaymentRuleUpdateComponent,
  PaymentRuleDeletePopupComponent,
  PaymentRuleDeleteDialogComponent,
  paymentRuleRoute,
  paymentRulePopupRoute
} from './';

const ENTITY_STATES = [...paymentRuleRoute, ...paymentRulePopupRoute];

@NgModule({
  imports: [OfferSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    PaymentRuleComponent,
    PaymentRuleDetailComponent,
    PaymentRuleUpdateComponent,
    PaymentRuleDeleteDialogComponent,
    PaymentRuleDeletePopupComponent
  ],
  entryComponents: [PaymentRuleComponent, PaymentRuleUpdateComponent, PaymentRuleDeleteDialogComponent, PaymentRuleDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OfferPaymentRuleModule {}
