package com.rstefanyshyn.ecom.product.domain.service;

import com.rstefanyshyn.ecom.product.domain.repository.ProductRepository;

public class ProductUpdater {
  private final ProductRepository productRepository;

  public ProductUpdater(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

//  public void updateProductQuantity(List<OrderProductQuantity> orderProductQuantities) {
//    for (OrderProductQuantity orderProductQuantity : orderProductQuantities) {
//      productRepository.updateQuantity(orderProductQuantity.productPublicId(),
//        orderProductQuantity.quantity().value());
//    }
//  }
}
