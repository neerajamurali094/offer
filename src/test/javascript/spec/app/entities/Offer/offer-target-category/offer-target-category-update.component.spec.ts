/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { OfferTestModule } from '../../../../test.module';
import { OfferTargetCategoryUpdateComponent } from 'app/entities/Offer/offer-target-category/offer-target-category-update.component';
import { OfferTargetCategoryService } from 'app/entities/Offer/offer-target-category/offer-target-category.service';
import { OfferTargetCategory } from 'app/shared/model/Offer/offer-target-category.model';

describe('Component Tests', () => {
  describe('OfferTargetCategory Management Update Component', () => {
    let comp: OfferTargetCategoryUpdateComponent;
    let fixture: ComponentFixture<OfferTargetCategoryUpdateComponent>;
    let service: OfferTargetCategoryService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OfferTestModule],
        declarations: [OfferTargetCategoryUpdateComponent]
      })
        .overrideTemplate(OfferTargetCategoryUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OfferTargetCategoryUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OfferTargetCategoryService);
    });

    describe('save', () => {
      it(
        'Should call update service on save for existing entity',
        fakeAsync(() => {
          // GIVEN
          const entity = new OfferTargetCategory(123);
          spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.offerTargetCategory = entity;
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
          const entity = new OfferTargetCategory();
          spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.offerTargetCategory = entity;
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
