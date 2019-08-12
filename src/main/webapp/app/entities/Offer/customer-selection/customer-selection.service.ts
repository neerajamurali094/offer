import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICustomerSelection } from 'app/shared/model/Offer/customer-selection.model';

type EntityResponseType = HttpResponse<ICustomerSelection>;
type EntityArrayResponseType = HttpResponse<ICustomerSelection[]>;

@Injectable({ providedIn: 'root' })
export class CustomerSelectionService {
  public resourceUrl = SERVER_API_URL + 'api/customer-selections';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/customer-selections';

  constructor(private http: HttpClient) {}

  create(customerSelection: ICustomerSelection): Observable<EntityResponseType> {
    return this.http.post<ICustomerSelection>(this.resourceUrl, customerSelection, { observe: 'response' });
  }

  update(customerSelection: ICustomerSelection): Observable<EntityResponseType> {
    return this.http.put<ICustomerSelection>(this.resourceUrl, customerSelection, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICustomerSelection>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICustomerSelection[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICustomerSelection[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
