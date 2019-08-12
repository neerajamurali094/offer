/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { OfferTestModule } from '../../../../test.module';
import { PriceRuleUpdateComponent } from 'app/entities/Offer/price-rule/price-rule-update.component';
import { PriceRuleService } from 'app/entities/Offer/price-rule/price-rule.service';
import { PriceRule } from 'app/shared/model/Offer/price-rule.model';

describe('Component Tests', () => {
  describe('PriceRule Management Update Component', () => {
    let comp: PriceRuleUpdateComponent;
    let fixture: ComponentFixture<PriceRuleUpdateComponent>;
    let service: PriceRuleService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OfferTestModule],
        declarations: [PriceRuleUpdateComponent]
      })
        .overrideTemplate(PriceRuleUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PriceRuleUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PriceRuleService);
    });

    describe('save', () => {
      it(
        'Should call update service on save for existing entity',
        fakeAsync(() => {
          // GIVEN
          const entity = new PriceRule(123);
          spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.priceRule = entity;
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
          const entity = new PriceRule();
          spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.priceRule = entity;
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
