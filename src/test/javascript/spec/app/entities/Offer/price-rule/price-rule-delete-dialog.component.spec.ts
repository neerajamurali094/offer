/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { OfferTestModule } from '../../../../test.module';
import { PriceRuleDeleteDialogComponent } from 'app/entities/Offer/price-rule/price-rule-delete-dialog.component';
import { PriceRuleService } from 'app/entities/Offer/price-rule/price-rule.service';

describe('Component Tests', () => {
  describe('PriceRule Management Delete Component', () => {
    let comp: PriceRuleDeleteDialogComponent;
    let fixture: ComponentFixture<PriceRuleDeleteDialogComponent>;
    let service: PriceRuleService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OfferTestModule],
        declarations: [PriceRuleDeleteDialogComponent]
      })
        .overrideTemplate(PriceRuleDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PriceRuleDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PriceRuleService);
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
