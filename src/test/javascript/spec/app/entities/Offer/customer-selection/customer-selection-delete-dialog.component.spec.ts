/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { OfferTestModule } from '../../../../test.module';
import { CustomerSelectionDeleteDialogComponent } from 'app/entities/Offer/customer-selection/customer-selection-delete-dialog.component';
import { CustomerSelectionService } from 'app/entities/Offer/customer-selection/customer-selection.service';

describe('Component Tests', () => {
  describe('CustomerSelection Management Delete Component', () => {
    let comp: CustomerSelectionDeleteDialogComponent;
    let fixture: ComponentFixture<CustomerSelectionDeleteDialogComponent>;
    let service: CustomerSelectionService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OfferTestModule],
        declarations: [CustomerSelectionDeleteDialogComponent]
      })
        .overrideTemplate(CustomerSelectionDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CustomerSelectionDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CustomerSelectionService);
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
