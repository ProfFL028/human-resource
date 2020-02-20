export class SystemDept {
  id: number;
  fullName: string;
  shortName: String;
  deptNumber: string;
  sortNumber: string;
  beginDate;
  endDate;
  children?: SystemDept[];
}
