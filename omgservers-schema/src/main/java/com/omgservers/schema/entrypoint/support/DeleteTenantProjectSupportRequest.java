package com.omgservers.schema.entrypoint.support;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteTenantProjectSupportRequest {

    @NotNull
    Long tenantId;

    @NotNull
    Long tenantProjectId;
}
