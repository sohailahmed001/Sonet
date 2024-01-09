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
  mostLikedAlbums: any[];
  responsiveOptions: any[];
  carouselImages: any[] = [];

  constructor(private utilsService: UtilsService) { }

  ngOnInit(): void {
    this.setCarouselImages()
    this.getDataFromJSON();
    this.getLatestPublishedAlbums();
  }

  getLatestPublishedAlbums() {
    this.utilsService.getObjects('api/sonet/albums/latest', {}).subscribe({
      next: (albums: any[]) => {
        this.albums = albums;
        this.postAlbumFetch();
      },
      error: (error) => {
        this.utilsService.handleError(error);
      }
    });
  }

  postAlbumFetch() {
    (this.albums || []).forEach(album => {
      if(album.coverImageFile) {
        album.selectedImageURL = this.utilsService.base64ImageConvertPrefix + album.coverImageFile;
      }
    });
  }

  getDataFromJSON() {
    this.utilsService.getDataFromJSON('sonet.data.json').subscribe({
      next: (sonetData) => {
        this.albums = sonetData['albums'];
        this.mostLikedAlbums = (sonetData['albums'] || []).slice(0, 3);
      },
      error: (error) => {
        this.utilsService.handleError(error);
      }
    });
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
