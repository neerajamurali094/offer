import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDeductionValueType } from 'app/shared/model/Offer/deduction-value-type.model';
import { DeductionValueTypeService } from './deduction-value-type.service';

@Component({
  selector: 'jhi-deduction-value-type-delete-dialog',
  templateUrl: './deduction-value-type-delete-dialog.component.html'
})
export class DeductionValueTypeDeleteDialogComponent {
  deductionValueType: IDeductionValueType;

  constructor(
    private deductionValueTypeService: DeductionValueTypeService,
    public activeModal: NgbActiveModal,
    private eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.deductionValueTypeService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'deductionValueTypeListModification',
        content: 'Deleted an deductionValueType'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-deduction-value-type-delete-popup',
  template: ''
})
export class DeductionValueTypeDeletePopupComponent implements OnInit, OnDestroy {
  private ngbModalRef: NgbModalRef;

  constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ deductionValueType }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(DeductionValueTypeDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.deductionValueType = deductionValueType;
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
