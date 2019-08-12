import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IAllocationMethod } from 'app/shared/model/Offer/allocation-method.model';
import { AllocationMethodService } from './allocation-method.service';

@Component({
  selector: 'jhi-allocation-method-update',
  templateUrl: './allocation-method-update.component.html'
})
export class AllocationMethodUpdateComponent implements OnInit {
  allocationMethod: IAllocationMethod;
  isSaving: boolean;

  constructor(private allocationMethodService: AllocationMethodService, private activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ allocationMethod }) => {
      this.allocationMethod = allocationMethod;
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    if (this.allocationMethod.id !== undefined) {
      this.subscribeToSaveResponse(this.allocationMethodService.update(this.allocationMethod));
    } else {
      this.subscribeToSaveResponse(this.allocationMethodService.create(this.allocationMethod));
    }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<IAllocationMethod>>) {
    result.subscribe((res: HttpResponse<IAllocationMethod>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  private onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  private onSaveError() {
    this.isSaving = false;
  }
}
