import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPaymentRule } from 'app/shared/model/Offer/payment-rule.model';
import { PaymentRuleService } from './payment-rule.service';

@Component({
  selector: 'jhi-payment-rule-delete-dialog',
  templateUrl: './payment-rule-delete-dialog.component.html'
})
export class PaymentRuleDeleteDialogComponent {
  paymentRule: IPaymentRule;

  constructor(private paymentRuleService: PaymentRuleService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.paymentRuleService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'paymentRuleListModification',
        content: 'Deleted an paymentRule'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-payment-rule-delete-popup',
  template: ''
})
export class PaymentRuleDeletePopupComponent implements OnInit, OnDestroy {
  private ngbModalRef: NgbModalRef;

  constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ paymentRule }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(PaymentRuleDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.paymentRule = paymentRule;
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
