export interface IPaymentRule {
  id?: number;
  paymentMode?: string;
  authorizedProvider?: string;
  cashBackType?: number;
  cashBackValue?: number;
  numberOfTransactionLimit?: number;
}

export class PaymentRule implements IPaymentRule {
  constructor(
    public id?: number,
    public paymentMode?: string,
    public authorizedProvider?: string,
    public cashBackType?: number,
    public cashBackValue?: number,
    public numberOfTransactionLimit?: number
  ) {}
}
