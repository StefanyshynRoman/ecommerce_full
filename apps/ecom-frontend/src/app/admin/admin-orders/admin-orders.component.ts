import { Component, inject, PLATFORM_ID } from '@angular/core';
import { CommonModule, isPlatformBrowser } from '@angular/common';
import { FaIconComponent } from '@fortawesome/angular-fontawesome';
import { injectQuery } from '@tanstack/angular-query-experimental';
import { OrderedItems } from '../../shared/model/order.model';
import { lastValueFrom } from 'rxjs';
import { Pagination } from '../../shared/model/request.model';
import { OrderService } from '../../shared/service/order.service';

@Component({
  selector: 'ecom-admin-orders',
  standalone: true,
  imports: [CommonModule, FaIconComponent],
  templateUrl: './admin-orders.component.html',
  styleUrl: './admin-orders.component.scss',
})
export class AdminOrdersComponent {
  orderService = inject(OrderService);

  pageRequest: Pagination = {
    page: 0,
    size: 20,
    sort: [],
  };

  platformId = inject(PLATFORM_ID);

  ordersAdminQuery = injectQuery(() => ({
    queryKey: ['admin-orders', this.pageRequest],
    queryFn: () =>
      lastValueFrom(this.orderService.getOrdersForAdmin(this.pageRequest)),
  }));

  computeItemsName(items: OrderedItems[]) {
    return items.map((item) => item.name).join(', ');
  }

  computeItemsQuantity(items: OrderedItems[]) {
    return items.reduce((acc, item) => acc + item.quantity, 0);
  }

  computeTotal(items: OrderedItems[]) {
    return items.reduce((acc, item) => acc + item.price * item.quantity, 0);
  }

  checkIfPlatformBrowser() {
    return isPlatformBrowser(this.platformId);
  }
}
