import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IOfferTargetCategory } from 'app/shared/model/Offer/offer-target-category.model';

type EntityResponseType = HttpResponse<IOfferTargetCategory>;
type EntityArrayResponseType = HttpResponse<IOfferTargetCategory[]>;

@Injectable({ providedIn: 'root' })
export class OfferTargetCategoryService {
  public resourceUrl = SERVER_API_URL + 'api/offer-target-categories';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/offer-target-categories';

  constructor(private http: HttpClient) {}

  create(offerTargetCategory: IOfferTargetCategory): Observable<EntityResponseType> {
    return this.http.post<IOfferTargetCategory>(this.resourceUrl, offerTargetCategory, { observe: 'response' });
  }

  update(offerTargetCategory: IOfferTargetCategory): Observable<EntityResponseType> {
    return this.http.put<IOfferTargetCategory>(this.resourceUrl, offerTargetCategory, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IOfferTargetCategory>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IOfferTargetCategory[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IOfferTargetCategory[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
