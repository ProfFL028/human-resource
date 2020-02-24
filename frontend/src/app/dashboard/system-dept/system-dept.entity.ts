export class SystemDept {
  id: number;
  fullName: string;
  shortName: string;
  deptNumber: string;
  sortNumber: string;
  beginDate;
  endDate;
  parent?: SystemDept;
  children?: SystemDept[];
}
