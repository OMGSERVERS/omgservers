package com.omgservers.schema.entrypoint.developer.dto.tenantVersion;

import com.omgservers.schema.model.tenantJenkinsRequest.TenantJenkinsRequestQualifierEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TenantJenkinsRequestDto {

    Long id;

    Instant created;

    TenantJenkinsRequestQualifierEnum qualifier;

    Integer buildNumber;
}
