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
  audioDuration = 0;
  currentTrackTime = 0;
  currentVolume = 0.4;
  isSeeking = false;
  isSeekingVolume = false;

  constructor() {}

  ngOnInit(): void {
  }

  setInitialValues() {
    this.audioDuration = this.audioPlayerRef.nativeElement.duration;
    this.audioPlayerRef.nativeElement.volume = this.currentVolume; 
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
    this.currentTrackTime = 0;
    this.controlActive = 'stop';
  }

  updateSlider() {
    if (!this.isSeeking) {
      this.currentTrackTime = this.audioPlayerRef.nativeElement.currentTime;
    }
  }

  seekAudio(event: any) {
    console.log('Seek', event)
    this.isSeeking = true;
    const targetTime = event.value;
    this.audioPlayerRef.nativeElement.currentTime = targetTime;
    this.currentTrackTime = targetTime;
  }

  onSeekEnd(event: any) {
    console.log('End', event)
    this.isSeeking = false;
  }

  changeVolume(event: any) {
    this.isSeekingVolume = true;
    const targetVolume = parseFloat(event.value);
    this.audioPlayerRef.nativeElement.volume = targetVolume;
    this.currentVolume = targetVolume;
  }

  onVolumeSeekEnd(event: any) {
    this.isSeekingVolume = false;
  }
}
