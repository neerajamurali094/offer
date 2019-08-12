/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { OfferTestModule } from '../../../../test.module';
import { AllocationMethodDeleteDialogComponent } from 'app/entities/Offer/allocation-method/allocation-method-delete-dialog.component';
import { AllocationMethodService } from 'app/entities/Offer/allocation-method/allocation-method.service';

describe('Component Tests', () => {
  describe('AllocationMethod Management Delete Component', () => {
    let comp: AllocationMethodDeleteDialogComponent;
    let fixture: ComponentFixture<AllocationMethodDeleteDialogComponent>;
    let service: AllocationMethodService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OfferTestModule],
        declarations: [AllocationMethodDeleteDialogComponent]
      })
        .overrideTemplate(AllocationMethodDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AllocationMethodDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AllocationMethodService);
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
