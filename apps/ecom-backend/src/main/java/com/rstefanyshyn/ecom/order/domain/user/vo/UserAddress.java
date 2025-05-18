package com.rstefanyshyn.ecom.order.domain.user.vo;

import com.rstefanyshyn.ecom.shared.error.domain.Assert;
import org.jilt.Builder;

@Builder
public record UserAddress(String street, String city, String zipCode, String country) {
  public UserAddress{
    Assert.field("street", street).notNull();
    Assert.field("city", city).notNull();
    Assert.field("zipcode", zipCode).notNull();
    Assert.field("country", country).notNull();
  }
}
