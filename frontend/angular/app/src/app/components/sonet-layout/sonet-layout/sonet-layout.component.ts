import { Component, OnInit } from '@angular/core';
import { MenuItem } from 'primeng/api';
import { UtilsService } from 'src/app/utils/utils.service';

@Component({
  selector: 'app-sonet-layout',
  templateUrl: './sonet-layout.component.html',
  styleUrls: ['./sonet-layout.component.scss']
})
export class SonetLayoutComponent implements OnInit {
  sideMenuItems: MenuItem[] = [];
  backgroundImage: string = 'assets/images/album2.jpg';

  constructor(
    public utilsService: UtilsService) {}

  ngOnInit(): void {
    this.sideMenuItems = [
      {
        icon: 'fa fa-home',
        routerLink: '/sonet/home',
      },
      {
        icon: 'fa fa-coffee',
        routerLink: '/sonet/my-playlists',
      },
      {
        icon: 'fa fa-leaf',
        routerLink: '/sonet/dummy',
      }
    ];
  }

  getBackgroundStyle(): string {
    if(this.backgroundImage) {
      const gradient = 'linear-gradient(180deg, rgba(29, 33, 35, 0.70) 0%, #1D2123 61.48%)';
      const imageUrl = `url(${this.backgroundImage})`;
      return `${gradient}, ${imageUrl} center / cover no-repeat`;
    }
    return null;
  }
}
