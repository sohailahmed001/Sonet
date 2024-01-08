import { Component, ElementRef, OnInit } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { ActivatedRoute, Router } from '@angular/router';
import { Album, Song } from 'src/app/model/common.model';
import { SonetService } from 'src/app/services/sonet.service';
import { UtilsService } from 'src/app/utils/utils.service';

@Component({
  selector: 'app-album-details',
  templateUrl: './album-details.component.html',
  styleUrls: ['./album-details.component.scss']
})
export class AlbumDetailsComponent implements OnInit {
  album: Album = new Album();
  songs: Song[] = [];

  constructor(
    private utilsService: UtilsService,
    private route: ActivatedRoute,
    private router: Router,
    private sonetService: SonetService) { }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      const id = params['id'];
      
      if(id > 0) {
        this.getPublishedAlbumById(id, this.navigateToHome.bind(this));
      }
      else {
        this.navigateToHome();
      }
    });
  }

  getPublishedAlbumById(id: number, errorFn?: any) {
    this.utilsService.getObjectByID('api/sonet/albums/published', id).subscribe({
      next: (data: Album) => {
        console.log('alb', data);
        this.album = data;
        this.postAlbumFetch();
      },
      error: (error) => {
        this.utilsService.handleError(error);
        if(errorFn) {
          errorFn();
        }
      }
    })
  }

  postAlbumFetch() {
    if(this.album.coverImageFile) {
      this.album.selectedImageURL = 'data:image/jpeg;base64,'+ this.album.coverImageFile;
    }
    this.songs = this.album.songs || [];

    (this.songs || []).forEach(song => {
      if(song.primaryImageFile) {
        song.selectedImageURL = 'data:image/jpeg;base64,'+ song.primaryImageFile;
      }
    })
  }

  onSongClick(song: Song) {
    this.sonetService.songPlayingSubject.next(song);
  }

  navigateToHome() {
    this.router.navigate(['/sonet', 'home']);
  }
}
