package com.codecraft.veterinary_pet_system.service;

import com.codecraft.veterinary_pet_system.entity.Appointment;
import org.springframework.stereotype.Service;
import com.codecraft.veterinary_pet_system.entity.Supply;
import com.codecraft.veterinary_pet_system.repository.SupplyRepository;
import java.util.List;
import java.util.Optional;

@Service
public class SupplyService {
    private final SupplyRepository supplyRepository;

    public SupplyService(SupplyRepository supplyRepository) {
        this.supplyRepository = supplyRepository;
    }
    public Optional<Supply> getSupplyById(Long id) {
        return supplyRepository.findById(id);
    }

    public Supply addSupply(Supply supply) {
        return supplyRepository.save(supply);
    }

    public List<Supply> getAllSupplies() {
        return supplyRepository.findAll();
    }

    public Supply updateSupply(Long id, Supply updated) {
        Supply s = supplyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Supply not found"));
        s.setName(updated.getName());
        s.setQuantity(updated.getQuantity());
        return supplyRepository.save(s);
    }

    public void deleteSupply(Long id) {
        supplyRepository.deleteById(id);
    }
}
