import { Component, OnInit } from '@angular/core';
import { MenuItem } from 'primeng/api';
import { Album, Song } from 'src/app/model/common.model';
import { UtilsService } from 'src/app/utils/utils.service';

@Component({
  selector: 'app-create-album',
  templateUrl: './create-album.component.html',
  styleUrls: ['./create-album.component.scss']
})
export class CreateAlbumComponent implements OnInit {
  album: Album = new Album();
  createStepItems: MenuItem[];
  currentStep: number = 0;
  visitedSteps: number = 0;
  songs: Song[] = [];
  todayDate: Date = new Date();

  constructor(private utilsService: UtilsService) {}

  ngOnInit(): void {
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

    this.getLocalDataJson();
  }

  goToNextStep() {
    if(this.currentStep < 2) {
      ++this.currentStep > this.visitedSteps && this.visitedSteps++
    }
  }

  goToPreviousStep() {
    this.currentStep == 0 ? this.currentStep : this.currentStep--;
  }

  getLocalDataJson() {
    this.utilsService.getDataFromJSON('sonet.data.json').subscribe({
      next: (sonetData) => {
        this.songs = sonetData['songs'];
        console.log('songs', this.songs)
      },
      error: (error) => {
        this.utilsService.handleError(error);
      }
    });
  }
}
