package com.tiki.order.repository;

import com.tiki.order.entity.OrderDetail;
import com.tiki.order.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    @Query("""
            select sum(o.quantity)
            from OrderDetail o
            join o.order
            where o.productVariantId = :productId and o.order.status = :status
            group by o.productVariantId
            """)
    Long getSoldNumByProduct(@Param("productId") Long productId, @Param("status") OrderStatus status);

    @Query("""
            SELECT od.productVariantId
            FROM OrderDetail od
            GROUP BY od.productVariantId 
            ORDER BY SUM(od.quantity) DESC
            """)
    List<Long> findTopProductsByQuantity();

}
