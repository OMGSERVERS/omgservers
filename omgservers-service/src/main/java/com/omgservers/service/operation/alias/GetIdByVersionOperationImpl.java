package com.omgservers.service.operation.alias;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.shard.alias.AliasShard;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetIdByVersionOperationImpl implements GetIdByVersionOperation {

    final AliasShard aliasShard;

    @Override
    public Uni<Long> execute(final Long tenantId, final String version) {
        try {
            final var versionId = Long.valueOf(version);
            return Uni.createFrom().item(versionId);
        } catch (NumberFormatException e) {
            throw new ServerSideBadRequestException(ExceptionQualifierEnum.WRONG_VERSION,
                    "version aliases are not support yet");
        }
    }
}
