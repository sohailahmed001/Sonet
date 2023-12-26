import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { ConfirmationService } from 'primeng/api';
import { Table } from 'primeng/table';
import { UtilsService } from 'src/app/utils/utils.service';

@Component({
  selector: 'app-search-role',
  templateUrl: './search-role.component.html',
  styleUrls: ['./search-role.component.scss']
})
export class SearchRoleComponent implements OnInit {

  roles: any[];
  selectedRoles: any[];
  @ViewChild('dt') postTable: Table;

  constructor(private router: Router,
    private confirmationService: ConfirmationService,
    private utilsService: UtilsService) {

  }

  ngOnInit(): void {
    this.getRoles();
  }

  onAddClick() {
    this.router.navigate(['/edit-role', 0]);
  }

  getFilterValue(event: Event): void {
    let targetValue = event.target as HTMLInputElement;
    this.postTable.filterGlobal(targetValue.value, 'contains');
  }

  editRole(rowData) {
      this.router.navigate(['edit-role', rowData.id]);
  }

  deleteRole(rowData) {
    this.confirmationService.confirm({
      message: 'Are you sure you want to delete the selected role?',
      header: 'Confirm',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.utilsService.deleteObjects('api/roles', rowData.id).subscribe({
          next: (data: any) => {
            this.utilsService.handleSuccessMessage('Role deleted successfully');
            this.getRoles();
          },
          error: (error: any) => {
            this.utilsService.handleError(error);
          }
        })
      }
    });
  }

  getRoles() {
    this.utilsService.getObjects('api/roles', {}).subscribe({
        next: (data) => {
            console.log('roles', data);
            this.roles = data;
            this.postRoleSuccess(this.roles);
        },
        error: (error) => {
          this.utilsService.handleError(error);
        }
      }
    )
  }

  private postRoleSuccess(data) {
    console.log(data);

    this.roles?.forEach((role) => {

      let authorities: any[] = role.authorities?.map(val => val.name);
      role['authoritiesStr'] = authorities ? authorities.join(", ") : null;

    });
  }
}
