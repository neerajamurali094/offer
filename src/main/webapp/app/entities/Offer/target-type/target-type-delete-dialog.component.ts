import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITargetType } from 'app/shared/model/Offer/target-type.model';
import { TargetTypeService } from './target-type.service';

@Component({
  selector: 'jhi-target-type-delete-dialog',
  templateUrl: './target-type-delete-dialog.component.html'
})
export class TargetTypeDeleteDialogComponent {
  targetType: ITargetType;

  constructor(private targetTypeService: TargetTypeService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.targetTypeService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'targetTypeListModification',
        content: 'Deleted an targetType'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-target-type-delete-popup',
  template: ''
})
export class TargetTypeDeletePopupComponent implements OnInit, OnDestroy {
  private ngbModalRef: NgbModalRef;

  constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ targetType }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(TargetTypeDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.targetType = targetType;
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
