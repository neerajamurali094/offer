import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAllocationMethod } from 'app/shared/model/Offer/allocation-method.model';
import { AllocationMethodService } from './allocation-method.service';

@Component({
  selector: 'jhi-allocation-method-delete-dialog',
  templateUrl: './allocation-method-delete-dialog.component.html'
})
export class AllocationMethodDeleteDialogComponent {
  allocationMethod: IAllocationMethod;

  constructor(
    private allocationMethodService: AllocationMethodService,
    public activeModal: NgbActiveModal,
    private eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.allocationMethodService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'allocationMethodListModification',
        content: 'Deleted an allocationMethod'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-allocation-method-delete-popup',
  template: ''
})
export class AllocationMethodDeletePopupComponent implements OnInit, OnDestroy {
  private ngbModalRef: NgbModalRef;

  constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ allocationMethod }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(AllocationMethodDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.allocationMethod = allocationMethod;
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
