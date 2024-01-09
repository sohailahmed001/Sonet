import { Component, ElementRef, OnDestroy, OnInit } from '@angular/core';
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
export class AlbumDetailsComponent implements OnInit, OnDestroy {
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

  ngOnDestroy(): void {
    this.sonetService.albumBackgroundSubject.next(null);
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
      this.album.selectedImageURL = this.utilsService.base64ImageConvertPrefix + this.album.coverImageFile;
      this.sonetService.albumBackgroundSubject.next(this.album.selectedImageURL);
    }
    this.songs = this.album.songs || [];

    (this.songs || []).forEach(song => {
      if(song.primaryImageFile) {
        song.selectedImageURL = this.utilsService.base64ImageConvertPrefix + song.primaryImageFile;
      }
    });
  }

  onSongClick(song: Song) {
    this.sonetService.songPlayingSubject.next(song);
  }

  isSongPlaying(song: Song): boolean {
    return song.id == this.sonetService.songPlaying?.id;
  }

  onLikeAlbumClick() {
    this.utilsService.postByPathVariable('api/sonet/albums/toggleLike', this.album.id).subscribe({
      next: (data: any) => {
        console.log('onLike', data);
        this.album.liked = !this.album.liked;
      },
      error: (error) => {
        this.utilsService.handleError(error);
      }
    })
  }

  navigateToHome() {
    this.router.navigate(['/sonet', 'home']);
  }
}
