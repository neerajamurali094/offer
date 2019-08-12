/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { OfferTestModule } from '../../../../test.module';
import { OfferTargetDetailComponent } from 'app/entities/Offer/offer-target/offer-target-detail.component';
import { OfferTarget } from 'app/shared/model/Offer/offer-target.model';

describe('Component Tests', () => {
  describe('OfferTarget Management Detail Component', () => {
    let comp: OfferTargetDetailComponent;
    let fixture: ComponentFixture<OfferTargetDetailComponent>;
    const route = ({ data: of({ offerTarget: new OfferTarget(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OfferTestModule],
        declarations: [OfferTargetDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(OfferTargetDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OfferTargetDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.offerTarget).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
