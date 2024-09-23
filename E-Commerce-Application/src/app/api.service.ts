import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { register } from './model/register';
import { Observable } from 'rxjs';
import { OrderRequest } from './model/OrderRequest';
import { OrderResponse } from './model/OrderResponse';


@Injectable({
  providedIn: 'root'
})
export class ApiService {

  constructor(private http:HttpClient) { }

  register(register:register):Observable<any>
  {
    return this.http.post("http://localhost:8080/v3/register",register,{responseType:'text'});

  }
  login(userName:string,password:string):Observable<any>
  {console.log(userName);
    console.log(password)

    return this.http.post("http://localhost:8080/v3/login",{userName,password},{responseType:'text'})
  }
getProducts():Observable<any>
{
  return this.http.get("http://localhost:8080/v2/products");
}
addToCart(productId:number,quantity:number):Observable<any>{
  console.log("iam triggered");
  console.log(productId+""+quantity);
 
  let params = new HttpParams();
params = params.set('productId', productId);
params = params.set('quantity', quantity);
return this.http.post("http://localhost:8080/v4/add", null, {
  params: params,
  responseType: 'text' 
});
}
getCartItems():Observable<any>{
  return this.http.get("http://localhost:8080/v4/items");
}
placeOrder(orderRequest: OrderRequest): Observable<any> {


  return this.http.post("http://localhost:8080/v1/orders", orderRequest,{responseType:'text'});
}
getHistory():Observable<any>
{
  return this.http.get("http://localhost:8080/v1/orders");
}
clearCart(): Observable<any> {
  return this.http.delete("http://localhost:8080/v4/delete",{responseType:'text'});
}
getOrderDetails(id:number):Observable<OrderResponse>
{
  console.log(id);
  return this.http.get<OrderResponse>("http://localhost:8080/v1/orders/"+id);
}

}
