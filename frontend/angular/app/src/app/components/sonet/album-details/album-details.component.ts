import { Component, OnInit } from '@angular/core';
import { UtilsService } from 'src/app/utils/utils.service';

@Component({
  selector: 'app-album-details',
  templateUrl: './album-details.component.html',
  styleUrls: ['./album-details.component.scss']
})
export class AlbumDetailsComponent implements OnInit {

  songs: any[];

  constructor(private utilsService: UtilsService) { }

  ngOnInit(): void {
    this.utilsService.getDataFromJSON('sonet.data.json').subscribe({
      next: (sonetData) => {
        this.songs = sonetData['songs'];
      },
      error: (error) => {
        this.utilsService.handleError(error);
      }
    });
  }
}
