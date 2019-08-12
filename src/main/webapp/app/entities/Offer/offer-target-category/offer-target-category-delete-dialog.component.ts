import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IOfferTargetCategory } from 'app/shared/model/Offer/offer-target-category.model';
import { OfferTargetCategoryService } from './offer-target-category.service';

@Component({
  selector: 'jhi-offer-target-category-delete-dialog',
  templateUrl: './offer-target-category-delete-dialog.component.html'
})
export class OfferTargetCategoryDeleteDialogComponent {
  offerTargetCategory: IOfferTargetCategory;

  constructor(
    private offerTargetCategoryService: OfferTargetCategoryService,
    public activeModal: NgbActiveModal,
    private eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.offerTargetCategoryService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'offerTargetCategoryListModification',
        content: 'Deleted an offerTargetCategory'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-offer-target-category-delete-popup',
  template: ''
})
export class OfferTargetCategoryDeletePopupComponent implements OnInit, OnDestroy {
  private ngbModalRef: NgbModalRef;

  constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ offerTargetCategory }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(OfferTargetCategoryDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.offerTargetCategory = offerTargetCategory;
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
