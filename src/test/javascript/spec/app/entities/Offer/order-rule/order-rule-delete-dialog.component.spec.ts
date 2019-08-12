/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { OfferTestModule } from '../../../../test.module';
import { OrderRuleDeleteDialogComponent } from 'app/entities/Offer/order-rule/order-rule-delete-dialog.component';
import { OrderRuleService } from 'app/entities/Offer/order-rule/order-rule.service';

describe('Component Tests', () => {
  describe('OrderRule Management Delete Component', () => {
    let comp: OrderRuleDeleteDialogComponent;
    let fixture: ComponentFixture<OrderRuleDeleteDialogComponent>;
    let service: OrderRuleService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OfferTestModule],
        declarations: [OrderRuleDeleteDialogComponent]
      })
        .overrideTemplate(OrderRuleDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OrderRuleDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OrderRuleService);
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
