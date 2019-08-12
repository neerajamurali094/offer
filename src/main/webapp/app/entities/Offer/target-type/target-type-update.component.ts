import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ITargetType } from 'app/shared/model/Offer/target-type.model';
import { TargetTypeService } from './target-type.service';

@Component({
  selector: 'jhi-target-type-update',
  templateUrl: './target-type-update.component.html'
})
export class TargetTypeUpdateComponent implements OnInit {
  targetType: ITargetType;
  isSaving: boolean;

  constructor(private targetTypeService: TargetTypeService, private activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ targetType }) => {
      this.targetType = targetType;
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    if (this.targetType.id !== undefined) {
      this.subscribeToSaveResponse(this.targetTypeService.update(this.targetType));
    } else {
      this.subscribeToSaveResponse(this.targetTypeService.create(this.targetType));
    }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<ITargetType>>) {
    result.subscribe((res: HttpResponse<ITargetType>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  private onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  private onSaveError() {
    this.isSaving = false;
  }
}
