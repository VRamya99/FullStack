import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-order',
  templateUrl: './order.component.html',
  styleUrl: './order.component.css'
})
export class OrderComponent implements OnInit{
 
 
  constructor(private router:Router)
  {
    
  }

  ngOnInit(){
   
    setTimeout(() => {
      this.router.navigateByUrl("/products")
    }, 10000);
  }

}
