import { IPriceRule } from 'app/shared/model/Offer/price-rule.model';

export interface ICustomerSelection {
  id?: number;
  customerSelectionType?: string;
  priceRules?: IPriceRule[];
}

export class CustomerSelection implements ICustomerSelection {
  constructor(public id?: number, public customerSelectionType?: string, public priceRules?: IPriceRule[]) {}
}
