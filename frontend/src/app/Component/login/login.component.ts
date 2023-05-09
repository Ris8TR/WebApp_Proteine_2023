import {Component, OnInit} from '@angular/core';
import {User} from "../user/user.component";
import {NgForm} from "@angular/forms";



@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})

export class LoginComponent implements OnInit{

  isLoggedIn: Boolean = false;
  private email: any;

  Login(email: User, password: User): void {
    // @ts-ignore
    /*
    const {Client} = pg;
    const client = new Client({
      user: 'postgres',
      host: 'localhost',
      database: 'mydb',
      password: 'root',
      port: 5432,
    });
*/
    this.isLoggedIn=true;


  }

  ngOnInit(): void {
  }




  onSubmit(form: NgForm) {
    const email = form.value.email
    const password = form.value.email
    this.Login(email, password);
  }


  logout(): void {
    this.isLoggedIn = false;
  }

  getEmail() {
    return this.email;
  }

  getLogIn() {
    return this.isLoggedIn;
  }


}
