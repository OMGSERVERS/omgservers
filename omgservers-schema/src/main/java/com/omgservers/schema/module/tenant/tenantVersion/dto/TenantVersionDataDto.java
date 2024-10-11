package com.omgservers.schema.module.tenant.tenantVersion.dto;

import com.omgservers.schema.model.tenantBuildRequest.TenantBuildRequestModel;
import com.omgservers.schema.model.tenantImage.TenantImageModel;
import com.omgservers.schema.model.tenantVersion.TenantVersionModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TenantVersionDataDto {

    @NotNull
    TenantVersionModel tenantVersion;

    @NotNull
    List<TenantBuildRequestModel> tenantBuildRequests;

    @NotNull
    List<TenantImageModel> tenantImages;
}
