import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";



@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit{
  loggedIn: boolean;

  constructor( private router: Router) {
    this.loggedIn = this.isLoggedIn();
    if (!this.loggedIn) {
      this.router.navigate(['/login']);
    }
  }

  ngOnInit(): void {
  }

  isLoggedIn() {
    return false;
  }
}

export interface User {
  firstName: string;
  lastName: string;
  email: string;
  username: string;

}


