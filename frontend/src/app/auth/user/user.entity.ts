export class User {
  name: string = "";
  token: string = "";
  principal: string = "";
  details: UserDetail = new UserDetail();
  authorities?: Array<String> = Array();

  constructor() {
  }
}

export class UserDetail {
  tellerNumber: string;
  deptName: String;
  positionName: String;
  autoLoginIp: String;
  name: String;
  menus?: Array<String>;
}
