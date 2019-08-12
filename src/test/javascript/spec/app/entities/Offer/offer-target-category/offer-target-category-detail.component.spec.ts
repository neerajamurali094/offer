/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { OfferTestModule } from '../../../../test.module';
import { OfferTargetCategoryDetailComponent } from 'app/entities/Offer/offer-target-category/offer-target-category-detail.component';
import { OfferTargetCategory } from 'app/shared/model/Offer/offer-target-category.model';

describe('Component Tests', () => {
  describe('OfferTargetCategory Management Detail Component', () => {
    let comp: OfferTargetCategoryDetailComponent;
    let fixture: ComponentFixture<OfferTargetCategoryDetailComponent>;
    const route = ({ data: of({ offerTargetCategory: new OfferTargetCategory(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OfferTestModule],
        declarations: [OfferTargetCategoryDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(OfferTargetCategoryDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OfferTargetCategoryDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.offerTargetCategory).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
