import { Product } from "./Product";

export interface OrderItemsResponse {
    id: number;
    price: number;
    quantity: number;
    product: Product;
  }