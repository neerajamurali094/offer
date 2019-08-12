import { IPriceRule } from 'app/shared/model/Offer/price-rule.model';

export interface IDeductionValueType {
  id?: number;
  deductionValueType?: string;
  priceRules?: IPriceRule[];
}

export class DeductionValueType implements IDeductionValueType {
  constructor(public id?: number, public deductionValueType?: string, public priceRules?: IPriceRule[]) {}
}
