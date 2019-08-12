import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICustomerSelection } from 'app/shared/model/Offer/customer-selection.model';

@Component({
  selector: 'jhi-customer-selection-detail',
  templateUrl: './customer-selection-detail.component.html'
})
export class CustomerSelectionDetailComponent implements OnInit {
  customerSelection: ICustomerSelection;

  constructor(private activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ customerSelection }) => {
      this.customerSelection = customerSelection;
    });
  }

  previousState() {
    window.history.back();
  }
}
