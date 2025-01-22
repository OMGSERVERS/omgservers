package com.omgservers.schema.entrypoint.developer.dto.tenantVersion;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TenantVersionDetailsDto {

    TenantVersionDto version;

    List<TenantBuildRequestDto> buildRequests;

    List<TenantImageDto> images;
}
