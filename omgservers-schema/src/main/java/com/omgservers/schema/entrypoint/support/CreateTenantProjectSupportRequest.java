package com.omgservers.schema.entrypoint.support;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateTenantProjectSupportRequest {

    @NotNull
    Long tenantId;
}
