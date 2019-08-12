export interface ICountry {
  id?: number;
  countryName?: string;
  countryCode?: string;
  offerId?: number;
}

export class Country implements ICountry {
  constructor(public id?: number, public countryName?: string, public countryCode?: string, public offerId?: number) {}
}
