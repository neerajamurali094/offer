/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { OfferTestModule } from '../../../../test.module';
import { OrderRuleDetailComponent } from 'app/entities/Offer/order-rule/order-rule-detail.component';
import { OrderRule } from 'app/shared/model/Offer/order-rule.model';

describe('Component Tests', () => {
  describe('OrderRule Management Detail Component', () => {
    let comp: OrderRuleDetailComponent;
    let fixture: ComponentFixture<OrderRuleDetailComponent>;
    const route = ({ data: of({ orderRule: new OrderRule(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OfferTestModule],
        declarations: [OrderRuleDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(OrderRuleDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OrderRuleDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.orderRule).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
