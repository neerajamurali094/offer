/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { OfferTestModule } from '../../../../test.module';
import { PaymentRuleDetailComponent } from 'app/entities/Offer/payment-rule/payment-rule-detail.component';
import { PaymentRule } from 'app/shared/model/Offer/payment-rule.model';

describe('Component Tests', () => {
  describe('PaymentRule Management Detail Component', () => {
    let comp: PaymentRuleDetailComponent;
    let fixture: ComponentFixture<PaymentRuleDetailComponent>;
    const route = ({ data: of({ paymentRule: new PaymentRule(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OfferTestModule],
        declarations: [PaymentRuleDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PaymentRuleDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PaymentRuleDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.paymentRule).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
