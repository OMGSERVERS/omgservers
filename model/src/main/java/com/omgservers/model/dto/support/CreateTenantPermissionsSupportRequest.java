package com.omgservers.model.dto.support;

import com.omgservers.model.tenantPermission.TenantPermissionEnum;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTenantPermissionsSupportRequest {

    @NotNull
    Long tenantId;

    @NotNull
    Long userId;

    @NotEmpty
    Set<TenantPermissionEnum> permissionsToCreate;
}
