import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITargetType } from 'app/shared/model/Offer/target-type.model';

type EntityResponseType = HttpResponse<ITargetType>;
type EntityArrayResponseType = HttpResponse<ITargetType[]>;

@Injectable({ providedIn: 'root' })
export class TargetTypeService {
  public resourceUrl = SERVER_API_URL + 'api/target-types';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/target-types';

  constructor(private http: HttpClient) {}

  create(targetType: ITargetType): Observable<EntityResponseType> {
    return this.http.post<ITargetType>(this.resourceUrl, targetType, { observe: 'response' });
  }

  update(targetType: ITargetType): Observable<EntityResponseType> {
    return this.http.put<ITargetType>(this.resourceUrl, targetType, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITargetType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITargetType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITargetType[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
