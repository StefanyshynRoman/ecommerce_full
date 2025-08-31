package com.rstefanyshyn.ecom.order.domain.order.vo;


import com.rstefanyshyn.ecom.shared.error.domain.Assert;

public record OrderQuantity(long value) {

  public OrderQuantity {
    Assert.field("value", value).positive();

  }
}
