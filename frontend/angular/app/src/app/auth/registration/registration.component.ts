import { Component, OnInit, ViewChild } from '@angular/core';
import { FormGroup, NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from 'src/app/services/user.service';
import { UtilsService } from 'src/app/utils/utils.service';

class RegistrationUser {
  username: string;
  password: string;
  firstName: string;
  middleName: string;
  lastName: string;
  photo: any;
  dob: Date;
  gender: any;
}

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.scss'],
})

export class RegistrationComponent implements OnInit {
  @ViewChild('myform') form: NgForm;
  user: RegistrationUser = new RegistrationUser();
  minDate: Date;
  maxDate: Date;
  showLoader = false;

  constructor(public utilsService: UtilsService, private userService: UserService, private router: Router) { }
  
  ngOnInit(): void {
    this.minDate = new Date();
    this.maxDate = new Date();

    this.minDate.setFullYear(new Date().getFullYear() - 120);
    this.maxDate.setFullYear(new Date().getFullYear() - 16)
  }

  onSignInClick() {
    this.utilsService.clearErrorMessages();

    if (this.form.form.invalid) {
      this.utilsService.markFormGroupTouched(this.form.form);
      return;
    }

    this.showLoader = true;

    this.utilsService.saveObjects("api/register", this.user).subscribe(
      {
        next: (data) => {
          this.showLoader = false;
          this.utilsService.handleSuccessMessage("User Registered Successfully");
          this.router.navigate(['login']);
        },
        error: (er) => {
          this.showLoader = false;
          this.utilsService.handleError(er);
        }
      }
    )
  }
}
