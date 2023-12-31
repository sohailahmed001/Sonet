import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Role } from 'src/app/model/common.model';
import { UtilsService } from 'src/app/utils/utils.service';

@Component({
  selector: 'app-edit-role',
  templateUrl: './edit-role.component.html',
  styleUrls: ['./edit-role.component.scss']
})
export class EditRoleComponent implements OnInit {

  role: Role = new Role();
  authorityOptions: any[] = [];
  saveBtnItems: any[] = this.getSaveButtonItems();
  showLoader: boolean = false;

  constructor(private router: Router,
    private utilsService: UtilsService,
    private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.showLoader = true;

    this.route.params.subscribe(
      (params) => {
        if(params['id'] && params['id'] !== '0'){
          this.getRoleByID(params['id']);
        }
        else{
          this.showLoader = false;
        }
      }
    );

    this.getAuthorities();
  }

  getRoleByID(id: number) {
    this.utilsService.getObjectByID('api/roles',id).subscribe({
        next : (data: any) => {
          this.role = data;
          this.postRoleByIDSuccess(data);
          this.showLoader = false;
        },
        error : (error) => {
          console.log(error);
          this.utilsService.handleError(error);
          this.showLoader = false;
        }
      }
    )
  }

  getAuthorities() {
    this.utilsService.getObjects('api/authorities', {}).subscribe({
        next : (data: any) => {
          this.authorityOptions = data;
        },
        error : (error) => {
          this.utilsService.handleError(error);
        }
      }
    )
  }

  postRoleByIDSuccess(data: Object) {
  }

  getSaveButtonItems() {
    return [{
      label: 'Save & Back',
      icon: 'pi pi-sync'
    }]
  }

  onCancelClick() {
    this.router.navigate(['search-role']);
  }

  onSaveClick() {
    this.showLoader = true; 
    this.utilsService.saveObjects('api/roles', this.role).subscribe({
      next : (data: any) => {
        this.utilsService.handleSuccessMessage('Role Saved');
        this.getRoleByID(data.id);
        this.showLoader = false;
      },
      error : (error) => {
        this.utilsService.handleError(error);
        this.showLoader = false;
      }
    }
  )
  }
}
