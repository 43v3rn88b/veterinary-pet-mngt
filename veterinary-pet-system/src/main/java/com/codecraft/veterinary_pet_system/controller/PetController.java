package com.codecraft.veterinary_pet_system.controller;

import com.codecraft.veterinary_pet_system.entity.Owner;
import com.codecraft.veterinary_pet_system.entity.Pet;
import com.codecraft.veterinary_pet_system.dto.PetDTO;
import com.codecraft.veterinary_pet_system.mapper.PetMapper;
import com.codecraft.veterinary_pet_system.repository.OwnerRepository;
import com.codecraft.veterinary_pet_system.repository.PetRepository;
import com.codecraft.veterinary_pet_system.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pets")
//@RequiredArgsConstructor
public class PetController {
    private final PetService petService;
    @Autowired
    private OwnerRepository ownerRepository;
    @Autowired
    private PetRepository petRepository;

    public PetController(PetService petService) {
        this.petService = petService;
    }


    @PostMapping
    public ResponseEntity<PetDTO> addPet(@RequestBody PetDTO petDTO) {
        System.out.println("Received PetDTO: " + petDTO.getName() + ", ownerId=" + petDTO.getOwnerId());

        Owner owner = ownerRepository.findById(petDTO.getOwnerId())
                .orElseThrow(() -> new RuntimeException("Owner not found"));

        Pet pet = new Pet();
        pet.setName(petDTO.getName());
        pet.setSpecies(petDTO.getSpecies());
        pet.setBreed(petDTO.getBreed());
        pet.setAge(petDTO.getAge());
        pet.setOwner(owner);

        Pet savedPet = petRepository.save(pet);

        // convert back to DTO for the response
        PetDTO responseDto = PetMapper.toDTO(savedPet);

        return ResponseEntity.ok(responseDto);
    }


//    @PostMapping
//    public ResponseEntity<Pet> addPet(@RequestBody Pet pet) {
//        return ResponseEntity.ok(petService.addPet(pet));
//    }
// ✅ Get all pets (returns DTOs)
    @GetMapping
    public List<PetDTO> getAllPets() {
        return petService.getAllPets().stream()
                .map(PetMapper::toDTO)
                .collect(Collectors.toList());
    }
//    @GetMapping
//    public ResponseEntity<List<Pet>> getAllPets() {
//        return ResponseEntity.ok(petService.getAllPets());
//    }

    // ✅ Add new pet (returns DTO)
//    @PostMapping
//    public ResponseEntity<PetDTO> addPet(@RequestBody Pet pet) {
//        Pet saved = petService.addPet(pet);
//        return ResponseEntity.ok(PetMapper.toDTO(saved));
//    }

    // ✅ Update pet (returns DTO)
    @PutMapping("/{id}")
    public ResponseEntity<PetDTO> updatePet(
            @PathVariable Long id,
            @RequestBody PetDTO dto) {

        Owner owner = ownerRepository.findById(dto.getOwnerId())
                .orElseThrow(() -> new RuntimeException("Owner not found"));

        Pet updatedPet = PetMapper.toEntity(dto, owner); // pass owner here
        updatedPet.setId(id);

        Pet saved = petService.updatePet(id, updatedPet);

        return ResponseEntity.ok(PetMapper.toDTO(saved));
    }


    // ✅ Delete pet
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePet(@PathVariable Long id) {
        petService.deletePet(id);
        return ResponseEntity.noContent().build();
    }



    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<Pet>> getPetsByOwner(@PathVariable Long ownerId) {
        return ResponseEntity.ok(petService.getPetsByOwner(ownerId));
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<Pet> updatePet(@PathVariable Long id, @RequestBody Pet pet) {
//        return ResponseEntity.ok(petService.updatePet(id, pet));
//    }


}
