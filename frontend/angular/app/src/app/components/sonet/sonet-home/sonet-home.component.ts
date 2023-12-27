import { Component, OnInit } from '@angular/core';
import { UtilsService } from 'src/app/utils/utils.service';

@Component({
  selector: 'app-sonet-home',
  templateUrl: './sonet-home.component.html',
  styleUrls: ['./sonet-home.component.scss']
})
export class SonetHomeComponent implements OnInit {

  songs: any[];
  albums: any[];

  constructor(private utilsService: UtilsService) { }

  ngOnInit(): void {
    this.utilsService.getDataFromJSON('sonet.data.json').subscribe({
      next: (sonetData) => {
        this.songs = sonetData['songs'];
        this.albums = sonetData['albums'];
      },
      error: (error) => {
        this.utilsService.handleError(error);
      }
    });
  }
}
