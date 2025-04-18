package com.omgservers.service.shard.user.impl.service.userService.impl.method.user;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.user.DeleteUserRequest;
import com.omgservers.schema.shard.user.DeleteUserResponse;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.shard.user.impl.operation.user.DeleteUserOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeleteUserMethodImpl implements DeleteUserMethod {

    final ChangeWithContextOperation changeWithContextOperation;
    final DeleteUserOperation deleteUserOperation;

    @Override
    public Uni<DeleteUserResponse> deleteUser(final ShardModel shardModel,
                                              final DeleteUserRequest request) {
        log.trace("{}", request);

        final var id = request.getId();
        return changeWithContextOperation.<Boolean>changeWithContext((changeContext, sqlConnection) ->
                        deleteUserOperation.execute(
                                changeContext,
                                sqlConnection,
                                shardModel.slot(),
                                id))
                .map(ChangeContext::getResult)
                .map(DeleteUserResponse::new);
    }
}
