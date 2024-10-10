package com.omgservers.schema.entrypoint.developer.dto.tenantVersion;

import com.omgservers.schema.model.tenantImage.TenantImageQualifierEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TenantImageDto {

    Long id;

    Long tenantId;

    Long versionId;

    Instant created;

    TenantImageQualifierEnum qualifier;

    String imageId;
}
