import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../auth.service';
import { UtilsService } from 'src/app/utils/utils.service';
import { AppUser } from 'src/app/model/app-user.model';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})

export class LoginComponent {
  username: string;
  password: string;
  showLoader = false;
  PROJECT_PREFIX: string = environment.PROJECT_PREFIX;

  constructor(private router: Router,
    private authService: AuthService, 
    public utilsService: UtilsService) { }

  onLoginClick() {
    this.utilsService.clearErrorMessages();

    this.showLoader = true;

    const user = new AppUser();
    user.username = this.username;
    user.password = this.password;

    this.authService.login(user).subscribe(
      {
        next: (data) => {
          this.utilsService.handleSuccessMessage("Login Successfull");
          this.authService.processAuthData(data);

          setTimeout(() => {
            this.redirectBasedOnRole()
          }, 200);

          this.showLoader = false;
        },
        error: (er) => {
          this.utilsService.handleError(er);
          console.log(er);
          
          this.showLoader = false;
        }
      }
    )
  }

  redirectBasedOnRole() {
    const userRolesAndAuths = this.authService.getUserRolesAndAuthorities() || [];

    if (userRolesAndAuths.includes('ROLE_ADMIN')) {
      this.router.navigate(['/home']);
    } else if (userRolesAndAuths.includes('canListenSongs')) {
      this.router.navigate(['/sonet', 'home']);
    } else {
      this.router.navigate(['/unauthorized']);
    }
  }
}
