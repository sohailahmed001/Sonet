import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Album } from 'src/app/model/common.model';
import { UtilsService } from 'src/app/utils/utils.service';

@Component({
  selector: 'app-my-albums',
  templateUrl: './my-albums.component.html',
  styleUrls: ['./my-albums.component.scss']
})
export class MyAlbumsComponent implements OnInit {
  publishedAlbums: Album[] = [];
  unpublishedAlbums: Album[] = [];

  constructor(private utilsService: UtilsService, public router: Router) { }

  ngOnInit(): void {
    this.getAlbums();
  }

  getAlbums() {
    this.utilsService.getObjects('api/sonet/albums/artist', {}).subscribe({
      next: (data) => {
        console.log('Albums', data);
        this.publishedAlbums = (data || []).filter(album => album.published);
        this.unpublishedAlbums = (data || []).filter(album => !album.published);
        this.postAlbumsFetch();
      },
      error: (error) => {
        this.utilsService.handleError(error);
      }
    })
  }

  postAlbumsFetch() {
    (this.publishedAlbums || []).forEach(album => {
      if(album.coverImageFile) {
        album.selectedImageURL = this.utilsService.base64ImageConvertPrefix + album.coverImageFile;
      }
    })
  }

  onAlbumSelect(album: Album) {
    if(album.published) {
      this.router.navigate(['/sonet', 'album-details', album.id]);
    }
    else {
    this.router.navigate(['/sonet', 'create-album', album.id]);
    }
  }

}
