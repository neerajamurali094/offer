/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { OfferTestModule } from '../../../../test.module';
import { AllocationMethodUpdateComponent } from 'app/entities/Offer/allocation-method/allocation-method-update.component';
import { AllocationMethodService } from 'app/entities/Offer/allocation-method/allocation-method.service';
import { AllocationMethod } from 'app/shared/model/Offer/allocation-method.model';

describe('Component Tests', () => {
  describe('AllocationMethod Management Update Component', () => {
    let comp: AllocationMethodUpdateComponent;
    let fixture: ComponentFixture<AllocationMethodUpdateComponent>;
    let service: AllocationMethodService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OfferTestModule],
        declarations: [AllocationMethodUpdateComponent]
      })
        .overrideTemplate(AllocationMethodUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AllocationMethodUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AllocationMethodService);
    });

    describe('save', () => {
      it(
        'Should call update service on save for existing entity',
        fakeAsync(() => {
          // GIVEN
          const entity = new AllocationMethod(123);
          spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.allocationMethod = entity;
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
          const entity = new AllocationMethod();
          spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.allocationMethod = entity;
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
