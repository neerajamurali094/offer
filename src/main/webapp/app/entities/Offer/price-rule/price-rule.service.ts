import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPriceRule } from 'app/shared/model/Offer/price-rule.model';

type EntityResponseType = HttpResponse<IPriceRule>;
type EntityArrayResponseType = HttpResponse<IPriceRule[]>;

@Injectable({ providedIn: 'root' })
export class PriceRuleService {
  public resourceUrl = SERVER_API_URL + 'api/price-rules';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/price-rules';

  constructor(private http: HttpClient) {}

  create(priceRule: IPriceRule): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(priceRule);
    return this.http
      .post<IPriceRule>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(priceRule: IPriceRule): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(priceRule);
    return this.http
      .put<IPriceRule>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPriceRule>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPriceRule[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPriceRule[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(priceRule: IPriceRule): IPriceRule {
    const copy: IPriceRule = Object.assign({}, priceRule, {
      startDate: priceRule.startDate != null && priceRule.startDate.isValid() ? priceRule.startDate.toJSON() : null,
      endDate: priceRule.endDate != null && priceRule.endDate.isValid() ? priceRule.endDate.toJSON() : null,
      createdDate: priceRule.createdDate != null && priceRule.createdDate.isValid() ? priceRule.createdDate.toJSON() : null,
      updatedDate: priceRule.updatedDate != null && priceRule.updatedDate.isValid() ? priceRule.updatedDate.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.startDate = res.body.startDate != null ? moment(res.body.startDate) : null;
      res.body.endDate = res.body.endDate != null ? moment(res.body.endDate) : null;
      res.body.createdDate = res.body.createdDate != null ? moment(res.body.createdDate) : null;
      res.body.updatedDate = res.body.updatedDate != null ? moment(res.body.updatedDate) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((priceRule: IPriceRule) => {
        priceRule.startDate = priceRule.startDate != null ? moment(priceRule.startDate) : null;
        priceRule.endDate = priceRule.endDate != null ? moment(priceRule.endDate) : null;
        priceRule.createdDate = priceRule.createdDate != null ? moment(priceRule.createdDate) : null;
        priceRule.updatedDate = priceRule.updatedDate != null ? moment(priceRule.updatedDate) : null;
      });
    }
    return res;
  }
}
