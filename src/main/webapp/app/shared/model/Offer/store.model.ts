export interface IStore {
  id?: number;
  storeId?: number;
  outletId?: number;
  offerId?: number;
}

export class Store implements IStore {
  constructor(public id?: number, public storeId?: number, public outletId?: number, public offerId?: number) {}
}
