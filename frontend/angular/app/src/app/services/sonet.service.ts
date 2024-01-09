import { HttpClient } from "@angular/common/http";
import { environment } from "src/environments/environment";
import { UtilsService } from "../utils/utils.service";
import { Observable, Subject } from "rxjs";
import { Injectable } from "@angular/core";
import { Song } from "../model/common.model";

@Injectable({
    providedIn: 'root'
  })

export class SonetService {
    baseURL = environment.baseURL;
    PROJECT_PREFIX: string = environment.PROJECT_PREFIX;
    songPlayingSubject = new Subject<Song>();
    albumBackgroundSubject = new Subject<string>();

    constructor(private http: HttpClient, private utilsService: UtilsService) { }

    getUserByUsername(username: string): Observable<any> {
        return this.utilsService.getObjects('api/sonet/sonet-users', { username: username });
    }
}