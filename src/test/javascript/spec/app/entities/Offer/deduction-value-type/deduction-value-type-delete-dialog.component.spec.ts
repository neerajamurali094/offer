/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { OfferTestModule } from '../../../../test.module';
import { DeductionValueTypeDeleteDialogComponent } from 'app/entities/Offer/deduction-value-type/deduction-value-type-delete-dialog.component';
import { DeductionValueTypeService } from 'app/entities/Offer/deduction-value-type/deduction-value-type.service';

describe('Component Tests', () => {
  describe('DeductionValueType Management Delete Component', () => {
    let comp: DeductionValueTypeDeleteDialogComponent;
    let fixture: ComponentFixture<DeductionValueTypeDeleteDialogComponent>;
    let service: DeductionValueTypeService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OfferTestModule],
        declarations: [DeductionValueTypeDeleteDialogComponent]
      })
        .overrideTemplate(DeductionValueTypeDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DeductionValueTypeDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DeductionValueTypeService);
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
