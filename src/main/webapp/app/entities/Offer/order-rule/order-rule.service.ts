import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IOrderRule } from 'app/shared/model/Offer/order-rule.model';

type EntityResponseType = HttpResponse<IOrderRule>;
type EntityArrayResponseType = HttpResponse<IOrderRule[]>;

@Injectable({ providedIn: 'root' })
export class OrderRuleService {
  public resourceUrl = SERVER_API_URL + 'api/order-rules';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/order-rules';

  constructor(private http: HttpClient) {}

  create(orderRule: IOrderRule): Observable<EntityResponseType> {
    return this.http.post<IOrderRule>(this.resourceUrl, orderRule, { observe: 'response' });
  }

  update(orderRule: IOrderRule): Observable<EntityResponseType> {
    return this.http.put<IOrderRule>(this.resourceUrl, orderRule, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IOrderRule>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IOrderRule[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IOrderRule[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
