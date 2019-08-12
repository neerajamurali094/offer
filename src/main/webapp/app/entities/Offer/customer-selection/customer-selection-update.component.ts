import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ICustomerSelection } from 'app/shared/model/Offer/customer-selection.model';
import { CustomerSelectionService } from './customer-selection.service';

@Component({
  selector: 'jhi-customer-selection-update',
  templateUrl: './customer-selection-update.component.html'
})
export class CustomerSelectionUpdateComponent implements OnInit {
  customerSelection: ICustomerSelection;
  isSaving: boolean;

  constructor(private customerSelectionService: CustomerSelectionService, private activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ customerSelection }) => {
      this.customerSelection = customerSelection;
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    if (this.customerSelection.id !== undefined) {
      this.subscribeToSaveResponse(this.customerSelectionService.update(this.customerSelection));
    } else {
      this.subscribeToSaveResponse(this.customerSelectionService.create(this.customerSelection));
    }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<ICustomerSelection>>) {
    result.subscribe((res: HttpResponse<ICustomerSelection>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  private onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  private onSaveError() {
    this.isSaving = false;
  }
}
