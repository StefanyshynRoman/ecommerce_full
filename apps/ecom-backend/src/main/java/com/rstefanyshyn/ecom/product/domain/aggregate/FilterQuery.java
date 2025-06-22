package com.rstefanyshyn.ecom.product.domain.aggregate;

import com.rstefanyshyn.ecom.product.domain.vo.ProductSize;
import com.rstefanyshyn.ecom.product.domain.vo.PublicId;
import org.jilt.Builder;

import java.util.List;

@Builder
public record FilterQuery(PublicId categoryId, List<ProductSize> sizes) {
}
