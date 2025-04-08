package com.omgservers.service.shard.root.impl.service.rootService.impl.method.root;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.module.root.root.DeleteRootRequest;
import com.omgservers.schema.module.root.root.DeleteRootResponse;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.shard.root.impl.operation.root.DeleteRootOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class DeleteRootMethodImpl implements DeleteRootMethod {

    final ChangeWithContextOperation changeWithContextOperation;
    final DeleteRootOperation deleteRootOperation;

    @Override
    public Uni<DeleteRootResponse> execute(final ShardModel shardModel,
                                           final DeleteRootRequest request) {
        log.trace("{}", request);

        final var id = request.getId();
        return changeWithContextOperation.<Boolean>changeWithContext(
                        (changeContext, sqlConnection) -> deleteRootOperation.execute(changeContext,
                                sqlConnection,
                                shardModel.shard(),
                                id))
                .map(ChangeContext::getResult)
                .map(DeleteRootResponse::new);
    }
}
