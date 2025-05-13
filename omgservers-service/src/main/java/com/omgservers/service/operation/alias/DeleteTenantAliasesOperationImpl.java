package com.omgservers.service.operation.alias;

import com.omgservers.schema.model.alias.AliasModel;
import com.omgservers.schema.model.alias.AliasQualifierEnum;
import com.omgservers.schema.shard.alias.DeleteAliasRequest;
import com.omgservers.schema.shard.alias.DeleteAliasResponse;
import com.omgservers.schema.shard.alias.FindAliasRequest;
import com.omgservers.schema.shard.alias.FindAliasResponse;
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
class DeleteTenantAliasesOperationImpl implements DeleteTenantAliasesOperation {

    final AliasShard aliasShard;

    final ViewPtrAliasesOperation viewPtrAliasesOperation;

    @Override
    public Uni<Void> execute(final Long tenantId) {
        return viewPtrAliasesOperation.execute(tenantId)
                .flatMap(ptrAliases -> Multi.createFrom().iterable(ptrAliases)
                        .onItem().transformToUniAndConcatenate(ptrAlias ->
                                findAndDeleteAlias(tenantId, ptrAlias.getValue())
                                        .onFailure(ServerSideClientException.class)
                                        .recoverWithItem(t -> {
                                            log.warn("Failed to delete alias \"{}\" for tenant \"{}\", {}:{}",
                                                    ptrAlias.getValue(),
                                                    tenantId,
                                                    t.getClass().getSimpleName(),
                                                    t.getMessage());
                                            return null;
                                        })
                        )
                        .collect().asList()
                        .replaceWithVoid()
                );
    }

    Uni<Boolean> findAndDeleteAlias(final Long tenantId, final String aliasValue) {
        return findAlias(tenantId, aliasValue)
                .flatMap(alias -> deleteAlias(alias.getShardKey(), alias.getId()));
    }

    Uni<AliasModel> findAlias(final Long tenantId,
                              final String alisValue) {
        final var request = new FindAliasRequest(AliasQualifierEnum.TENANT, alisValue, 0L, alisValue);
        return aliasShard.getService().execute(request)
                .map(FindAliasResponse::getAlias);
    }

    Uni<Boolean> deleteAlias(final String shardKey, final Long aliasId) {
        final var request = new DeleteAliasRequest(shardKey, aliasId);
        return aliasShard.getService().execute(request)
                .map(DeleteAliasResponse::getDeleted);
    }

}
