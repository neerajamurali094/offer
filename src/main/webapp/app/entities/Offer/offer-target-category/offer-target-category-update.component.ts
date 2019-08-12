import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IOfferTargetCategory } from 'app/shared/model/Offer/offer-target-category.model';
import { OfferTargetCategoryService } from './offer-target-category.service';
import { IOffer } from 'app/shared/model/Offer/offer.model';
import { OfferService } from 'app/entities/Offer/offer';

@Component({
  selector: 'jhi-offer-target-category-update',
  templateUrl: './offer-target-category-update.component.html'
})
export class OfferTargetCategoryUpdateComponent implements OnInit {
  offerTargetCategory: IOfferTargetCategory;
  isSaving: boolean;

  offers: IOffer[];

  constructor(
    private jhiAlertService: JhiAlertService,
    private offerTargetCategoryService: OfferTargetCategoryService,
    private offerService: OfferService,
    private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ offerTargetCategory }) => {
      this.offerTargetCategory = offerTargetCategory;
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
    if (this.offerTargetCategory.id !== undefined) {
      this.subscribeToSaveResponse(this.offerTargetCategoryService.update(this.offerTargetCategory));
    } else {
      this.subscribeToSaveResponse(this.offerTargetCategoryService.create(this.offerTargetCategory));
    }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<IOfferTargetCategory>>) {
    result.subscribe((res: HttpResponse<IOfferTargetCategory>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
