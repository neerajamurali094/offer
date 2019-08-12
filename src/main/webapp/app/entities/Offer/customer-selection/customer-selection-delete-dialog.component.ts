import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICustomerSelection } from 'app/shared/model/Offer/customer-selection.model';
import { CustomerSelectionService } from './customer-selection.service';

@Component({
  selector: 'jhi-customer-selection-delete-dialog',
  templateUrl: './customer-selection-delete-dialog.component.html'
})
export class CustomerSelectionDeleteDialogComponent {
  customerSelection: ICustomerSelection;

  constructor(
    private customerSelectionService: CustomerSelectionService,
    public activeModal: NgbActiveModal,
    private eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.customerSelectionService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'customerSelectionListModification',
        content: 'Deleted an customerSelection'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-customer-selection-delete-popup',
  template: ''
})
export class CustomerSelectionDeletePopupComponent implements OnInit, OnDestroy {
  private ngbModalRef: NgbModalRef;

  constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ customerSelection }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(CustomerSelectionDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.customerSelection = customerSelection;
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
