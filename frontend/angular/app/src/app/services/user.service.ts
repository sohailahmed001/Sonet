import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, map } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  baseURL = environment.baseURL;
  PROJECT_PREFIX: string = environment.PROJECT_PREFIX;

  constructor(private http: HttpClient) { }

    getAllAuthorities(): Observable<any> {
        return this.http.get(this.baseURL + "api/authorities", { observe: 'response' })
            .pipe(
                map((data: any) => {
                    console.log(data);
                    return data;
                })
            );
    }
}