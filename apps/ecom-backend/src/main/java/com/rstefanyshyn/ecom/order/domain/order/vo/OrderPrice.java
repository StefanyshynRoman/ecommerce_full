package com.rstefanyshyn.ecom.order.domain.order.vo;

import com.rstefanyshyn.ecom.shared.error.domain.Assert;

public record OrderPrice(double value) {

  public OrderPrice {
    Assert.field("value", value).strictlyPositive();
  }
}
