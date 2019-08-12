import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDeductionValueType } from 'app/shared/model/Offer/deduction-value-type.model';

type EntityResponseType = HttpResponse<IDeductionValueType>;
type EntityArrayResponseType = HttpResponse<IDeductionValueType[]>;

@Injectable({ providedIn: 'root' })
export class DeductionValueTypeService {
  public resourceUrl = SERVER_API_URL + 'api/deduction-value-types';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/deduction-value-types';

  constructor(private http: HttpClient) {}

  create(deductionValueType: IDeductionValueType): Observable<EntityResponseType> {
    return this.http.post<IDeductionValueType>(this.resourceUrl, deductionValueType, { observe: 'response' });
  }

  update(deductionValueType: IDeductionValueType): Observable<EntityResponseType> {
    return this.http.put<IDeductionValueType>(this.resourceUrl, deductionValueType, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDeductionValueType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDeductionValueType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDeductionValueType[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
