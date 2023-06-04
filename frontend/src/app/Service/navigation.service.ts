import { Injectable } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class NavigationService {



  constructor(private router: Router,
              private route: ActivatedRoute) {}

  private previousComponent: {
  route: string;
} | null = null;

setPreviousComponent(route: string ) {
  this.previousComponent = { route };
}


getPreviousComponent() {
  return this.previousComponent;
}


  goBack(): void {
    const previousComponent = this.getPreviousComponent();
    if (previousComponent) {
      this.router.navigateByUrl(previousComponent.route);
    } else {
      this.router.navigate(['/home']);
    }
  }
}
