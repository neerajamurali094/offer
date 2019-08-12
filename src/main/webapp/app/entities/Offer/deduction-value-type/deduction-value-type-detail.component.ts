import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDeductionValueType } from 'app/shared/model/Offer/deduction-value-type.model';

@Component({
  selector: 'jhi-deduction-value-type-detail',
  templateUrl: './deduction-value-type-detail.component.html'
})
export class DeductionValueTypeDetailComponent implements OnInit {
  deductionValueType: IDeductionValueType;

  constructor(private activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ deductionValueType }) => {
      this.deductionValueType = deductionValueType;
    });
  }

  previousState() {
    window.history.back();
  }
}
