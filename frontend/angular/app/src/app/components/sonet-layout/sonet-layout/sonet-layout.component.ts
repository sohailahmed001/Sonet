import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MenuItem } from 'primeng/api';
import { UtilsService } from 'src/app/utils/utils.service';

@Component({
  selector: 'app-sonet-layout',
  templateUrl: './sonet-layout.component.html',
  styleUrls: ['./sonet-layout.component.scss']
})
export class SonetLayoutComponent implements OnInit {
  sideMenuItems: MenuItem[] = [];

  constructor(
    public utilsService: UtilsService,
    private router: Router,
    private activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.sideMenuItems = [
      {
        icon: 'fa fa-home',
        routerLink: '/sonet/home',
        expanded: this.checkMenuActive('/sonet/home')
      },
      {
        icon: 'fa fa-coffee',
        routerLink: '/sonet/dummy',
        expanded: this.checkMenuActive('/sonet/dummy')
      },
      {
        icon: 'fa fa-leaf',
        routerLink: '/sonet/dummy',
        expanded: this.checkMenuActive('/sonet/dummy')
      }
    ];
  }

  checkMenuActive(route: string): boolean {
    return this.router.url == route;
  }
}
