import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MenuItem } from 'primeng/api';
import { AuthService } from 'src/app/auth/auth.service';
import { SonetService } from 'src/app/services/sonet.service';
import { UtilsService } from 'src/app/utils/utils.service';

@Component({
  selector: 'app-sonet-layout',
  templateUrl: './sonet-layout.component.html',
  styleUrls: ['./sonet-layout.component.scss']
})
export class SonetLayoutComponent implements OnInit {
  sideMenuItems: MenuItem[] = [];
  backgroundStyle: any;
  tooltipOptions:any = {
    showDelay: 250,
    hideDelay: 100
  };

  constructor(
    public utilsService: UtilsService, 
    public router: Router,
    private cdr: ChangeDetectorRef,
    private authService: AuthService,
    private sonetService: SonetService) {}

  ngOnInit(): void {
    this.resetBackground(null);
    this.setMenuItems();
    this.sonetService.albumBackgroundSubject.subscribe(imageUrl => this.resetBackground(imageUrl));
  }

  resetBackground(imagePath: string) {
    this.backgroundStyle = this.getBackgroundStyle(imagePath);
    this.cdr.detectChanges();
  }

  getBackgroundStyle(imagePath: string): string {
    const path: string = imagePath ? imagePath : null;
    if(path) {
      const gradient = 'linear-gradient(180deg, rgba(29, 33, 35, 0.70) 0%, #1D2123 61.48%)';
      const imageUrl = `url(${path})`;
      return `${gradient}, ${imageUrl} center / cover no-repeat`;
    }

    return null;
  }

  setMenuItems() {
    this.sideMenuItems = [
      {
        icon: 'fa fa-home',
        tooltip: 'Home',
        tooltipOptions: this.tooltipOptions,
        routerLink: '/sonet/home'
      },
      {
        icon: 'fas fa-headphones-alt',
        tooltip: 'My Playlist',
        tooltipOptions: this.tooltipOptions,
        routerLink: '/sonet/my-playlists'
      },
      {
        icon: '	fas fa-record-vinyl',
        tooltip: 'My Albums',
        tooltipOptions: this.tooltipOptions,
        routerLink: '/sonet/my-albums',
        visible: this.authService.hasAnyRole(['ROLE_ARTIST'])
      }
    ];
  }
}
