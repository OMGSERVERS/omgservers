package com.omgservers.schema.entrypoint.support;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateTenantStageSupportRequest {

    @NotBlank
    @Size(max = 64)
    String tenant;

    @NotBlank
    @Size(max = 64)
    String project;
}
