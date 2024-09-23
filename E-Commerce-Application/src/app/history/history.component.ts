// src/app/history/history.component.ts

import { Component, OnInit } from '@angular/core';
import { ApiService } from '../api.service';
import { History } from '../model/History';
import { Router } from '@angular/router';

@Component({
  selector: 'app-history',
  templateUrl: './history.component.html',
  styleUrls: ['./history.component.css']
})
export class HistoryComponent implements OnInit {
  orderItems: History[] = [];
  isLoading: boolean = true;
  errorMessage: string = '';

  constructor(private service: ApiService,private router:Router) {}

  ngOnInit(): void {
    this.getHistory();
  }

  formatDate(dateStr: string): string {
    const date = new Date(dateStr);
    return date.toLocaleString(); 
  }
  getHistory(): void {
    this.service.getHistory().subscribe(
      (res) => {
        console.log(res);
        this.orderItems = res;
        this.isLoading = false;
      },
      (err) => {
        console.error(err);
        this.errorMessage = 'Failed to load order history. Please try again later.';
        this.isLoading = false;
      }
    );
  }
  viewDetailedOrder(id:number){
 
    this.router.navigate(['/order-detail', id]);
    

  }
  goToProducts()
  {
    this.router.navigateByUrl("/products")
  }
}
