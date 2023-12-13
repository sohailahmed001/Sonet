import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AppUser } from 'src/app/model/app-user.model';
import { UserService } from 'src/app/services/user.service';
import { UtilsService } from 'src/app/utils/utils.service';

@Component({
  selector: 'app-edit-user',
  templateUrl: './edit-user.component.html',
  styleUrls: ['./edit-user.component.scss']
})
export class EditUserComponent implements OnInit {
  user:AppUser = new AppUser();

  constructor(private route: ActivatedRoute, private userService: UserService, private utilsService: UtilsService) { }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      const id = params['id'];
      if(id == 0) {
        this.user = new AppUser();
      }
      else if(id > 0) {
        this.getUserById(id);
      }
    });
  }

  getUserById(id: number) {
    this.userService.getUserById(id).subscribe({
      next: (data: any) => {
        this.user = data;
      },
      error: (error) => {
        this.utilsService.handleError(error);
      }
    });
  }

  onSaveClick(event: any) {
    this.userService.saveUser(this.user).subscribe({
      next: (data: any) => {
        console.log('User', data);
        this.getUserById(data.id);
      },
      error: (error) => {
        this.utilsService.handleError(error);
      }
    })
  }
}
