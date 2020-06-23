export class SystemDept {
  id: number
  fullName: string
  shortName: string
  deptNumber: string
  sortNumber: string
  beginDate: Date
  endDate: Date
  parent?: SystemDept
  children?: SystemDept[]

}

export function  systemDeptDisplayForAutoComplete(systemDept?: SystemDept) {
  return systemDept && systemDept.shortName ? systemDept.shortName : ''
}
