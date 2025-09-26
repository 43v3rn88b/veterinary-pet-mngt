package com.codecraft.veterinary_pet_system.controller;

import com.codecraft.veterinary_pet_system.dto.OwnerDTO;
import com.codecraft.veterinary_pet_system.mapper.OwnerMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.codecraft.veterinary_pet_system.entity.Owner;
import com.codecraft.veterinary_pet_system.service.OwnerService;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/owners")
public class OwnerController {

    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }


//    @PostMapping
//    public ResponseEntity<Owner> addOwner(@RequestBody Owner owner) {
//        return ResponseEntity.ok(ownerService.addOwner(owner));
//    }

//    @GetMapping
//    public ResponseEntity<List<Owner>> getAllOwners() {
//        return ResponseEntity.ok(ownerService.getAllOwners());
//    }

    @PutMapping("/{id}")
    public ResponseEntity<OwnerDTO> updateOwner(@PathVariable Long id, @RequestBody OwnerDTO ownerDTO) {
        Owner owner = OwnerMapper.toEntity(ownerDTO);
        Owner updated = ownerService.updateOwner(id, owner);
        return ResponseEntity.ok(OwnerMapper.toDTO(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOwner(@PathVariable Long id) {
        ownerService.deleteOwner(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public List<OwnerDTO> getAllOwners() {
        return ownerService.getAllOwners().stream()
                .map(OwnerMapper::toDTO)
                .collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<OwnerDTO> addOwner(@RequestBody OwnerDTO ownerDTO) {
        Owner owner = OwnerMapper.toEntity(ownerDTO);   // convert DTO -> entity
        Owner saved = ownerService.addOwner(owner);
        return ResponseEntity.ok(OwnerMapper.toDTO(saved));  // convert back
    }
    // ✅ Get single owner by ID as DTO
    @GetMapping("/{id}")
    public ResponseEntity<OwnerDTO> getOwnerById(@PathVariable Long id) {
        return ownerService.getOwnerById(id)
                .map(OwnerMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
