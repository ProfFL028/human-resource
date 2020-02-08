import {SystemDept} from "../system-dept/system-dept.entity";
import {SystemPosition} from "../system-position/system-position.entity";
import {SystemRole} from "../system-role/system-role.entity";

export class SystemUser {
  tellerNumber: String;
  name: String;
  autoLoginIp?: String;
  telephone: String;
  dept: SystemDept;
  position: SystemPosition;
  roles: SystemRole[];
}
