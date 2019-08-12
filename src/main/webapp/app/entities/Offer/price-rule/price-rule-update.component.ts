import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IPriceRule } from 'app/shared/model/Offer/price-rule.model';
import { PriceRuleService } from './price-rule.service';
import { ITargetType } from 'app/shared/model/Offer/target-type.model';
import { TargetTypeService } from 'app/entities/Offer/target-type';
import { IDeductionValueType } from 'app/shared/model/Offer/deduction-value-type.model';
import { DeductionValueTypeService } from 'app/entities/Offer/deduction-value-type';
import { ICustomerSelection } from 'app/shared/model/Offer/customer-selection.model';
import { CustomerSelectionService } from 'app/entities/Offer/customer-selection';
import { IAllocationMethod } from 'app/shared/model/Offer/allocation-method.model';
import { AllocationMethodService } from 'app/entities/Offer/allocation-method';

@Component({
  selector: 'jhi-price-rule-update',
  templateUrl: './price-rule-update.component.html'
})
export class PriceRuleUpdateComponent implements OnInit {
  priceRule: IPriceRule;
  isSaving: boolean;

  targettypes: ITargetType[];

  deductionvaluetypes: IDeductionValueType[];

  customerselections: ICustomerSelection[];

  allocationmethods: IAllocationMethod[];
  startDate: string;
  endDate: string;
  createdDate: string;
  updatedDate: string;

  constructor(
    private jhiAlertService: JhiAlertService,
    private priceRuleService: PriceRuleService,
    private targetTypeService: TargetTypeService,
    private deductionValueTypeService: DeductionValueTypeService,
    private customerSelectionService: CustomerSelectionService,
    private allocationMethodService: AllocationMethodService,
    private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ priceRule }) => {
      this.priceRule = priceRule;
      this.startDate = this.priceRule.startDate != null ? this.priceRule.startDate.format(DATE_TIME_FORMAT) : null;
      this.endDate = this.priceRule.endDate != null ? this.priceRule.endDate.format(DATE_TIME_FORMAT) : null;
      this.createdDate = this.priceRule.createdDate != null ? this.priceRule.createdDate.format(DATE_TIME_FORMAT) : null;
      this.updatedDate = this.priceRule.updatedDate != null ? this.priceRule.updatedDate.format(DATE_TIME_FORMAT) : null;
    });
    this.targetTypeService.query({ filter: 'pricerule-is-null' }).subscribe(
      (res: HttpResponse<ITargetType[]>) => {
        if (!this.priceRule.targetTypeId) {
          this.targettypes = res.body;
        } else {
          this.targetTypeService.find(this.priceRule.targetTypeId).subscribe(
            (subRes: HttpResponse<ITargetType>) => {
              this.targettypes = [subRes.body].concat(res.body);
            },
            (subRes: HttpErrorResponse) => this.onError(subRes.message)
          );
        }
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
    this.deductionValueTypeService.query().subscribe(
      (res: HttpResponse<IDeductionValueType[]>) => {
        this.deductionvaluetypes = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
    this.customerSelectionService.query().subscribe(
      (res: HttpResponse<ICustomerSelection[]>) => {
        this.customerselections = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
    this.allocationMethodService.query().subscribe(
      (res: HttpResponse<IAllocationMethod[]>) => {
        this.allocationmethods = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    this.priceRule.startDate = this.startDate != null ? moment(this.startDate, DATE_TIME_FORMAT) : null;
    this.priceRule.endDate = this.endDate != null ? moment(this.endDate, DATE_TIME_FORMAT) : null;
    this.priceRule.createdDate = this.createdDate != null ? moment(this.createdDate, DATE_TIME_FORMAT) : null;
    this.priceRule.updatedDate = this.updatedDate != null ? moment(this.updatedDate, DATE_TIME_FORMAT) : null;
    if (this.priceRule.id !== undefined) {
      this.subscribeToSaveResponse(this.priceRuleService.update(this.priceRule));
    } else {
      this.subscribeToSaveResponse(this.priceRuleService.create(this.priceRule));
    }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<IPriceRule>>) {
    result.subscribe((res: HttpResponse<IPriceRule>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  private onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  private onSaveError() {
    this.isSaving = false;
  }

  private onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackTargetTypeById(index: number, item: ITargetType) {
    return item.id;
  }

  trackDeductionValueTypeById(index: number, item: IDeductionValueType) {
    return item.id;
  }

  trackCustomerSelectionById(index: number, item: ICustomerSelection) {
    return item.id;
  }

  trackAllocationMethodById(index: number, item: IAllocationMethod) {
    return item.id;
  }
}
