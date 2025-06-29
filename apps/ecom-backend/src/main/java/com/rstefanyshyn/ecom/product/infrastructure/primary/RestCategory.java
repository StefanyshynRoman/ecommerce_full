package com.rstefanyshyn.ecom.product.infrastructure.primary;

import com.rstefanyshyn.ecom.product.domain.aggregate.Category;
import com.rstefanyshyn.ecom.product.domain.aggregate.CategoryBuilder;
import com.rstefanyshyn.ecom.product.domain.vo.CategoryName;
import com.rstefanyshyn.ecom.product.domain.vo.PublicId;
import com.rstefanyshyn.ecom.shared.error.domain.Assert;
import org.jilt.Builder;

import java.util.UUID;

import static com.rstefanyshyn.ecom.shared.error.domain.Assert.*;

@Builder
public record RestCategory(UUID publicId,
                           String name) {

  public RestCategory {
    notNull("name", name);
  }

  public static Category toDomain(RestCategory restCategory) {
    CategoryBuilder categoryBuilder = CategoryBuilder.category();

    if(restCategory.name != null) {
      categoryBuilder.name(new CategoryName(restCategory.name));
    }

    if(restCategory.publicId != null) {
      categoryBuilder.publicId(new PublicId(restCategory.publicId));
    }

    return categoryBuilder.build();
  }

  public static RestCategory fromDomain(Category category) {
    RestCategoryBuilder restCategoryBuilder = RestCategoryBuilder.restCategory();

    if(category.getName() != null) {
      restCategoryBuilder.name(category.getName().value());
    }

    return restCategoryBuilder
      .publicId(category.getPublicId().value())
      .build();
  }
}
