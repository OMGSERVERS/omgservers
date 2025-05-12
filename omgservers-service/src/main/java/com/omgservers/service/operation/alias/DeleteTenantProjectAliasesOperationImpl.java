package com.omgservers.service.operation.alias;

import com.omgservers.schema.model.alias.AliasQualifierEnum;
import com.omgservers.schema.shard.alias.DeleteAliasRequest;
import com.omgservers.schema.shard.alias.DeleteAliasResponse;
import com.omgservers.service.exception.ServerSideClientException;
import com.omgservers.service.shard.alias.AliasShard;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class DeleteTenantProjectAliasesOperationImpl implements DeleteTenantProjectAliasesOperation {

    final AliasShard aliasShard;

    final ViewEntityAliasesOperation viewEntityAliasesOperation;

    @Override
    public Uni<Void> execute(final Long tenantId, final Long tenantProjectId) {
        final var shardKey = tenantId.toString();
        return viewEntityAliasesOperation.execute(AliasQualifierEnum.PROJECT,
                        shardKey,
                        tenantProjectId)
                .flatMap(aliases -> Multi.createFrom().iterable(aliases)
                        .onItem().transformToUniAndConcatenate(alias ->
                                deleteAlias(shardKey, alias.getId())
                                        .onFailure(ServerSideClientException.class)
                                        .recoverWithItem(t -> {
                                            log.warn("Failed to delete alias \"{}\" for project \"{}\", {}:{}",
                                                    alias.getValue(),
                                                    tenantProjectId,
                                                    t.getClass().getSimpleName(),
                                                    t.getMessage());
                                            return null;
                                        })
                        )
                        .collect().asList()
                        .replaceWithVoid()
                );
    }

    Uni<Boolean> deleteAlias(final String shardKey, final Long aliasId) {
        final var request = new DeleteAliasRequest(shardKey, aliasId);
        return aliasShard.getService().execute(request)
                .map(DeleteAliasResponse::getDeleted);
    }

}
