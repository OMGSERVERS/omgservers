package com.omgservers.service.module.user.operation.testOperation;

import com.omgservers.model.client.ClientModel;
import com.omgservers.model.player.PlayerModel;
import com.omgservers.model.user.UserModel;
import com.omgservers.model.user.UserRoleEnum;
import com.omgservers.service.factory.ClientModelFactory;
import com.omgservers.service.factory.PlayerModelFactory;
import com.omgservers.service.factory.UserModelFactory;
import com.omgservers.service.module.user.operation.testInterface.UpsertClientOperationTestInterface;
import com.omgservers.service.module.user.operation.testInterface.UpsertPlayerOperationTestInterface;
import com.omgservers.service.module.user.operation.testInterface.UpsertUserOperationTestInterface;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;

import java.net.URI;

@ApplicationScoped
@AllArgsConstructor
class CreateTestClientOperationImpl implements CreateTestClientOperation {

    final UpsertPlayerOperationTestInterface upsertPlayerOperation;
    final UpsertClientOperationTestInterface upsertClientOperation;
    final UpsertUserOperationTestInterface upsertUserOperation;
    final GenerateIdOperation generateIdOperation;

    final PlayerModelFactory playerModelFactory;
    final ClientModelFactory clientModelFactory;
    final UserModelFactory userModelFactory;

    @Override
    public TestClientHolder createTestClient(final Long tenantId,
                                             final Long stageId,
                                             final Long versionId,
                                             final Long defaultMatchmakerId,
                                             final Long defaultRuntimeId) {
        final var shard = 0;
        final var user = userModelFactory.create(UserRoleEnum.PLAYER, "passwordhash");
        upsertUserOperation.upsertUser(shard, user);
        final var player = playerModelFactory.create(user.getId(), tenantId, stageId);
        upsertPlayerOperation.upsertPlayer(shard, player);
        final var client = clientModelFactory.create(user.getId(),
                player.getId(),
                URI.create("http://localhost:8081"),
                connectionId(),
                versionId,
                defaultMatchmakerId,
                defaultRuntimeId);
        upsertClientOperation.upsertClient(shard, client);

        return new TestClientHolder(user, player, client);
    }

    Long connectionId() {
        return generateIdOperation.generateId();
    }

    public record TestClientHolder(
            UserModel user,
            PlayerModel player,
            ClientModel client) {
    }
}
