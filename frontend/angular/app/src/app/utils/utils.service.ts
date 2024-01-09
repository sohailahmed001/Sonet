import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { MessageService } from 'primeng/api';
import { environment } from 'src/environments/environment';
import { AuthService } from '../auth/auth.service';
import { FormGroup, Validators } from '@angular/forms';
import { Song } from '../model/common.model';

@Injectable({
  providedIn: 'root'
})
export class UtilsService {
  private apiURL = environment.baseURL;
  public loggedInUserObj: any;

  isDesktop = false;
  isMobile = false;
  errorMessages: any[];
  uniqueObjIdCounter: number = 0;
  base64ImageConvertPrefix: string = 'data:image/jpeg;base64,';

  constructor(private httpClient: HttpClient,
    private msgsService: MessageService,
    private authService: AuthService) {
  }

  getObjects(serviceName: string, queryParams: any): Observable<any> {
    return this.httpClient.get(this.apiURL + serviceName, { params: queryParams, withCredentials: true });
  }

  getObjectByID(serviceName: string, id: any): Observable<any> {
    return this.httpClient.get(this.apiURL + serviceName + '/' + id, {withCredentials: true});
  }

  saveObjects(serviceName: string , createdObj : any): Observable<any> {
    return this.httpClient.post(this.apiURL + serviceName, createdObj, {withCredentials: true});
  }

  deleteObjects(serviceName: string , deletedObjId : any): Observable<any> {
    return this.httpClient.delete(this.apiURL + serviceName + '/' + deletedObjId, {withCredentials: true});
  }

  postByPathVariable(serviceName: string, id: any, body: any = {}): Observable<any> {
    return this.httpClient.post(this.apiURL + serviceName + '/' + id, body, {withCredentials: true});
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

  setMinMaxDatesForDob(minDate: Date, maxDate: Date) {
    minDate = new Date();
    maxDate = new Date();

    minDate.setFullYear(new Date().getFullYear() - 120);
    maxDate.setFullYear(new Date().getFullYear() - 16);
  }

  createNewObject(obj: any, uniqueFieldName: string) {
    if(!obj.id) {
      obj[uniqueFieldName] = ++this.uniqueObjIdCounter;
    }
  }

  removeObject(objectToRemove: any, objects: any[], idAttribName: string) {
    const index = (objects || []).findIndex(obj => objectToRemove[idAttribName] == obj[idAttribName]);
    if(index >= 0) {
      objects.splice(index, 1);
    }
  }
}
