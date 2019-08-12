/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { OfferTestModule } from '../../../../test.module';
import { AllocationMethodDetailComponent } from 'app/entities/Offer/allocation-method/allocation-method-detail.component';
import { AllocationMethod } from 'app/shared/model/Offer/allocation-method.model';

describe('Component Tests', () => {
  describe('AllocationMethod Management Detail Component', () => {
    let comp: AllocationMethodDetailComponent;
    let fixture: ComponentFixture<AllocationMethodDetailComponent>;
    const route = ({ data: of({ allocationMethod: new AllocationMethod(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OfferTestModule],
        declarations: [AllocationMethodDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AllocationMethodDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AllocationMethodDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.allocationMethod).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
