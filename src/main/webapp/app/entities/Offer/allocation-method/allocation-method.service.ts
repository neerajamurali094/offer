import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAllocationMethod } from 'app/shared/model/Offer/allocation-method.model';

type EntityResponseType = HttpResponse<IAllocationMethod>;
type EntityArrayResponseType = HttpResponse<IAllocationMethod[]>;

@Injectable({ providedIn: 'root' })
export class AllocationMethodService {
  public resourceUrl = SERVER_API_URL + 'api/allocation-methods';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/allocation-methods';

  constructor(private http: HttpClient) {}

  create(allocationMethod: IAllocationMethod): Observable<EntityResponseType> {
    return this.http.post<IAllocationMethod>(this.resourceUrl, allocationMethod, { observe: 'response' });
  }

  update(allocationMethod: IAllocationMethod): Observable<EntityResponseType> {
    return this.http.put<IAllocationMethod>(this.resourceUrl, allocationMethod, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAllocationMethod>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAllocationMethod[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAllocationMethod[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
