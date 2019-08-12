import { IPriceRule } from 'app/shared/model/Offer/price-rule.model';

export interface IAllocationMethod {
  id?: number;
  allocationMethod?: string;
  priceRules?: IPriceRule[];
}

export class AllocationMethod implements IAllocationMethod {
  constructor(public id?: number, public allocationMethod?: string, public priceRules?: IPriceRule[]) {}
}
