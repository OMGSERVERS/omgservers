package com.omgservers.schema.entrypoint.developer.dto.tenantVersion;

import com.omgservers.schema.model.tenantBuildRequest.TenantBuildRequestQualifierEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TenantBuildRequestDto {

    Long id;

    Long tenantId;

    Long versionId;

    Instant created;

    TenantBuildRequestQualifierEnum qualifier;

    Integer buildNumber;
}
