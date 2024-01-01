import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/auth/auth.service';

@Component({
  selector: 'app-sonet-header',
  templateUrl: './sonet-header.component.html',
  styleUrls: ['./sonet-header.component.scss']
})
export class SonetHeaderComponent {

  constructor(private authService: AuthService, public router: Router) {}
  
  logout() {
    this.authService.logout();
    this.router.navigate(['login']);
  }
}
