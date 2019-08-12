/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { OfferTestModule } from '../../../../test.module';
import { TargetTypeUpdateComponent } from 'app/entities/Offer/target-type/target-type-update.component';
import { TargetTypeService } from 'app/entities/Offer/target-type/target-type.service';
import { TargetType } from 'app/shared/model/Offer/target-type.model';

describe('Component Tests', () => {
  describe('TargetType Management Update Component', () => {
    let comp: TargetTypeUpdateComponent;
    let fixture: ComponentFixture<TargetTypeUpdateComponent>;
    let service: TargetTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OfferTestModule],
        declarations: [TargetTypeUpdateComponent]
      })
        .overrideTemplate(TargetTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TargetTypeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TargetTypeService);
    });

    describe('save', () => {
      it(
        'Should call update service on save for existing entity',
        fakeAsync(() => {
          // GIVEN
          const entity = new TargetType(123);
          spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.targetType = entity;
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
          const entity = new TargetType();
          spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.targetType = entity;
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
