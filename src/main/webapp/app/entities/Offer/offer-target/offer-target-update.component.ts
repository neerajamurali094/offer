import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IOfferTarget } from 'app/shared/model/Offer/offer-target.model';
import { OfferTargetService } from './offer-target.service';
import { IOffer } from 'app/shared/model/Offer/offer.model';
import { OfferService } from 'app/entities/Offer/offer';

@Component({
  selector: 'jhi-offer-target-update',
  templateUrl: './offer-target-update.component.html'
})
export class OfferTargetUpdateComponent implements OnInit {
  offerTarget: IOfferTarget;
  isSaving: boolean;

  offers: IOffer[];

  constructor(
    private jhiAlertService: JhiAlertService,
    private offerTargetService: OfferTargetService,
    private offerService: OfferService,
    private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ offerTarget }) => {
      this.offerTarget = offerTarget;
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
    if (this.offerTarget.id !== undefined) {
      this.subscribeToSaveResponse(this.offerTargetService.update(this.offerTarget));
    } else {
      this.subscribeToSaveResponse(this.offerTargetService.create(this.offerTarget));
    }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<IOfferTarget>>) {
    result.subscribe((res: HttpResponse<IOfferTarget>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
