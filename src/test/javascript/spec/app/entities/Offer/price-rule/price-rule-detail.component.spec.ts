/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { OfferTestModule } from '../../../../test.module';
import { PriceRuleDetailComponent } from 'app/entities/Offer/price-rule/price-rule-detail.component';
import { PriceRule } from 'app/shared/model/Offer/price-rule.model';

describe('Component Tests', () => {
  describe('PriceRule Management Detail Component', () => {
    let comp: PriceRuleDetailComponent;
    let fixture: ComponentFixture<PriceRuleDetailComponent>;
    const route = ({ data: of({ priceRule: new PriceRule(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OfferTestModule],
        declarations: [PriceRuleDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PriceRuleDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PriceRuleDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.priceRule).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
