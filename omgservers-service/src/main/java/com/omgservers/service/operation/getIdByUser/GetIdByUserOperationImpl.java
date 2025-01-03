package com.omgservers.service.operation.getIdByUser;

import com.omgservers.schema.model.alias.AliasModel;
import com.omgservers.schema.module.alias.FindAliasRequest;
import com.omgservers.schema.module.alias.FindAliasResponse;
import com.omgservers.service.configuration.DefaultAliasConfiguration;
import com.omgservers.service.module.alias.AliasModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetIdByUserOperationImpl implements GetIdByUserOperation {

    final AliasModule aliasModule;

    @Override
    public Uni<Long> execute(final String user) {
        try {
            final var userId = Long.valueOf(user);
            return Uni.createFrom().item(userId);
        } catch (NumberFormatException e) {
            return findUserAlias(user)
                    .map(AliasModel::getEntityId);
        }
    }

    Uni<AliasModel> findUserAlias(final String userAlias) {
        final var request = new FindAliasRequest(DefaultAliasConfiguration.GLOBAL_SHARD_KEY,
                DefaultAliasConfiguration.DEFAULT_USER_GROUP,
                userAlias);
        return aliasModule.getService().execute(request)
                .map(FindAliasResponse::getAlias);
    }
}
