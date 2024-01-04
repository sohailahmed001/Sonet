import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UtilsService } from 'src/app/utils/utils.service';

@Component({
  selector: 'app-my-albums',
  templateUrl: './my-albums.component.html',
  styleUrls: ['./my-albums.component.scss']
})
export class MyAlbumsComponent implements OnInit {
  albums: any[];

  constructor(private utilsService: UtilsService, public router: Router) { }

  ngOnInit(): void {
    this.utilsService.getDataFromJSON('sonet.data.json').subscribe({
      next: (sonetData) => {
        this.albums = sonetData['albums'];        
      },
      error: (error) => {
        this.utilsService.handleError(error);
      }
    });
  }

}
