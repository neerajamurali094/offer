import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPriceRule } from 'app/shared/model/Offer/price-rule.model';
import { PriceRuleService } from './price-rule.service';

@Component({
  selector: 'jhi-price-rule-delete-dialog',
  templateUrl: './price-rule-delete-dialog.component.html'
})
export class PriceRuleDeleteDialogComponent {
  priceRule: IPriceRule;

  constructor(private priceRuleService: PriceRuleService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.priceRuleService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'priceRuleListModification',
        content: 'Deleted an priceRule'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-price-rule-delete-popup',
  template: ''
})
export class PriceRuleDeletePopupComponent implements OnInit, OnDestroy {
  private ngbModalRef: NgbModalRef;

  constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ priceRule }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(PriceRuleDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.priceRule = priceRule;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
