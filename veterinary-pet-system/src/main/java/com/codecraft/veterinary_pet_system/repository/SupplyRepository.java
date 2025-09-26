package com.codecraft.veterinary_pet_system.repository;

import com.codecraft.veterinary_pet_system.entity.Supply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplyRepository extends JpaRepository<Supply, Long> {
}
