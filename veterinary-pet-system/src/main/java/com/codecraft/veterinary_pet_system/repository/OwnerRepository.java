package com.codecraft.veterinary_pet_system.repository;

import com.codecraft.veterinary_pet_system.entity.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {
}

