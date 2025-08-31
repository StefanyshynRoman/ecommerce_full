package com.rstefanyshyn.ecom.order.domain.order.service;

import com.rstefanyshyn.ecom.order.domain.order.aggregate.DetailCartResponse;
import com.rstefanyshyn.ecom.order.domain.order.aggregate.DetailCartResponseBuilder;
import com.rstefanyshyn.ecom.product.domain.aggregate.Product;
import com.rstefanyshyn.ecom.product.domain.aggregate.ProductCart;

import java.util.List;

public class CartReader {

  public CartReader() {
  }

  public DetailCartResponse getDetails(List<Product> products) {
    List<ProductCart> cartProducts = products.stream().map(ProductCart::from).toList();
    return DetailCartResponseBuilder.detailCartResponse().products(cartProducts)
      .build();
  }
}
