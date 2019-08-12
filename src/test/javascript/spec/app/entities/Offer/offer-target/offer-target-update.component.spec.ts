/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { OfferTestModule } from '../../../../test.module';
import { OfferTargetUpdateComponent } from 'app/entities/Offer/offer-target/offer-target-update.component';
import { OfferTargetService } from 'app/entities/Offer/offer-target/offer-target.service';
import { OfferTarget } from 'app/shared/model/Offer/offer-target.model';

describe('Component Tests', () => {
  describe('OfferTarget Management Update Component', () => {
    let comp: OfferTargetUpdateComponent;
    let fixture: ComponentFixture<OfferTargetUpdateComponent>;
    let service: OfferTargetService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OfferTestModule],
        declarations: [OfferTargetUpdateComponent]
      })
        .overrideTemplate(OfferTargetUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OfferTargetUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OfferTargetService);
    });

    describe('save', () => {
      it(
        'Should call update service on save for existing entity',
        fakeAsync(() => {
          // GIVEN
          const entity = new OfferTarget(123);
          spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.offerTarget = entity;
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
          const entity = new OfferTarget();
          spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.offerTarget = entity;
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
