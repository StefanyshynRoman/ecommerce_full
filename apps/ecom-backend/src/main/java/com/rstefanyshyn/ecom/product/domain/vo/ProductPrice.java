package com.rstefanyshyn.ecom.product.domain.vo;

import com.rstefanyshyn.ecom.shared.error.domain.Assert;

public record ProductPrice(double value) {

  public ProductPrice {
    Assert.field("value", value).min(0.1);
  }
}
