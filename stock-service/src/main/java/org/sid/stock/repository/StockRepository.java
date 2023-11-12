package org.sid.stock.repository;

import org.sid.stock.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock,Long> {

    @Query("select e from Stock e where e.productId =: productId")
    Optional<Stock> findByProductId(@Param("productId ") Long productId);
}
