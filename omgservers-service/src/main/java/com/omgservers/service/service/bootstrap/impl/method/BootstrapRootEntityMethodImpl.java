package com.omgservers.service.service.bootstrap.impl.method;

import com.omgservers.schema.model.alias.AliasModel;
import com.omgservers.schema.model.alias.AliasQualifierEnum;
import com.omgservers.schema.model.root.RootModel;
import com.omgservers.schema.module.alias.FindAliasRequest;
import com.omgservers.schema.module.alias.FindAliasResponse;
import com.omgservers.schema.module.alias.SyncAliasRequest;
import com.omgservers.schema.module.root.root.SyncRootRequest;
import com.omgservers.service.configuration.DefaultAliasConfiguration;
import com.omgservers.service.configuration.GlobalShardConfiguration;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.factory.alias.AliasModelFactory;
import com.omgservers.service.factory.root.RootModelFactory;
import com.omgservers.service.operation.server.GetServiceConfigOperation;
import com.omgservers.service.service.bootstrap.dto.BootstrapRootEntityRequest;
import com.omgservers.service.service.bootstrap.dto.BootstrapRootEntityResponse;
import com.omgservers.service.shard.alias.AliasShard;
import com.omgservers.service.shard.root.RootShard;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class BootstrapRootEntityMethodImpl implements BootstrapRootEntityMethod {

    final AliasShard aliasShard;

    final RootShard rootShard;

    final GetServiceConfigOperation getServiceConfigOperation;

    final RootModelFactory rootModelFactory;

    final AliasModelFactory aliasModelFactory;

    @Override
    public Uni<BootstrapRootEntityResponse> execute(final BootstrapRootEntityRequest request) {
        log.debug("Bootstrapping root entity");

        return findRootAlias()
                .replaceWith(Boolean.FALSE)
                .onFailure(ServerSideNotFoundException.class)
                .recoverWithUni(t -> createRoot()
                        .flatMap(root -> createRootAlias(root.getId())
                                .invoke(alias -> log.info("Root entity \"{}\" was created", root.getId())))
                        .replaceWith(Boolean.TRUE))
                .map(BootstrapRootEntityResponse::new);
    }

    Uni<AliasModel> findRootAlias() {
        final var request = new FindAliasRequest(GlobalShardConfiguration.GLOBAL_SHARD_KEY,
                DefaultAliasConfiguration.GLOBAL_ENTITIES_GROUP,
                DefaultAliasConfiguration.ROOT_ENTITY_ALIAS);
        return aliasShard.getService().execute(request)
                .map(FindAliasResponse::getAlias);
    }

    Uni<AliasModel> createRootAlias(final Long rootId) {
        final var alias = aliasModelFactory.create(AliasQualifierEnum.ROOT,
                GlobalShardConfiguration.GLOBAL_SHARD_KEY,
                DefaultAliasConfiguration.GLOBAL_ENTITIES_GROUP,
                rootId,
                DefaultAliasConfiguration.ROOT_ENTITY_ALIAS);
        final var request = new SyncAliasRequest(alias);
        return aliasShard.getService().execute(request)
                .replaceWith(alias);
    }

    Uni<RootModel> createRoot() {
        final var root = rootModelFactory.create();

        final var request = new SyncRootRequest(root);
        return rootShard.getService().syncRoot(request)
                .replaceWith(root);
    }
}
