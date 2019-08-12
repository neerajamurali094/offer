export interface IOrderRule {
  id?: number;
  prerequisiteOrderNumber?: number;
}

export class OrderRule implements IOrderRule {
  constructor(public id?: number, public prerequisiteOrderNumber?: number) {}
}
