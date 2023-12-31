import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { MessageService } from 'primeng/api';
import { LoginComponent } from './login/login.component';
import { RegistrationComponent } from './registration/registration.component';
import { LayoutComponent } from './layout/layout.component';
import { ProgressSpinnerModule } from 'primeng/progressspinner';
import { PasswordModule } from 'primeng/password';
import { ButtonModule } from 'primeng/button';
import { CardModule } from 'primeng/card';
import { ToastModule } from 'primeng/toast';
import { InputTextModule } from 'primeng/inputtext';
import { MessagesModule } from 'primeng/messages';
import { CalendarModule } from 'primeng/calendar';


@NgModule({
  declarations: [
    LoginComponent,
    RegistrationComponent,
    LayoutComponent
    ],
  imports: [
    FormsModule,
    BrowserModule,
    HttpClientModule,
    BrowserAnimationsModule,
    ProgressSpinnerModule,
    PasswordModule,
    ButtonModule,
    CardModule,
    ToastModule,
    RouterModule,
    InputTextModule,
    MessagesModule,
    CalendarModule
  ],
  exports:[
    FormsModule,
    ProgressSpinnerModule,
    CardModule,
    ButtonModule,
    InputTextModule
  ],
  providers: [MessageService],
  bootstrap: [LayoutComponent]

})
export class AuthModule { }
