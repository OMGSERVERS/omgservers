package com.omgservers.testDataFactory;

import com.omgservers.model.dto.user.SyncPlayerRequest;
import com.omgservers.model.dto.user.SyncUserRequest;
import com.omgservers.model.player.PlayerModel;
import com.omgservers.model.stage.StageModel;
import com.omgservers.model.tenant.TenantModel;
import com.omgservers.model.user.UserModel;
import com.omgservers.model.user.UserRoleEnum;
import com.omgservers.service.factory.user.PlayerModelFactory;
import com.omgservers.service.factory.user.UserModelFactory;
import com.omgservers.service.module.user.impl.service.userService.testInterface.UserServiceTestInterface;
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

    public UserModel createWorkerUser(String password) {
        final var passwordHash = BcryptUtil.bcryptHash(password);
        final var user = userModelFactory.create(UserRoleEnum.WORKER, passwordHash);
        final var syncUserRequest = new SyncUserRequest(user);
        userService.syncUser(syncUserRequest);
        return user;
    }

    public PlayerModel createUserPlayer(final UserModel user,
                                        final TenantModel tenant,
                                        final StageModel stage) {
        final var userId = user.getId();
        final var tenantId = tenant.getId();
        final var stageId = stage.getId();
        final var player = playerModelFactory.create(userId, tenantId, stageId);
        final var syncPlayerRequest = new SyncPlayerRequest(player);
        userService.syncPlayer(syncPlayerRequest);
        return player;
    }
}
