/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { OfferTestModule } from '../../../../test.module';
import { StoreUpdateComponent } from 'app/entities/Offer/store/store-update.component';
import { StoreService } from 'app/entities/Offer/store/store.service';
import { Store } from 'app/shared/model/Offer/store.model';

describe('Component Tests', () => {
  describe('Store Management Update Component', () => {
    let comp: StoreUpdateComponent;
    let fixture: ComponentFixture<StoreUpdateComponent>;
    let service: StoreService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OfferTestModule],
        declarations: [StoreUpdateComponent]
      })
        .overrideTemplate(StoreUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(StoreUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(StoreService);
    });

    describe('save', () => {
      it(
        'Should call update service on save for existing entity',
        fakeAsync(() => {
          // GIVEN
          const entity = new Store(123);
          spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.store = entity;
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
          const entity = new Store();
          spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.store = entity;
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
