/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { OfferTestModule } from '../../../../test.module';
import { OrderRuleUpdateComponent } from 'app/entities/Offer/order-rule/order-rule-update.component';
import { OrderRuleService } from 'app/entities/Offer/order-rule/order-rule.service';
import { OrderRule } from 'app/shared/model/Offer/order-rule.model';

describe('Component Tests', () => {
  describe('OrderRule Management Update Component', () => {
    let comp: OrderRuleUpdateComponent;
    let fixture: ComponentFixture<OrderRuleUpdateComponent>;
    let service: OrderRuleService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OfferTestModule],
        declarations: [OrderRuleUpdateComponent]
      })
        .overrideTemplate(OrderRuleUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OrderRuleUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OrderRuleService);
    });

    describe('save', () => {
      it(
        'Should call update service on save for existing entity',
        fakeAsync(() => {
          // GIVEN
          const entity = new OrderRule(123);
          spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.orderRule = entity;
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
          const entity = new OrderRule();
          spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.orderRule = entity;
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
