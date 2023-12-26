import { AfterViewInit, Component, ElementRef, OnInit, ViewChild } from '@angular/core';

@Component({
  selector: 'app-song-playing',
  templateUrl: './song-playing.component.html',
  styleUrls: ['./song-playing.component.scss']
})
export class SongPlayingComponent implements OnInit, AfterViewInit {
  @ViewChild('audioPlayer') audioPlayerRef: ElementRef;
  audioSamplePath: string = 'assets/sunshine-bliss.mp3' // For Testing
  controlActive: string;
  volumeValue: number = 20;
  audioDuration = 0;
  currentTrackTime = 0;
  isSeeking = false;

  constructor() {}

  ngOnInit(): void {
  }

  ngAfterViewInit() {
    this.audioPlayerRef.nativeElement.addEventListener('loadedmetadata', () => {
      this.audioDuration = this.audioPlayerRef.nativeElement.duration;
    });
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
}
