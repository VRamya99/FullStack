import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RegisterComponent } from './register/register.component';
import { LoginComponent } from './login/login.component';
import { ProductsComponent } from './products/products.component';
import { CartComponent } from './cart/cart.component';
import { HistoryComponent } from './history/history.component';
import { OrderComponent } from './order/order.component';
import { OrderItemsComponent } from './order-items/order-items.component';

const routes: Routes = [
  {path:'register',component:RegisterComponent},
  {path:'',redirectTo:'register',pathMatch:'full'},
  {path:'login',component:LoginComponent},
  {path:'products',component:ProductsComponent},
  {
    path:'cart',component:CartComponent
  },
  {path:'history',component:HistoryComponent},
  {path:'order',component:OrderComponent},{
    path: 'order-detail/:id', component: OrderItemsComponent },
  
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
