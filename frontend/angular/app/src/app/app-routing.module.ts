import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { authRoutes } from './auth/auth.routing';
import { HomeComponent } from './components/home/home.component';
import { LayoutComponent } from './auth/layout/layout.component';
import { FullLayoutComponent } from './utils/full-layout-component/full-layout/full-layout.component';
import { SearchUserComponent } from './components/search-user/search-user.component';
import { EditUserComponent } from './components/edit-user/edit-user.component';
import { ManageAuthoritiesComponent } from './components/manage-authorities/manage-authorities.component';
import { AuthGuard } from './routeguards/auth.routeguard';
import { SearchRoleComponent } from './components/search-role/search-role.component';
import { EditRoleComponent } from './components/edit-role/edit-role.component';
import { SonetLayoutComponent } from './components/sonet-layout/sonet-layout/sonet-layout.component';
import { SonetHomeComponent } from './components/sonet/sonet-home/sonet-home.component';
import { AlbumDetailsComponent } from './components/sonet/album-details/album-details.component';
import { MyPlaylistsComponent } from './components/sonet/my-playlists/my-playlists.component';
import { MyProfileComponent } from './components/sonet/my-profile/my-profile.component';
import { MyAlbumsComponent } from './components/sonet/my-albums/my-albums.component';
import { CreateAlbumComponent } from './components/sonet/create-album/create-album.component';

const routes: Routes = [
  {
    path : '',
    redirectTo: 'login',
    pathMatch: 'full',
  },
  {
    path : '',
    component : FullLayoutComponent,
    canActivate: [AuthGuard],
    data: { roles: ['ROLE_ADMIN'] },
    children : [
      {
        path: 'home',
        component: HomeComponent,
      },
      {
        path: 'search-user',
        component: SearchUserComponent,
      },
      {
        path: 'edit-user/:id',
        component: EditUserComponent,
      },
      {
        path: 'manage-authority',
        component: ManageAuthoritiesComponent,
      },
      {
        path: 'search-role',
        component: SearchRoleComponent,
      },
      {
        path: 'edit-role/:id',
        component: EditRoleComponent,
      },
      
    ]
  },
  {
    path: 'sonet',
    component: SonetLayoutComponent,
    children: [
      {
        path: 'home',
        component: SonetHomeComponent,
        canActivate: [AuthGuard],
        data: { roles: ['ROLE_LISTENER', 'ROLE_ARTIST'] }
      },
      {
        path: 'album-details/:id',
        component: AlbumDetailsComponent,
        canActivate: [AuthGuard],
        data: { roles: ['ROLE_LISTENER', 'ROLE_ARTIST'] }
      },
      {
        path: 'my-playlists',
        component: MyPlaylistsComponent,
        canActivate: [AuthGuard],
        data: { roles: ['ROLE_LISTENER', 'ROLE_ARTIST'] }
      },
      {
        path: 'my-profile',
        component: MyProfileComponent,
        canActivate: [AuthGuard],
        data: { roles: ['ROLE_LISTENER', 'ROLE_ARTIST'] }
      },
      {
        path: 'my-albums',
        component: MyAlbumsComponent,
        canActivate: [AuthGuard],
        data: { roles: ['ROLE_ARTIST'] }
      },
      {
        path: 'create-album/:id',
        component: CreateAlbumComponent,
        canActivate: [AuthGuard],
        data: { roles: ['ROLE_ARTIST'] }
      },
    ]
  },
  {
    path: '',
    component: LayoutComponent,
    children: [...authRoutes]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
