/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { OfferTestModule } from '../../../../test.module';
import { PaymentRuleUpdateComponent } from 'app/entities/Offer/payment-rule/payment-rule-update.component';
import { PaymentRuleService } from 'app/entities/Offer/payment-rule/payment-rule.service';
import { PaymentRule } from 'app/shared/model/Offer/payment-rule.model';

describe('Component Tests', () => {
  describe('PaymentRule Management Update Component', () => {
    let comp: PaymentRuleUpdateComponent;
    let fixture: ComponentFixture<PaymentRuleUpdateComponent>;
    let service: PaymentRuleService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OfferTestModule],
        declarations: [PaymentRuleUpdateComponent]
      })
        .overrideTemplate(PaymentRuleUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PaymentRuleUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PaymentRuleService);
    });

    describe('save', () => {
      it(
        'Should call update service on save for existing entity',
        fakeAsync(() => {
          // GIVEN
          const entity = new PaymentRule(123);
          spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.paymentRule = entity;
          // WHEN
          comp.save();
          tick(); // simulate async

          // THEN
          expect(service.update).toHaveBeenCalledWith(entity);
          expect(comp.isSaving).toEqual(false);
        })
      );

      it(
        'Should call create service on save for new entity',
        fakeAsync(() => {
          // GIVEN
          const entity = new PaymentRule();
          spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.paymentRule = entity;
          // WHEN
          comp.save();
          tick(); // simulate async

          // THEN
          expect(service.create).toHaveBeenCalledWith(entity);
          expect(comp.isSaving).toEqual(false);
        })
      );
    });
  });
});
