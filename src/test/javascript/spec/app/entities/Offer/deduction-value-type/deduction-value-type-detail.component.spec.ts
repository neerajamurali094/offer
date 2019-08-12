/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { OfferTestModule } from '../../../../test.module';
import { DeductionValueTypeDetailComponent } from 'app/entities/Offer/deduction-value-type/deduction-value-type-detail.component';
import { DeductionValueType } from 'app/shared/model/Offer/deduction-value-type.model';

describe('Component Tests', () => {
  describe('DeductionValueType Management Detail Component', () => {
    let comp: DeductionValueTypeDetailComponent;
    let fixture: ComponentFixture<DeductionValueTypeDetailComponent>;
    const route = ({ data: of({ deductionValueType: new DeductionValueType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OfferTestModule],
        declarations: [DeductionValueTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(DeductionValueTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DeductionValueTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.deductionValueType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
