import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IOfferTarget } from 'app/shared/model/Offer/offer-target.model';

type EntityResponseType = HttpResponse<IOfferTarget>;
type EntityArrayResponseType = HttpResponse<IOfferTarget[]>;

@Injectable({ providedIn: 'root' })
export class OfferTargetService {
  public resourceUrl = SERVER_API_URL + 'api/offer-targets';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/offer-targets';

  constructor(private http: HttpClient) {}

  create(offerTarget: IOfferTarget): Observable<EntityResponseType> {
    return this.http.post<IOfferTarget>(this.resourceUrl, offerTarget, { observe: 'response' });
  }

  update(offerTarget: IOfferTarget): Observable<EntityResponseType> {
    return this.http.put<IOfferTarget>(this.resourceUrl, offerTarget, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IOfferTarget>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IOfferTarget[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IOfferTarget[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
