import { Moment } from 'moment';

export interface IPriceRule {
  id?: number;
  deductionValue?: number;
  allocationLimit?: number;
  oncePerCustomer?: boolean;
  usageLimit?: number;
  startDate?: Moment;
  endDate?: Moment;
  createdDate?: Moment;
  updatedDate?: Moment;
  prerequisiteSubtotalRange?: number;
  prerequisiteQuantityRange?: number;
  prerequisiteShippingPriceRange?: number;
  targetTypeId?: number;
  deductionValueTypeId?: number;
  customerSelectionId?: number;
  allocationMethodId?: number;
}

export class PriceRule implements IPriceRule {
  constructor(
    public id?: number,
    public deductionValue?: number,
    public allocationLimit?: number,
    public oncePerCustomer?: boolean,
    public usageLimit?: number,
    public startDate?: Moment,
    public endDate?: Moment,
    public createdDate?: Moment,
    public updatedDate?: Moment,
    public prerequisiteSubtotalRange?: number,
    public prerequisiteQuantityRange?: number,
    public prerequisiteShippingPriceRange?: number,
    public targetTypeId?: number,
    public deductionValueTypeId?: number,
    public customerSelectionId?: number,
    public allocationMethodId?: number
  ) {
    this.oncePerCustomer = this.oncePerCustomer || false;
  }
}
