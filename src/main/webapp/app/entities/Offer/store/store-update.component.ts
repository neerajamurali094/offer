import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IStore } from 'app/shared/model/Offer/store.model';
import { StoreService } from './store.service';
import { IOffer } from 'app/shared/model/Offer/offer.model';
import { OfferService } from 'app/entities/Offer/offer';

@Component({
  selector: 'jhi-store-update',
  templateUrl: './store-update.component.html'
})
export class StoreUpdateComponent implements OnInit {
  store: IStore;
  isSaving: boolean;

  offers: IOffer[];

  constructor(
    private jhiAlertService: JhiAlertService,
    private storeService: StoreService,
    private offerService: OfferService,
    private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ store }) => {
      this.store = store;
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
    if (this.store.id !== undefined) {
      this.subscribeToSaveResponse(this.storeService.update(this.store));
    } else {
      this.subscribeToSaveResponse(this.storeService.create(this.store));
    }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<IStore>>) {
    result.subscribe((res: HttpResponse<IStore>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
