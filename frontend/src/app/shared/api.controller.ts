export class ApiController {

  public static readonly BACKEND_URL = "http://localhost:8080/";
  public static readonly API_BASE = "api/v1";

  public static readonly AUTH_URL = ApiController.BACKEND_URL + ApiController.API_BASE + "/auth";
  public static readonly USER_API_URL = ApiController.BACKEND_URL + ApiController.API_BASE + "/user";
  public static readonly ROLE_API_URL = ApiController.BACKEND_URL + ApiController.API_BASE + "/role";
  public static readonly POSITION_API_URL = ApiController.BACKEND_URL + ApiController.API_BASE + "/position";


}
