import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { MenuItem } from 'primeng/api';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';
import { Album, Song } from 'src/app/model/common.model';
import { UtilsService } from 'src/app/utils/utils.service';
import { ActivatedRoute, Router } from '@angular/router';
import { environment } from 'src/environments/environment';
import { FileProgressEvent, FileUploadEvent } from 'primeng/fileupload';
import { HttpResponse } from '@angular/common/http';

@Component({
  selector: 'app-create-album',
  templateUrl: './create-album.component.html',
  styleUrls: ['./create-album.component.scss']
})
export class CreateAlbumComponent implements OnInit {
  @ViewChild('detailsForm') detailsForm: NgForm;
  album: Album = new Album();
  createStepItems: MenuItem[];
  currentStep: number = 0;
  visitedSteps: number = 0;
  songs: Song[] = [];
  todayDate: Date = new Date();
  showLoader: boolean = false;
  baseUrl: string = environment.baseURL;

  constructor(
    private utilsService: UtilsService,
    private sanitizer: DomSanitizer,
    private route: ActivatedRoute,
    private router: Router) {}

  ngOnInit(): void {
    this.setStepItems();
    this.route.params.subscribe(params => {
      const id = params['id'];
      if(id == 0) {
        this.album = new Album();
      }
      else if(id > 0) {
        this.getUnpublishedAlbumById(id, this.navigateToMyAlbums.bind(this));
      }
    });
  }

  setStepItems() {
    this.createStepItems = [
      {
          label: 'Details',
          command: () => {
            this.currentStep = 0;
          },
          expanded: this.currentStep == 0,
      },
      {
          label: 'Add Songs',
          command: () => {
            if(this.visitedSteps >= 1) {
              this.currentStep = 1;
            }
          },
          expanded: this.currentStep == 1,
      },
      {
          label: 'Publish',
          command: () => {
            if(this.visitedSteps >= 2) {
              this.currentStep = 2;
            }
          },
          expanded: this.currentStep == 2,
      }
    ];
  }

  getUnpublishedAlbumById(id: number, errorFn?: any) {
    this.utilsService.getObjectByID('api/sonet/albums/unpublished', id).subscribe({
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
    }
    this.songs = this.album.songs || [];

    (this.songs || []).forEach(song => {
      if(song.primaryImageFile) {
        song.selectedImageURL = this.utilsService.base64ImageConvertPrefix + song.primaryImageFile;
      }

      if(song.audioFile) {
        song.uploadedFileName = song.name;
      }
    })
  }

  goToPreviousStep() {
    this.currentStep == 0 ? this.currentStep : this.currentStep--;
  }

  goToNextStep() {
    if(this.currentStep == 0 && this.detailsForm?.form?.invalid) {
      this.utilsService.markFormGroupTouched(this.detailsForm.form);
      return;
    }

    this.showLoader = true;
    this.utilsService.saveObjects('api/sonet/albums', this.prepareData(this.album)).subscribe({
      next: (data: Album) => {
        console.log('Save', data);
        this.showLoader = false;
        this.utilsService.handleSuccess();
        this.getUnpublishedAlbumById(data.id);
        if(this.currentStep < 2) {
          ++this.currentStep > this.visitedSteps && this.visitedSteps++;
        }
      },
      error: (error) => {
        this.showLoader = false;
        this.utilsService.handleError(error);
      }
    });

    // if(this.currentStep < 2) {
    //   ++this.currentStep > this.visitedSteps && this.visitedSteps++
    // }
  }

  prepareData(album: Album): Album {
    album.songs = this.songs;
    (this.songs || []).forEach(song => song.album = { id: album.id });
    return this.album;
  }

  onImageUpload(event: FileUploadEvent, entity: any, urlAttribName: string) {
    console.log(event);
    if(event.originalEvent) {
      entity[urlAttribName] = (event.originalEvent as HttpResponse<any>).body?.downloadUri
      if ((event.files || []).length) {
        const unsafeUrl = (event.files[0] as any).objectURL?.changingThisBreaksApplicationSecurity;
        entity.selectedImageURL = this.sanitizer.bypassSecurityTrustUrl(unsafeUrl);
      }
    }
  }

  onAudioUpload(event: FileUploadEvent, song: Song) {
    console.log('audioEv', event);
    if(event.originalEvent) {
      song.audioFileUrl = (event.originalEvent as HttpResponse<any>).body?.downloadUri;
      if ((event.files || []).length) {
        song.uploadedFileName = event.files[0]?.name;
      }
    }
  }

  onClearImageClick(photoUpload: any, entity: any, attributeName: string) {
    entity['selectedImageURL'] = null;
    entity[attributeName] = null;
    photoUpload.clear();
  }

  onClearAudio(songUpload: any, song: Song) {
    song.uploadedFileName = null;
    song.audioFileUrl = null;
    songUpload.clear();
  }

  onAddNewSongClick() {
    const newSong = new Song();
    this.utilsService.createNewObject(newSong, 'tempId');
    (this.songs || []).push(newSong);
  }

  onRemoveSongClick(song: Song) {
    this.utilsService.removeObject(song, this.songs, song.id ? 'id' : 'tempId');
  }

  onPublishClick() {
    this.showLoader = true;
    this.utilsService.saveObjects('api/sonet/albums/publish', this.prepareData(this.album)).subscribe({
      next: (data: Album) => {
        console.log('Publish', data);
        this.showLoader = false;
        this.utilsService.handleSuccessMessage('Album Published');
        this.navigateToMyAlbums();
      },
      error: (error) => {
        this.showLoader = false;
        this.utilsService.handleError(error);
      }
    });
  }

  onAudioUploadProgress(event: FileProgressEvent, song: Song) {
    console.log('UP', event);
    song.uploadedValue = event.progress;
  }

  navigateToMyAlbums() {
    this.router.navigate(['/sonet', 'my-albums']);
  }
}
