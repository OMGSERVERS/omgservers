package com.omgservers.service.operation.docker;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideBadRequestException;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ParseDockerImageOperationImpl implements ParseDockerImageOperation {

    @Override
    public ParsedImage execute(final String image) {
        // namespace/name:tag

        final var imageIdParts = image.split("/");
        if (imageIdParts.length != 2) {
            throwError();
        }

        final var namespace = imageIdParts[0];
        if (namespace.isBlank()) {
            throwError();
        }
        final var nameTag = imageIdParts[1];

        final var nameTagParts = nameTag.split(":");
        if (nameTagParts.length != 2) {
            throwError();
        }

        final var name = nameTagParts[0];
        if (namespace.isBlank()) {
            throwError();
        }

        final var tag = nameTagParts[1];
        if (namespace.isBlank()) {
            throwError();
        }

        return new ParsedImage(namespace, name, tag);
    }

    void throwError() {
        throw new ServerSideBadRequestException(ExceptionQualifierEnum.WRONG_ARGUMENT, "incorrect docker image");
    }
}
