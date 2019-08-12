import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAllocationMethod } from 'app/shared/model/Offer/allocation-method.model';

@Component({
  selector: 'jhi-allocation-method-detail',
  templateUrl: './allocation-method-detail.component.html'
})
export class AllocationMethodDetailComponent implements OnInit {
  allocationMethod: IAllocationMethod;

  constructor(private activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ allocationMethod }) => {
      this.allocationMethod = allocationMethod;
    });
  }

  previousState() {
    window.history.back();
  }
}
