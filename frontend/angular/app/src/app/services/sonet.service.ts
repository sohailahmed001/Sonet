import { HttpClient } from "@angular/common/http";
import { environment } from "src/environments/environment";
import { UtilsService } from "../utils/utils.service";
import { Observable } from "rxjs";
import { Injectable } from "@angular/core";

@Injectable({
    providedIn: 'root'
  })

export class SonetService {
    baseURL = environment.baseURL;
    PROJECT_PREFIX: string = environment.PROJECT_PREFIX;

    constructor(private http: HttpClient, private utilsService: UtilsService) { }

    getUserByUsername(username: string): Observable<any> {
        return this.utilsService.getObjects('api/sonet/sonet-users', { username: username });
    }
}