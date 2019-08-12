/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { OfferTestModule } from '../../../../test.module';
import { DeductionValueTypeUpdateComponent } from 'app/entities/Offer/deduction-value-type/deduction-value-type-update.component';
import { DeductionValueTypeService } from 'app/entities/Offer/deduction-value-type/deduction-value-type.service';
import { DeductionValueType } from 'app/shared/model/Offer/deduction-value-type.model';

describe('Component Tests', () => {
  describe('DeductionValueType Management Update Component', () => {
    let comp: DeductionValueTypeUpdateComponent;
    let fixture: ComponentFixture<DeductionValueTypeUpdateComponent>;
    let service: DeductionValueTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OfferTestModule],
        declarations: [DeductionValueTypeUpdateComponent]
      })
        .overrideTemplate(DeductionValueTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DeductionValueTypeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DeductionValueTypeService);
    });

    describe('save', () => {
      it(
        'Should call update service on save for existing entity',
        fakeAsync(() => {
          // GIVEN
          const entity = new DeductionValueType(123);
          spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.deductionValueType = entity;
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
          const entity = new DeductionValueType();
          spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.deductionValueType = entity;
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
