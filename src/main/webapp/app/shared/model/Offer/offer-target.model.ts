export interface IOfferTarget {
  id?: number;
  targetId?: number;
  offerId?: number;
}

export class OfferTarget implements IOfferTarget {
  constructor(public id?: number, public targetId?: number, public offerId?: number) {}
}
