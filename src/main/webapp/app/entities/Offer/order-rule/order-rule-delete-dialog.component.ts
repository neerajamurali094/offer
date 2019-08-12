import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IOrderRule } from 'app/shared/model/Offer/order-rule.model';
import { OrderRuleService } from './order-rule.service';

@Component({
  selector: 'jhi-order-rule-delete-dialog',
  templateUrl: './order-rule-delete-dialog.component.html'
})
export class OrderRuleDeleteDialogComponent {
  orderRule: IOrderRule;

  constructor(private orderRuleService: OrderRuleService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.orderRuleService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'orderRuleListModification',
        content: 'Deleted an orderRule'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-order-rule-delete-popup',
  template: ''
})
export class OrderRuleDeletePopupComponent implements OnInit, OnDestroy {
  private ngbModalRef: NgbModalRef;

  constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ orderRule }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(OrderRuleDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.orderRule = orderRule;
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
