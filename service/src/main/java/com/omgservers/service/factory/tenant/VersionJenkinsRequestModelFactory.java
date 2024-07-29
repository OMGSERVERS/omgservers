package com.omgservers.service.factory.tenant;

import com.omgservers.schema.model.versionJenkinsRequest.VersionJenkinsRequestModel;
import com.omgservers.schema.model.versionJenkinsRequest.VersionJenkinsRequestQualifierEnum;
import com.omgservers.service.server.operation.generateId.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class VersionJenkinsRequestModelFactory {

    final GenerateIdOperation generateIdOperation;

    public VersionJenkinsRequestModel create(final Long tenantId,
                                             final Long versionId,
                                             final VersionJenkinsRequestQualifierEnum qualifier,
                                             final Integer buildNumber) {
        final var id = generateIdOperation.generateId();
        final var idempotencyKey = generateIdOperation.generateStringId();
        return create(id, tenantId, versionId, qualifier, buildNumber, idempotencyKey);
    }

    public VersionJenkinsRequestModel create(final Long tenantId,
                                             final Long versionId,
                                             final VersionJenkinsRequestQualifierEnum qualifier,
                                             final Integer buildNumber,
                                             final String idempotencyKey) {
        final var id = generateIdOperation.generateId();
        return create(id, tenantId, versionId, qualifier, buildNumber, idempotencyKey);
    }

    public VersionJenkinsRequestModel create(final Long id,
                                             final Long tenantId,
                                             final Long versionId,
                                             final VersionJenkinsRequestQualifierEnum qualifier,
                                             final Integer buildNumber,
                                             final String idempotencyKey) {
        final var now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var versionJenkinsRequest = new VersionJenkinsRequestModel();
        versionJenkinsRequest.setId(id);
        versionJenkinsRequest.setIdempotencyKey(idempotencyKey);
        versionJenkinsRequest.setTenantId(tenantId);
        versionJenkinsRequest.setVersionId(versionId);
        versionJenkinsRequest.setCreated(now);
        versionJenkinsRequest.setModified(now);
        versionJenkinsRequest.setQualifier(qualifier);
        versionJenkinsRequest.setBuildNumber(buildNumber);
        versionJenkinsRequest.setDeleted(false);
        return versionJenkinsRequest;
    }
}
