package com.omgservers.service.module.user.impl.service.userService.impl.method.deleteUser;

import com.omgservers.model.dto.user.DeleteUserRequest;
import com.omgservers.model.dto.user.DeleteUserResponse;
import com.omgservers.service.module.user.impl.operation.deleteUser.DeleteUserOperation;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import com.omgservers.service.operation.changeWithContext.ChangeWithContextOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
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
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<DeleteUserResponse> deleteUser(final DeleteUserRequest request) {
        log.debug("Delete user, request={}", request);

        final var id = request.getId();
        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> changeWithContextOperation.<Boolean>changeWithContext(
                                (changeContext, sqlConnection) -> deleteUserOperation.deleteUser(
                                        changeContext,
                                        sqlConnection,
                                        shardModel.shard(),
                                        id))
                        .map(ChangeContext::getResult))
                .map(DeleteUserResponse::new);
    }
}
