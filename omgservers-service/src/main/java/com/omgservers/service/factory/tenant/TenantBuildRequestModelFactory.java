package com.omgservers.service.factory.tenant;

import com.omgservers.schema.model.tenantBuildRequest.TenantBuildRequestModel;
import com.omgservers.schema.model.tenantBuildRequest.TenantBuildRequestQualifierEnum;
import com.omgservers.service.operation.server.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class TenantBuildRequestModelFactory {

    final GenerateIdOperation generateIdOperation;

    public TenantBuildRequestModel create(final Long tenantId,
                                          final Long versionId,
                                          final TenantBuildRequestQualifierEnum qualifier,
                                          final Integer buildNumber) {
        final var id = generateIdOperation.generateId();
        final var idempotencyKey = generateIdOperation.generateStringId();
        return create(id, tenantId, versionId, qualifier, buildNumber, idempotencyKey);
    }

    public TenantBuildRequestModel create(final Long tenantId,
                                          final Long versionId,
                                          final TenantBuildRequestQualifierEnum qualifier,
                                          final Integer buildNumber,
                                          final String idempotencyKey) {
        final var id = generateIdOperation.generateId();
        return create(id, tenantId, versionId, qualifier, buildNumber, idempotencyKey);
    }

    public TenantBuildRequestModel create(final Long id,
                                          final Long tenantId,
                                          final Long versionId,
                                          final TenantBuildRequestQualifierEnum qualifier,
                                          final Integer buildNumber,
                                          final String idempotencyKey) {
        final var now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var tenantBuildRequest = new TenantBuildRequestModel();
        tenantBuildRequest.setId(id);
        tenantBuildRequest.setIdempotencyKey(idempotencyKey);
        tenantBuildRequest.setTenantId(tenantId);
        tenantBuildRequest.setVersionId(versionId);
        tenantBuildRequest.setCreated(now);
        tenantBuildRequest.setModified(now);
        tenantBuildRequest.setQualifier(qualifier);
        tenantBuildRequest.setBuildNumber(buildNumber);
        tenantBuildRequest.setDeleted(false);
        return tenantBuildRequest;
    }
}
