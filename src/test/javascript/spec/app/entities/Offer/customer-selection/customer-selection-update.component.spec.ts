/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { OfferTestModule } from '../../../../test.module';
import { CustomerSelectionUpdateComponent } from 'app/entities/Offer/customer-selection/customer-selection-update.component';
import { CustomerSelectionService } from 'app/entities/Offer/customer-selection/customer-selection.service';
import { CustomerSelection } from 'app/shared/model/Offer/customer-selection.model';

describe('Component Tests', () => {
  describe('CustomerSelection Management Update Component', () => {
    let comp: CustomerSelectionUpdateComponent;
    let fixture: ComponentFixture<CustomerSelectionUpdateComponent>;
    let service: CustomerSelectionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OfferTestModule],
        declarations: [CustomerSelectionUpdateComponent]
      })
        .overrideTemplate(CustomerSelectionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CustomerSelectionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CustomerSelectionService);
    });

    describe('save', () => {
      it(
        'Should call update service on save for existing entity',
        fakeAsync(() => {
          // GIVEN
          const entity = new CustomerSelection(123);
          spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.customerSelection = entity;
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
          const entity = new CustomerSelection();
          spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.customerSelection = entity;
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
