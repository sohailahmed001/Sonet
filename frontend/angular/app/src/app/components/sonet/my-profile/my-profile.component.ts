import { Component, OnInit, ViewChild } from '@angular/core';
import { AuthService } from 'src/app/auth/auth.service';
import { SonetUser } from 'src/app/model/common.model';
import { SonetService } from 'src/app/services/sonet.service';
import { UtilsService } from 'src/app/utils/utils.service';
import { SonetLayoutComponent } from '../../sonet-layout/sonet-layout/sonet-layout.component';
import { NgForm } from '@angular/forms';

class PasswordDto {
  currentPassword: string;
  newPassword: string;
}

@Component({
  selector: 'app-my-profile',
  templateUrl: './my-profile.component.html',
  styleUrls: ['./my-profile.component.scss']
})
export class MyProfileComponent implements OnInit {
  @ViewChild('passform') passform: NgForm;
  user: SonetUser = new SonetUser();
  passwordDto: PasswordDto = new PasswordDto()
  minDate: Date;
  maxDate: Date;
  showLoader: boolean = false;
  showChangePasswordDialog: boolean = false;

  constructor(
    private sonetService: SonetService,
    public utilsService: UtilsService,
    private authService: AuthService,
    private sonetLayoutComponent: SonetLayoutComponent) { }

  ngOnInit(): void {
    this.utilsService.setMinMaxDatesForDob(this.minDate, this.maxDate);
    this.getUserByUsername();
  }

  getUserByUsername() {
    const username = this.authService.getUsername();

    this.showLoader = true;
    this.sonetService.getUserByUsername(username).subscribe({
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

  onChangePasswordClick() {
    this.passwordDto = new PasswordDto()
    this.showChangePasswordDialog = true;
  }

  changePassword() {
    this.utilsService.clearErrorMessages();

    if (this.passform.form.invalid) {
      this.utilsService.markFormGroupTouched(this.passform.form);
      return;
    }

    this.showLoader = true;

    this.utilsService.saveObjects("api/sonet/change-password", this.passwordDto).subscribe(
      {
        next: (data) => {
          this.showLoader = false;
          this.utilsService.handleSuccessMessage("Password Changed");
          this.dialogClose();
          this.getUserByUsername();
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
    this.showChangePasswordDialog = false;
  }
}
