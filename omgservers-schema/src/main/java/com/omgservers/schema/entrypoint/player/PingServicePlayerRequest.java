package com.omgservers.schema.entrypoint.player;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PingServicePlayerRequest {

    @NotBlank
    @Size(max = 64)
    String message;
}
