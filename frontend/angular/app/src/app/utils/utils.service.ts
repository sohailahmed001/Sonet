import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { MessageService } from 'primeng/api';
import { environment } from 'src/environments/environment';
import { AuthService } from '../auth/auth.service';
import { FormGroup, Validators } from '@angular/forms';

@Injectable({
  providedIn: 'root'
})
export class UtilsService {
  private apiURL = environment.baseURL;
  public loggedInUserObj: any;

  isDesktop = false;
  isMobile = false;
  errorMessages: any[];

  constructor(private httpClient: HttpClient,
    private msgsService: MessageService,
    private authService: AuthService) {
  }

  getObjects(serviceName: string, queryParams: any): Observable<any> {
    return this.httpClient.get(this.apiURL + serviceName, { params: queryParams, withCredentials: true });
  }

  getObjectByID(serviceName: string, id: any) {
    return this.httpClient.get(this.apiURL + serviceName + '/' + id, {withCredentials: true});
  }

  saveObjects(serviceName: string , createdObj : any){
    return this.httpClient.post(this.apiURL + serviceName, createdObj, {withCredentials: true});
  }

  deleteObjects(serviceName: string , deletedObjId : any){
    return this.httpClient.delete(this.apiURL + serviceName + '/' + deletedObjId, {withCredentials: true});
  }

  handleSuccessMessage(message: any = null) {
    let detailMessage = message?.length > 0 ? message : 'Details Saved Successfully';
    this.msgsService.add({ severity: 'success', detail: detailMessage });
  }

  clearErrorMessages() {
    this.errorMessages = [];
  }

  markFormGroupTouched(formGroup: FormGroup): void {
    Object.values(formGroup.controls).forEach(control => {
      control.markAsTouched();

      if (control instanceof FormGroup) {
        this.markFormGroupTouched(control);
      }
    });
  }

  handleError(error: any = null) {
    let errorMessage = 'Error Occured';

    if (error) {
      errorMessage = error.error?.message
    }

    this.errorMessages = [{ severity: 'error', summary: 'Error', detail: errorMessage }];
  }

  handleSuccess(message: string = null) {
    let msg = message || 'Details Saved Successfully';

    this.msgsService.add({ severity: 'success', summary: 'Success', detail: msg });
  }

  addNullOptions(data: any[]) {
    let nullOption = { value: null, description: 'Please Select' };
    data.unshift(nullOption);
    return data;
  }

  isSuccessfulResponse(data: any) {
    if (data.result == 'ok') {
      return true;
    }
    else {
      this.handleError();
      return false;
    }
  }

  getRequest(serviceName: any): Observable<any> {
    return this.httpClient.get(this.apiURL + serviceName);
  }

  addTokenToHeader(): HttpHeaders {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json'
    });

    const token = this.authService.getJWTToken();

    if (token) {
      return headers.set('Authorization', 'Bearer ' + token);
    }
    return headers;
  }

  // should be in assests always
  getDataFromJSON(fileName): Observable<any> {
    return this.httpClient.get<any>('assets/' + fileName);
  }
}
