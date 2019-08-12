/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { OfferTestModule } from '../../../../test.module';
import { CustomerSelectionDetailComponent } from 'app/entities/Offer/customer-selection/customer-selection-detail.component';
import { CustomerSelection } from 'app/shared/model/Offer/customer-selection.model';

describe('Component Tests', () => {
  describe('CustomerSelection Management Detail Component', () => {
    let comp: CustomerSelectionDetailComponent;
    let fixture: ComponentFixture<CustomerSelectionDetailComponent>;
    const route = ({ data: of({ customerSelection: new CustomerSelection(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OfferTestModule],
        declarations: [CustomerSelectionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CustomerSelectionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CustomerSelectionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.customerSelection).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
