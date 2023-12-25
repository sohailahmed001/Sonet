import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';

@Component({
  selector: 'app-song-playing',
  templateUrl: './song-playing.component.html',
  styleUrls: ['./song-playing.component.scss']
})
export class SongPlayingComponent implements OnInit {
  @ViewChild('audioPlayer') audioPlayerRef: ElementRef;
  audioSamplePath: string = 'assets/sunshine-bliss.mp3' // For Testing
  controlActive: string;
  sliderValue: number = 10;

  constructor() {}

  ngOnInit(): void {
  }

  play() {
    this.audioPlayerRef.nativeElement.play();
    this.controlActive = 'play';
  }

  pause() {
    this.audioPlayerRef.nativeElement.pause();
    this.controlActive = 'pause';
  }

  stop() {
    this.audioPlayerRef.nativeElement.pause();
    this.audioPlayerRef.nativeElement.currentTime = 0;
    this.controlActive = 'stop';
  }
}
