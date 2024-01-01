import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { AppUser } from '../model/app-user.model';
import { getCookie, setCookie } from 'typescript-cookie';
import { JwtHelperService } from "@auth0/angular-jwt";

@Injectable({
    providedIn: 'root'
})
export class AuthService {

    private jwtToken: string;
    baseURL = environment.baseURL;
    errorMessages: any[];
    PROJECT_PREFIX: string = environment.PROJECT_PREFIX;

    constructor(private http: HttpClient, private jwtService: JwtHelperService) { }

    login(userDetails: AppUser): Observable<any> {
        const httpHeaders = new HttpHeaders({
            'Authorization': 'Basic ' + window.btoa(userDetails.username + ':' + userDetails.password),
        });

        return this.http.get(this.baseURL + "api/login", { observe: 'response',withCredentials: true, headers: httpHeaders })
            .pipe(
                map((data: any) => {
                    return data;
                })
            );
    }

    logout(): void {
        this.jwtToken = null;
        sessionStorage.removeItem(this.PROJECT_PREFIX + 'Authorization');
        setCookie('XSRF-TOKEN', '');
    }

    getJWTToken(): string {
        if (!this.jwtToken) {
            this.jwtToken = sessionStorage.getItem(this.PROJECT_PREFIX + 'Authorization');
        }
        return this.jwtToken;
    }

    isAuthenticated(): boolean {
        const jwtToken = this.getJWTToken();
        return jwtToken && !this.jwtService.isTokenExpired(jwtToken);
    }

    hasAnyRole(roles: any[]): boolean {
        if(!(roles || []).length) {
            return true;
        }
        return (this.getUserRolesAndAuthorities() || []).some(userRole => roles.includes(userRole))
    }

    getUserRolesAndAuthorities(): any[] {
        const jwtToken = this.getJWTToken();
        const decodedToken = this.jwtService.decodeToken(jwtToken);
        const userRolesStr = (decodedToken.authorities || '');
        return userRolesStr.includes(',') ? userRolesStr.split(',') : [userRolesStr];
    }

    setJWTToken(jwt){
        sessionStorage.setItem(this.PROJECT_PREFIX + 'Authorization', jwt);
    }

    // add this method to all HTTP requests that require authentication
    addJWTTokenToHeader(headers: HttpHeaders): HttpHeaders {
        const jwtToken = this.getJWTToken();

        if (jwtToken) {
            return headers.append('Authorization', jwtToken);
        }
        return headers;
    }

    getXSRFToken(){
        return getCookie('XSRF-TOKEN');
    }
    
    processAuthData(data: any) {
        const jwtToken = data.headers.get('Authorization');
    
        if (jwtToken) {
            this.setJWTToken(jwtToken);
        }
    }
}
