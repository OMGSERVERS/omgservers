package com.omgservers.service.server.bootstrap.impl.method;

import com.omgservers.schema.model.root.RootModel;
import com.omgservers.schema.shard.root.root.SyncRootRequest;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.factory.alias.AliasModelFactory;
import com.omgservers.service.factory.root.RootModelFactory;
import com.omgservers.service.operation.alias.CreateRootAliasOperation;
import com.omgservers.service.operation.alias.FindRootAliasOperation;
import com.omgservers.service.operation.server.GetServiceConfigOperation;
import com.omgservers.service.server.bootstrap.dto.BootstrapRootEntityRequest;
import com.omgservers.service.server.bootstrap.dto.BootstrapRootEntityResponse;
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
    final CreateRootAliasOperation createRootAliasOperation;
    final FindRootAliasOperation findRootAliasOperation;

    final RootModelFactory rootModelFactory;

    final AliasModelFactory aliasModelFactory;

    @Override
    public Uni<BootstrapRootEntityResponse> execute(final BootstrapRootEntityRequest request) {
        log.debug("Bootstrapping root entity");

        return findRootAliasOperation.execute()
                .replaceWith(Boolean.FALSE)
                .onFailure(ServerSideNotFoundException.class)
                .recoverWithUni(t -> createRoot()
                        .flatMap(root -> createRootAliasOperation.execute(root.getId())
                                .invoke(alias -> log.info("Root entity \"{}\" was created", root.getId())))
                        .replaceWith(Boolean.TRUE))
                .map(BootstrapRootEntityResponse::new);
    }

    Uni<RootModel> createRoot() {
        final var root = rootModelFactory.create();

        final var request = new SyncRootRequest(root);
        return rootShard.getService().execute(request)
                .replaceWith(root);
    }
}
