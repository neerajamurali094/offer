export interface ITargetType {
  id?: number;
  targetType?: string;
}

export class TargetType implements ITargetType {
  constructor(public id?: number, public targetType?: string) {}
}
