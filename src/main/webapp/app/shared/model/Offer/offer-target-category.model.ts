export interface IOfferTargetCategory {
  id?: number;
  targetCategoryId?: number;
  offerId?: number;
}

export class OfferTargetCategory implements IOfferTargetCategory {
  constructor(public id?: number, public targetCategoryId?: number, public offerId?: number) {}
}
