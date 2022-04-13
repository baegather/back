package com.example.baegether.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@AllArgsConstructor
@Setter
@Data
@NoArgsConstructor
public class UserDto {
    @JsonProperty("user_id")
    private Long id;

}
