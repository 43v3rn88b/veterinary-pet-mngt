package com.codecraft.veterinary_pet_system.repository;

import com.codecraft.veterinary_pet_system.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    List<Invoice> findByOwnerId(Long ownerId);

    @Query("SELECT COALESCE(SUM(i.amount), 0) FROM Invoice i")
    BigDecimal sumTotalAmount();
}
