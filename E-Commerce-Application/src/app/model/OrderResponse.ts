import { OrderItemsResponse } from "./OrderItemsResponse";

export interface OrderResponse {
    id: number;
    totalPrice: number; 
    status: string;
    createAt: string; 
    items: OrderItemsResponse[];
  }