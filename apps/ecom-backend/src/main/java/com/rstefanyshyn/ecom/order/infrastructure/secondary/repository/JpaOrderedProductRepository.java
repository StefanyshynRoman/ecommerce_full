package com.rstefanyshyn.ecom.order.infrastructure.secondary.repository;

import com.rstefanyshyn.ecom.order.infrastructure.secondary.entity.OrderedProductEntity;
import com.rstefanyshyn.ecom.order.infrastructure.secondary.entity.OrderedProductEntityPk;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaOrderedProductRepository extends JpaRepository<OrderedProductEntity, OrderedProductEntityPk> {

}
