import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IOfferTarget } from 'app/shared/model/Offer/offer-target.model';
import { OfferTargetService } from './offer-target.service';

@Component({
  selector: 'jhi-offer-target-delete-dialog',
  templateUrl: './offer-target-delete-dialog.component.html'
})
export class OfferTargetDeleteDialogComponent {
  offerTarget: IOfferTarget;

  constructor(private offerTargetService: OfferTargetService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.offerTargetService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'offerTargetListModification',
        content: 'Deleted an offerTarget'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-offer-target-delete-popup',
  template: ''
})
export class OfferTargetDeletePopupComponent implements OnInit, OnDestroy {
  private ngbModalRef: NgbModalRef;

  constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ offerTarget }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(OfferTargetDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.offerTarget = offerTarget;
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
