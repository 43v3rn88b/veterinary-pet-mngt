package com.codecraft.veterinary_pet_system.controller;

import com.codecraft.veterinary_pet_system.dto.SupplyDTO;
import com.codecraft.veterinary_pet_system.mapper.SupplyMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.codecraft.veterinary_pet_system.entity.Supply;
import com.codecraft.veterinary_pet_system.service.SupplyService;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/inventory")

public class SupplyController {

    private final SupplyService supplyService;

    public SupplyController(SupplyService supplyService) {
        this.supplyService = supplyService;
    }

    @PostMapping
    public ResponseEntity<SupplyDTO> addSupply(@RequestBody SupplyDTO dto) {
        Supply supply = SupplyMapper.toEntity(dto);
        Supply saved = supplyService.addSupply(supply);
        return ResponseEntity.ok(SupplyMapper.toDTO(saved));
    }

    @GetMapping
    public List<SupplyDTO> getAllSupplies() {
        return supplyService.getAllSupplies().stream()
                .map(SupplyMapper::toDTO)
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    public ResponseEntity<SupplyDTO> updateSupply(
            @PathVariable Long id,
            @RequestBody SupplyDTO dto) {

        Supply supply = SupplyMapper.toEntity(dto);
        supply.setId(id);

        Supply updated = supplyService.updateSupply(id, supply);
        return ResponseEntity.ok(SupplyMapper.toDTO(updated));
    }
    @GetMapping("/{id}")
    public ResponseEntity<SupplyDTO> getSupplyById(@PathVariable Long id) {
        return supplyService.getSupplyById(id)
                .map(SupplyMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSupply(@PathVariable Long id) {
        supplyService.deleteSupply(id);
        return ResponseEntity.noContent().build();
    }
}
