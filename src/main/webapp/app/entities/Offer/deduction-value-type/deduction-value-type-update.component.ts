import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IDeductionValueType } from 'app/shared/model/Offer/deduction-value-type.model';
import { DeductionValueTypeService } from './deduction-value-type.service';

@Component({
  selector: 'jhi-deduction-value-type-update',
  templateUrl: './deduction-value-type-update.component.html'
})
export class DeductionValueTypeUpdateComponent implements OnInit {
  deductionValueType: IDeductionValueType;
  isSaving: boolean;

  constructor(private deductionValueTypeService: DeductionValueTypeService, private activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ deductionValueType }) => {
      this.deductionValueType = deductionValueType;
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    if (this.deductionValueType.id !== undefined) {
      this.subscribeToSaveResponse(this.deductionValueTypeService.update(this.deductionValueType));
    } else {
      this.subscribeToSaveResponse(this.deductionValueTypeService.create(this.deductionValueType));
    }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<IDeductionValueType>>) {
    result.subscribe((res: HttpResponse<IDeductionValueType>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  private onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  private onSaveError() {
    this.isSaving = false;
  }
}
