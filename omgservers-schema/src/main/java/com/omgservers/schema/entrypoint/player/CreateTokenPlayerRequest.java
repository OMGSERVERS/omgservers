package com.omgservers.schema.entrypoint.player;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTokenPlayerRequest {

    @NotNull
    Long userId;

    @NotBlank
    @Size(max = 64)
    @ToString.Exclude
    String password;
}
