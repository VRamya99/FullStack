import { Component, OnInit } from '@angular/core';
import { ApiService } from '../api.service';
import { CartItems } from '../model/CartItems';
import { Route, Router } from '@angular/router';
import { OrderRequest } from '../model/OrderRequest';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrl: './cart.component.css'
})
export class CartComponent implements OnInit {
  cart:CartItems[]=[];
  totalCartItemsPrice=0;
  productIds=[];
  quantity=[];
  orderError=""
  orderPlaced=false;
 
  isdisabled=false;
  ngOnInit(){

  }
  constructor(private service:ApiService,private router:Router)
  {
this.gtCartItems();
  }
  gtCartItems()
  {
    this.service.getCartItems().subscribe((res)=>{
console.log(res);
this.cart=res;

if(res.length===0)
{
  this.isdisabled=true;
}
this.cart.forEach((cart)=>{
  this.totalCartItemsPrice=this.totalCartItemsPrice+cart.totalPrice;
})


    },
    (err)=>{
      console.log(err);
        })
  }

  goBack(){
this.router.navigateByUrl("/products")
  }
  order()
  {
    if (this.cart.length === 0) {
      this.orderError = 'Your cart is empty.';
      return;
    }

    const orderItems = this.cart.map(item => ({
      productId: item.id,
      quantity: item.quantity
    }));

    const orderRequest: OrderRequest = {
     
      orderItems: orderItems
    };

    this.service.placeOrder(orderRequest).subscribe(
      (response) => {
        console.log('Order placed successfully:', response);
        this.orderPlaced = true;
        this.orderError = '';
       
        this.clearCart();
       
       
          this.router.navigateByUrl("/order");
        
      },
      (error) => {
        console.error('Error placing order:', error);
        this.orderError = 'There was an issue placing your order. Please try again.';
      }
    );

  }
   clearCart() {
    this.service.clearCart().subscribe(
      () => {
        this.cart = [];
        this.totalCartItemsPrice = 0;
      },
      (error) => {
        console.error('Error clearing cart:', error);
      }
    );
    }

}
