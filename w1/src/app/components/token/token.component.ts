import { Component} from '@angular/core';
import { TokenService } from '../../services/token-service.service';

@Component({
  selector: 'app-token',
  templateUrl: './token.component.html',
  styleUrls: ['./token.component.css']
})
export class TokenComponent {

  token : string;

  constructor(private tokenService: TokenService){
    this.token = ''
  }
  
  setToken(){
    this.tokenService.setToken(this.token);
  }

  getToken(){
    return this.tokenService.getToken()
  }

}
