package com.omgservers.service.operation.authz;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideForbiddenException;
import com.omgservers.service.operation.docker.ParseDockerImageOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class AuthorizeDockerImageOperationImpl implements AuthorizeDockerImageOperation {

    final ParseDockerImageOperation parseDockerImageOperation;

    @Override
    public DockerImageAuthorization execute(final String tenant,
                                            final String project,
                                            final String image) {
        final var parsedImage = parseDockerImageOperation.execute(image);
        final var namespaceAuthorized = tenant.equals(parsedImage.namespace());
        final var projectAuthorized = project.equals(parsedImage.name());

        if (namespaceAuthorized && projectAuthorized) {
            return new DockerImageAuthorization(parsedImage);
        } else {
            throw new ServerSideForbiddenException(ExceptionQualifierEnum.WRONG_IMAGE);
        }
    }
}
