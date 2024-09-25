package com.omgservers.schema.entrypoint.player;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateClientPlayerRequest {

    @NotNull
    Long tenantId;

    @NotNull
    Long tenantStageId;

    @NotBlank
    @Size(max = 1024)
    String tenantStageSecret;
}
