package com.codecraft.veterinary_pet_system.dto;

import lombok.*;

@Setter
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String username;
    private String role;
    // ✅ no password here, don’t expose it in API
}
