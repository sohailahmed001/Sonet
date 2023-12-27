import { Component, OnInit } from '@angular/core';
import { UtilsService } from 'src/app/utils/utils.service';

@Component({
  selector: 'app-my-playlists',
  templateUrl: './my-playlists.component.html',
  styleUrls: ['./my-playlists.component.scss']
})
export class MyPlaylistsComponent implements OnInit {
  likedAlbum: any;
  albums: any[];

  constructor(private utilsService: UtilsService) { }

  ngOnInit(): void {
    this.utilsService.getDataFromJSON('sonet.data.json').subscribe({
      next: (sonetData) => {
        this.albums = sonetData['albums'];
        this.likedAlbum = sonetData['likedAlbum'];
        console.log(this.likedAlbum);
        
      },
      error: (error) => {
        this.utilsService.handleError(error);
      }
    });
  }
}
