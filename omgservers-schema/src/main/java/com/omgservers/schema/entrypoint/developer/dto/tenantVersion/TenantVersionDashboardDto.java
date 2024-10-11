package com.omgservers.schema.entrypoint.developer.dto.tenantVersion;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TenantVersionDashboardDto {

    TenantVersionDto tenantVersion;

    List<TenantBuildRequestDto> tenantBuildRequests;

    List<TenantImageDto> tenantImages;
}
