import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ICountry } from 'app/shared/model/Offer/country.model';
import { CountryService } from './country.service';
import { IOffer } from 'app/shared/model/Offer/offer.model';
import { OfferService } from 'app/entities/Offer/offer';

@Component({
  selector: 'jhi-country-update',
  templateUrl: './country-update.component.html'
})
export class CountryUpdateComponent implements OnInit {
  country: ICountry;
  isSaving: boolean;

  offers: IOffer[];

  constructor(
    private jhiAlertService: JhiAlertService,
    private countryService: CountryService,
    private offerService: OfferService,
    private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ country }) => {
      this.country = country;
    });
    this.offerService.query().subscribe(
      (res: HttpResponse<IOffer[]>) => {
        this.offers = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    if (this.country.id !== undefined) {
      this.subscribeToSaveResponse(this.countryService.update(this.country));
    } else {
      this.subscribeToSaveResponse(this.countryService.create(this.country));
    }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<ICountry>>) {
    result.subscribe((res: HttpResponse<ICountry>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  private onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  private onSaveError() {
    this.isSaving = false;
  }

  private onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackOfferById(index: number, item: IOffer) {
    return item.id;
  }
}
