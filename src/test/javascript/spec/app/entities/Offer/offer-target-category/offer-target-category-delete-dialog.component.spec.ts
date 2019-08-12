/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { OfferTestModule } from '../../../../test.module';
import { OfferTargetCategoryDeleteDialogComponent } from 'app/entities/Offer/offer-target-category/offer-target-category-delete-dialog.component';
import { OfferTargetCategoryService } from 'app/entities/Offer/offer-target-category/offer-target-category.service';

describe('Component Tests', () => {
  describe('OfferTargetCategory Management Delete Component', () => {
    let comp: OfferTargetCategoryDeleteDialogComponent;
    let fixture: ComponentFixture<OfferTargetCategoryDeleteDialogComponent>;
    let service: OfferTargetCategoryService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OfferTestModule],
        declarations: [OfferTargetCategoryDeleteDialogComponent]
      })
        .overrideTemplate(OfferTargetCategoryDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OfferTargetCategoryDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OfferTargetCategoryService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
