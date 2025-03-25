package com.omgservers.testDataFactory;

import com.omgservers.schema.model.player.PlayerModel;
import com.omgservers.schema.model.tenant.TenantModel;
import com.omgservers.schema.model.tenantStage.TenantStageModel;
import com.omgservers.schema.model.user.UserModel;
import com.omgservers.schema.model.user.UserRoleEnum;
import com.omgservers.schema.module.user.SyncPlayerRequest;
import com.omgservers.schema.module.user.SyncUserRequest;
import com.omgservers.service.factory.user.PlayerModelFactory;
import com.omgservers.service.factory.user.UserModelFactory;
import com.omgservers.service.shard.user.service.testInterface.UserServiceTestInterface;
import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class UserTestDataFactory {

    final UserServiceTestInterface userService;

    final PlayerModelFactory playerModelFactory;
    final UserModelFactory userModelFactory;

    public UserModel createDeveloperUser(String password) {
        final var passwordHash = BcryptUtil.bcryptHash(password);
        final var user = userModelFactory.create(UserRoleEnum.DEVELOPER, passwordHash);
        final var syncUserRequest = new SyncUserRequest(user);
        userService.syncUser(syncUserRequest);
        return user;
    }

    public UserModel createPlayerUser(String password) {
        final var passwordHash = BcryptUtil.bcryptHash(password);
        final var user = userModelFactory.create(UserRoleEnum.PLAYER, passwordHash);
        final var syncUserRequest = new SyncUserRequest(user);
        userService.syncUser(syncUserRequest);
        return user;
    }

    public UserModel createRuntimeUser(String password) {
        final var passwordHash = BcryptUtil.bcryptHash(password);
        final var user = userModelFactory.create(UserRoleEnum.RUNTIME, passwordHash);
        final var syncUserRequest = new SyncUserRequest(user);
        userService.syncUser(syncUserRequest);
        return user;
    }

    public PlayerModel createUserPlayer(final UserModel user,
                                        final TenantModel tenant,
                                        final TenantStageModel stage) {
        final var userId = user.getId();
        final var tenantId = tenant.getId();
        final var tenantStageId = stage.getId();
        final var player = playerModelFactory.create(userId, tenantId, tenantStageId);
        final var syncPlayerRequest = new SyncPlayerRequest(player);
        userService.syncPlayer(syncPlayerRequest);
        return player;
    }
}
