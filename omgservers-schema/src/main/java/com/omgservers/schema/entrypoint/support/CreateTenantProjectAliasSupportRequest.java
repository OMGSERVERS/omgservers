package com.omgservers.schema.entrypoint.support;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTenantProjectAliasSupportRequest {

    @NotBlank
    @Size(max = 64)
    String tenant;

    @NotNull
    Long projectId;

    @NotBlank
    @Size(max = 64)
    String alias;
}
