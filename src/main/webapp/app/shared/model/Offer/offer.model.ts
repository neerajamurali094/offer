import { Moment } from 'moment';
import { IStore } from 'app/shared/model/Offer/store.model';
import { IOfferTarget } from 'app/shared/model/Offer/offer-target.model';
import { IOfferTargetCategory } from 'app/shared/model/Offer/offer-target-category.model';
import { ICountry } from 'app/shared/model/Offer/country.model';

export interface IOffer {
  id?: number;
  title?: string;
  description?: string;
  createdDate?: Moment;
  updatedDate?: Moment;
  promoCode?: string;
  usageCount?: number;
  priceRuleId?: number;
  paymentRuleId?: number;
  orderRuleId?: number;
  stores?: IStore[];
  offerTargets?: IOfferTarget[];
  offerTargetCategories?: IOfferTargetCategory[];
  countries?: ICountry[];
}

export class Offer implements IOffer {
  constructor(
    public id?: number,
    public title?: string,
    public description?: string,
    public createdDate?: Moment,
    public updatedDate?: Moment,
    public promoCode?: string,
    public usageCount?: number,
    public priceRuleId?: number,
    public paymentRuleId?: number,
    public orderRuleId?: number,
    public stores?: IStore[],
    public offerTargets?: IOfferTarget[],
    public offerTargetCategories?: IOfferTargetCategory[],
    public countries?: ICountry[]
  ) {}
}
