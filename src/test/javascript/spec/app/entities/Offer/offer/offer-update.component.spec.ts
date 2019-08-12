/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { OfferTestModule } from '../../../../test.module';
import { OfferUpdateComponent } from 'app/entities/Offer/offer/offer-update.component';
import { OfferService } from 'app/entities/Offer/offer/offer.service';
import { Offer } from 'app/shared/model/Offer/offer.model';

describe('Component Tests', () => {
  describe('Offer Management Update Component', () => {
    let comp: OfferUpdateComponent;
    let fixture: ComponentFixture<OfferUpdateComponent>;
    let service: OfferService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OfferTestModule],
        declarations: [OfferUpdateComponent]
      })
        .overrideTemplate(OfferUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OfferUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OfferService);
    });

    describe('save', () => {
      it(
        'Should call update service on save for existing entity',
        fakeAsync(() => {
          // GIVEN
          const entity = new Offer(123);
          spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.offer = entity;
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
          const entity = new Offer();
          spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.offer = entity;
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
