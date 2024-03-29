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
  userType: any;
}

enum Usertype {
  LISTENER = 'LISTENER',
  ARTIST = 'ARTIST'
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
  registerAsOptions: any[];

  constructor(public utilsService: UtilsService, private userService: UserService, private router: Router) { }
  
  ngOnInit(): void {
    this.utilsService.setMinMaxDatesForDob(this.minDate, this.maxDate);

    this.registerAsOptions = [
      { label: 'Listener', value: Usertype.LISTENER},
      { label: 'Artist', value: Usertype.ARTIST, }
    ];

    this.user.userType = Usertype.LISTENER;
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
