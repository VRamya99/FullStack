import { Component, OnInit } from '@angular/core';
import { OrderResponse } from '../model/OrderResponse';
import { ActivatedRoute, Router } from '@angular/router';
import { ApiService } from '../api.service';

@Component({
  selector: 'app-order-items',
  templateUrl: './order-items.component.html',
  styleUrl: './order-items.component.css'
})


export class OrderItemsComponent implements OnInit {
  orderId!: number;
  orderDetails!: OrderResponse;
  isLoading: boolean = true;
  errorMessage: string = '';

  constructor(private route: ActivatedRoute,private apiService: ApiService,private router: Router) {
    
   }

  ngOnInit(): void {
 
    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam) {
      this.orderId = Number(idParam);
      this.fetchOrderDetails();
    } else {
      this.errorMessage = 'Invalid order ID.';
      this.isLoading = false;
    }
  }

 
  fetchOrderDetails(): void {
    this.apiService.getOrderDetails(this.orderId).subscribe(
      (res: OrderResponse) => {
        console.log(res);
        this.orderDetails = res;
        this.isLoading = false;
      },
      (err) => {
        console.error(err);
        this.errorMessage = 'Failed to load order details. Please try again later.';
        this.isLoading = false;
      }
    );
  }

 
  formatDate(dateStr: string): string {
    const date = new Date(dateStr);
    return date.toLocaleString(); 
  }

  
  goBack(): void {
    this.router.navigateByUrl("/history");
  }
}
