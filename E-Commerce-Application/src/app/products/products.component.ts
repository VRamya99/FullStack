import { Component, OnInit } from '@angular/core';
import { ApiService } from '../api.service';
import { Product } from '../model/Product';
import { ActivatedRoute, Router } from '@angular/router';
import { faShoppingCart } from '@fortawesome/free-solid-svg-icons';
import { faHistory } from '@fortawesome/free-solid-svg-icons';
@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css']
})
export class ProductsComponent implements OnInit {
  products: Product[] = [];
  filteredProducts: Product[] = [];
  searchTerm: string = '';
  quantities: { [key: number]: number } = {}; 
  message: string = "";
  added: boolean = false;
  cart = faShoppingCart;
  history=faHistory
  cartItems: Product[] = [];
  cartItemCount: number = 0;

  constructor( private service: ApiService,private route: ActivatedRoute,private router: Router) {
    
  }

  ngOnInit() {
    this.loadCartItems();
  }

  
  loadCartItems() {
    this.service.getCartItems().subscribe(
      (cartRes: Product[]) => {
        this.cartItems = cartRes;
        this.cartItemCount = this.cartItems.length;
        this.getProducts();
      },
      (err) => {
        console.error('Error fetching cart items:', err);
       
        this.getProducts();
      }
    );
  }

 
  getProducts() {
    this.service.getProducts().subscribe(
      (res: Product[]) => {
        console.log('API Response:', res);
        if (Array.isArray(res)) {
          this.products = res; 
        
          this.products.forEach(product => this.quantities[product.id] = 1); 

        
          const cartProductIds = this.cartItems.map(item => item.id);
          this.filteredProducts = this.products.filter(product => !cartProductIds.includes(product.id));
        } else {
          console.error('Expected an array but got:', res);
        }
      },
      (err) => {
        console.error('Error fetching products:', err);
      }
    );
  }

  
  onSearch(): void {
    const lowerSearch = this.searchTerm.toLowerCase();
    this.filteredProducts = this.products
      .filter(product => !this.cartItems.map(item => item.id).includes(product.id)) 
      .filter(product =>
        product.name.toLowerCase().includes(lowerSearch)
      );
  }

 
  addToCart(productId: number, quantity: number) {
    console.log(`Adding Product ID: ${productId}, Quantity: ${quantity}`);
    if (quantity > 0) {
      this.service.addToCart(productId, quantity).subscribe(
        (res) => {
          console.log('Add to Cart Response:', res);
          this.added = true;
          this.message = `Added ${quantity} of "${this.getProductName(productId)}" to the cart!`;

          const addedProduct = this.products.find(p => p.id === productId);
          if (addedProduct) {
            this.cartItems.push(addedProduct);
            this.cartItemCount++;
          }
          this.filteredProducts = this.filteredProducts.filter(product => product.id !== productId);

          
          this.quantities[productId] = 1;

         
          if (this.searchTerm.trim() !== '') {
            this.onSearch();
          }

        
          setTimeout(() => {
            this.added = false;
            this.message = '';
          }, 3000);
        },
        (err) => {
          console.log('Error adding to cart:', err);
        
        }
      );
    } else {
      console.warn('Quantity must be greater than zero.');
      
    }
  }

  goToCart(){
    this.router.navigateByUrl("/cart");
  }

 
  getProductName(productId: number): string {
    const product = this.products.find(p => p.id === productId);
    return product ? product.name : 'Unknown Product';
  }
  viewOrderHistory()
  {

    this.router.navigateByUrl("/history");
  }
}
