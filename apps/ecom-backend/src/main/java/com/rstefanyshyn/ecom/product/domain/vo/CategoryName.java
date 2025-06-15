package com.rstefanyshyn.ecom.product.domain.vo;

import com.rstefanyshyn.ecom.shared.error.domain.Assert;

public record CategoryName(String value) {
  public CategoryName {
    Assert.field("value", value).notNull().minLength(3);
  }
}
