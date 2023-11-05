package com.omgservers.utils.operation.deleteVersionOperation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.omgservers.utils.DeveloperCli;
import com.omgservers.utils.model.VersionParameters;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class DeleteVersionOperationImpl implements DeleteVersionOperation {

    @Inject
    DeveloperCli developerCli;

    @Override
    public Boolean deleteVersion(VersionParameters versionParameters)
            throws JsonProcessingException {

        final var developerUserId = versionParameters.getDeveloperUserId();
        final var developerPassword = versionParameters.getDeveloperPassword();

        final var token = developerCli.createDeveloperToken(developerUserId, developerPassword);

        final var tenantId = versionParameters.getTenantId();
        final var versionId = versionParameters.getVersionId();
        final var deleteVersionResponse = developerCli.deleteVersion(token, tenantId, versionId);
        return deleteVersionResponse.getDeleted();
    }
}
