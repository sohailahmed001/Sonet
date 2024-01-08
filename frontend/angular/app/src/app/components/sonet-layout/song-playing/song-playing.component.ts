import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { Song } from 'src/app/model/common.model';
import { SonetService } from 'src/app/services/sonet.service';

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
  playOnLoad:boolean = false;

  constructor(private sonetService: SonetService) {}

  ngOnInit(): void {
    this.sonetService.songPlayingSubject.subscribe((song: Song) => {
      if(song) {
        this.stop();
        this.setInitialValues();
        this.convertAudio(song);
        this.playOnLoad = true;
      }
    })
  }

  setInitialValues() {
    this.audioDuration = this.audioPlayerRef.nativeElement.duration;
    this.audioPlayerRef.nativeElement.volume = this.currentVolume; 
  }

  convertAudio(song: Song) {
    if(!song.tempAudioSrc && song.audioFile) {
      const binaryAudioData = atob(song.audioFile);
      const bytes = new Uint8Array(binaryAudioData.length);

      for (let i = 0; i < binaryAudioData.length; i++) {
        bytes[i] = binaryAudioData.charCodeAt(i);
      }

      const blob = new Blob([bytes], { type: 'audio/mpeg' });
      const audio = new Audio(URL.createObjectURL(blob)) as HTMLAudioElement;
      song.tempAudioSrc = audio.src;
      this.audioSamplePath = song.tempAudioSrc;
    }
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
    this.isSeeking = true;
    const targetTime = event.value;
    this.audioPlayerRef.nativeElement.currentTime = targetTime;
    this.currentTrackTime = targetTime;
  }

  onSeekEnd(event: any) {
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

  onCanPlay(event: any) {
    if(this.playOnLoad) {
      this.playOnLoad = false;
      this.play();
    }
  }
}
