import { Component } from '@angular/core';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent {

}

export interface User {
  firstName: string;
  lastName: string;
  email: string;
  username: string;

}
