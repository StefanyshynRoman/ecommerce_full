package com.rstefanyshyn.ecom.order.domain.user.vo;

import com.rstefanyshyn.ecom.shared.error.domain.Assert;

public record AuthorityName (String name){
  public AuthorityName {
    Assert.field("name", name).notNull();
  }
}
