export class SystemDept {
  id: number;
  fullName: string;
  shortName: String;
  deptNumber: string;
  sortNumber: string;
  children?: SystemDept[];
}
