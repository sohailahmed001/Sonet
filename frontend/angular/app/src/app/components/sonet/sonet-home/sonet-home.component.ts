import { Component, OnInit } from '@angular/core';
import { Album } from 'src/app/model/common.model';
import { UtilsService } from 'src/app/utils/utils.service';

@Component({
  selector: 'app-sonet-home',
  templateUrl: './sonet-home.component.html',
  styleUrls: ['./sonet-home.component.scss']
})
export class SonetHomeComponent implements OnInit {
  albums: Album[];
  mostLikedAlbums: Album[];
  responsiveOptions: any[];
  carouselImages: any[] = [];

  constructor(private utilsService: UtilsService) { }

  ngOnInit(): void {
    this.setCarouselImages()
    this.getMostLikedAlbums();
    this.getLatestPublishedAlbums();
  }

  getLatestPublishedAlbums() {
    this.utilsService.getObjects('api/sonet/albums/latest', {}).subscribe({
      next: (albums: any[]) => {
        this.albums = albums;
        this.postAlbumFetch(this.albums);
      },
      error: (error) => {
        this.utilsService.handleError(error);
      }
    });
  }

  getMostLikedAlbums() {
    this.utilsService.getObjects('api/sonet/albums/mostLiked', {}).subscribe({
      next: (albums: any[]) => {
        this.mostLikedAlbums = albums;
        this.postAlbumFetch(this.mostLikedAlbums);
      },
      error: (error) => {
        this.utilsService.handleError(error);
      }
    });
  }

  postAlbumFetch(albums: any[]) {
    (albums || []).forEach(album => {
      if(album.coverImageFile) {
        album.selectedImageURL = this.utilsService.base64ImageConvertPrefix + album.coverImageFile;
      }
    });
  }

  onLikeAlbumClick(album: Album) {
    this.utilsService.postByPathVariable('api/sonet/albums/toggleLike', album.id).subscribe({
      next: (data: any) => {
        console.log('onLike', data);
        album.liked = !album.liked;
      },
      error: (error) => {
        this.utilsService.handleError(error);
      }
    })
  }

  setCarouselImages() {
    this.carouselImages = [
      {
        name: 'Image1',
        src: 'assets/images/carousel1.jpg'
      },
      {
        name: 'Image2',
        src: 'assets/images/carousel2.jpg'
      }
    ]
    this.responsiveOptions = [
      {
          breakpoint: '1199px',
          numVisible: 1,
          numScroll: 1
      },
      {
          breakpoint: '991px',
          numVisible: 2,
          numScroll: 1
      },
      {
          breakpoint: '767px',
          numVisible: 1,
          numScroll: 1
      }
    ];
  }
}
