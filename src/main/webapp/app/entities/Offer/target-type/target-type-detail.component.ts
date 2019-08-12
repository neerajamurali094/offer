import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITargetType } from 'app/shared/model/Offer/target-type.model';

@Component({
  selector: 'jhi-target-type-detail',
  templateUrl: './target-type-detail.component.html'
})
export class TargetTypeDetailComponent implements OnInit {
  targetType: ITargetType;

  constructor(private activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ targetType }) => {
      this.targetType = targetType;
    });
  }

  previousState() {
    window.history.back();
  }
}
