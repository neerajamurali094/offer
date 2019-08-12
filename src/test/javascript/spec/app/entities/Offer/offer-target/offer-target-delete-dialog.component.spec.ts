/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { OfferTestModule } from '../../../../test.module';
import { OfferTargetDeleteDialogComponent } from 'app/entities/Offer/offer-target/offer-target-delete-dialog.component';
import { OfferTargetService } from 'app/entities/Offer/offer-target/offer-target.service';

describe('Component Tests', () => {
  describe('OfferTarget Management Delete Component', () => {
    let comp: OfferTargetDeleteDialogComponent;
    let fixture: ComponentFixture<OfferTargetDeleteDialogComponent>;
    let service: OfferTargetService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OfferTestModule],
        declarations: [OfferTargetDeleteDialogComponent]
      })
        .overrideTemplate(OfferTargetDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OfferTargetDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OfferTargetService);
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
