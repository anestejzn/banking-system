import { User } from "src/modules/shared/model/user";


export interface LoginResponse {
  token: string;
  user: User;
}
