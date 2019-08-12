/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { OfferTestModule } from '../../../../test.module';
import { TargetTypeDetailComponent } from 'app/entities/Offer/target-type/target-type-detail.component';
import { TargetType } from 'app/shared/model/Offer/target-type.model';

describe('Component Tests', () => {
  describe('TargetType Management Detail Component', () => {
    let comp: TargetTypeDetailComponent;
    let fixture: ComponentFixture<TargetTypeDetailComponent>;
    const route = ({ data: of({ targetType: new TargetType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OfferTestModule],
        declarations: [TargetTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TargetTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TargetTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.targetType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
