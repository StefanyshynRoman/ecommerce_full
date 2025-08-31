package com.rstefanyshyn.ecom.order.domain.order.vo;

import com.rstefanyshyn.ecom.shared.error.domain.Assert;

public record StripeSessionId(String value) {

  public StripeSessionId {
    Assert.notNull("value", value);
  }
}
