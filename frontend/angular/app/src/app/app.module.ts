import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { JWT_OPTIONS, JwtHelperService } from '@auth0/angular-jwt';
import { ConfirmationService, MessageService } from 'primeng/api';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { MessagesModule } from 'primeng/messages';
import { TableModule } from 'primeng/table';
import { ToolbarModule } from 'primeng/toolbar';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AuthModule } from './auth/auth.module';
import { EditRoleComponent } from './components/edit-role/edit-role.component';
import { EditUserComponent } from './components/edit-user/edit-user.component';
import { HomeComponent } from './components/home/home.component';
import { ManageAuthoritiesComponent } from './components/manage-authorities/manage-authorities.component';
import { SearchRoleComponent } from './components/search-role/search-role.component';
import { SearchUserComponent } from './components/search-user/search-user.component';
import { XhrInterceptor } from './interceptors/app.request.interceptor';
import { AuthGuardService } from './routeguards/auth.routeguard';
import { FullLayoutComponentModule } from './utils/full-layout-component/full-layout-component.module';
import { MultiSelectModule } from 'primeng/multiselect';
import { DialogModule } from 'primeng/dialog';
import { ToastModule } from 'primeng/toast';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { SonetLayoutComponent } from './components/sonet-layout/sonet-layout/sonet-layout.component';
import { SonetHomeComponent } from './components/sonet/sonet-home/sonet-home.component';
import { PanelMenuModule } from 'primeng/panelmenu';
import { SonetHeaderComponent } from './components/sonet-layout/sonet-header/sonet-header.component';
import { SongPlayingComponent } from './components/sonet-layout/song-playing/song-playing.component';
import { SliderModule } from 'primeng/slider';
import { AlbumDetailsComponent } from './components/sonet/album-details/album-details.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    SearchUserComponent,
    EditUserComponent,
    SearchRoleComponent,
    ManageAuthoritiesComponent,
    EditRoleComponent,
    SonetLayoutComponent,
    SonetHomeComponent,
    SonetHeaderComponent,
    SongPlayingComponent,
    AlbumDetailsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    AuthModule,
    ConfirmDialogModule,
    TableModule,
    MessagesModule,
    ToolbarModule,
    FullLayoutComponentModule,
    HttpClientModule,
    MultiSelectModule,
    DialogModule,
    ToastModule,
    PanelMenuModule,
    SliderModule
  ],
  providers: [
    {
      provide : HTTP_INTERCEPTORS,
      useClass : XhrInterceptor,
      multi : true
    },
    {
      provide: JWT_OPTIONS,
      useValue: {} // Provide your JWT_OPTIONS configuration here if needed
    },
    JwtHelperService,
    AuthGuardService,
    MessageService,
    ConfirmationService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
