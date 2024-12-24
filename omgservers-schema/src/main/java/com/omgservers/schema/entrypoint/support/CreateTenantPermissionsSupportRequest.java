package com.omgservers.schema.entrypoint.support;

import com.omgservers.schema.model.tenantPermission.TenantPermissionQualifierEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTenantPermissionsSupportRequest {

    @NotBlank
    @Size(max = 64)
    String tenant;

    @NotNull
    Long userId;

    @NotEmpty
    Set<TenantPermissionQualifierEnum> permissionsToCreate;
}
