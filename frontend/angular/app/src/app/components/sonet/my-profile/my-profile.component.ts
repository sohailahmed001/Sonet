import { Component, OnInit, ViewChild } from '@angular/core';
import { AuthService } from 'src/app/auth/auth.service';
import { SonetUser } from 'src/app/model/common.model';
import { SonetService } from 'src/app/services/sonet.service';
import { UtilsService } from 'src/app/utils/utils.service';
import { NgForm } from '@angular/forms';

class CredDto {
  username: string;
  currentPassword: string;
  newPassword: string;
}

@Component({
  selector: 'app-my-profile',
  templateUrl: './my-profile.component.html',
  styleUrls: ['./my-profile.component.scss']
})
export class MyProfileComponent implements OnInit {
  @ViewChild('detailsForm') detailsForm: NgForm;
  @ViewChild('credentialsForm') credentialsForm: NgForm;
  username: string;
  user: SonetUser = new SonetUser();
  credDto: CredDto = new CredDto()
  minDate: Date;
  maxDate: Date;
  changePassword: boolean = false;
  showLoader: boolean = false;
  showChangeCredDialog: boolean = false;

  constructor(
    private sonetService: SonetService,
    public utilsService: UtilsService,
    private authService: AuthService) { }

  ngOnInit(): void {
    this.utilsService.setMinMaxDatesForDob(this.minDate, this.maxDate);
    this.getUserByUsername();
  }

  getUserByUsername() {
    this.username = this.authService.getUsername();

    this.showLoader = true;
    this.sonetService.getUserByUsername(this.username).subscribe({
      next: (data: any) => {
        this.showLoader = false;
        this.user = data;
      },
      error: (error) => {
        this.showLoader = false;
        this.utilsService.handleError(error);
      }
    });
  }

  onChangeCredClick() {
    this.credDto = new CredDto()
    this.credDto.username = this.username;
    this.showChangeCredDialog = true;
  }

  changeCredentials() {
    this.utilsService.clearErrorMessages();

    if (this.credentialsForm.form.invalid) {
      this.utilsService.markFormGroupTouched(this.credentialsForm.form);
      return;
    }

    if(!this.changePassword) {
      this.credDto.newPassword = null;
    }

    this.showLoader = true;

    this.utilsService.saveObjects("api/change-credentials", this.credDto).subscribe(
      {
        next: (data) => {
          this.showLoader = false;
          this.utilsService.handleSuccessMessage("Credentials Changed");
          this.dialogClose();
          this.authService.logoutAndRedirectLogin();
        },
        error: (er) => {
          this.showLoader = false;
          this.dialogClose();
          this.utilsService.handleError(er);
        }
      }
    )
  }

  dialogClose() {
    this.showChangeCredDialog = false;
  }

  onSaveClick() {
    this.utilsService.clearErrorMessages();

    if (this.detailsForm.form.invalid) {
      this.utilsService.markFormGroupTouched(this.detailsForm.form);
      return;
    }

    this.showLoader = true;

    this.utilsService.saveObjects("api/sonet/sonet-users", this.user).subscribe(
      {
        next: (data) => {
          this.showLoader = false;
          this.utilsService.handleSuccessMessage("Updated Details");
          this.getUserByUsername();
        },
        error: (er) => {
          this.showLoader = false;
          this.utilsService.handleError(er);
        }
      }
    )
  }
}
