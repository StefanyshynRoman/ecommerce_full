package com.rstefanyshyn.ecom.order.domain.order.aggregate;

import com.rstefanyshyn.ecom.product.domain.vo.PublicId;
import org.jilt.Builder;

@Builder
public record DetailCartItemRequest(PublicId productId, long quantity) {
}
