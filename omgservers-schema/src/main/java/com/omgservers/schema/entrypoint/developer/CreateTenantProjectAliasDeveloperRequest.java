package com.omgservers.schema.entrypoint.developer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTenantProjectAliasDeveloperRequest {

    /**
     * Tenant id or tenant alias.
     */
    @NotBlank
    @Size(max = 64)
    String tenant;

    @NotNull
    Long projectId;

    @NotBlank
    @Size(max = 64)
    String alias;
}