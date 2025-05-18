package com.rstefanyshyn.ecom.order.domain.user.vo;

import com.rstefanyshyn.ecom.shared.error.domain.Assert;

public record UserLastname(String value) {

  public UserLastname {
    Assert.field("value", value).maxLength(255);
  }
}
