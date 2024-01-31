package com.omgservers.service.module.user.impl.service.shortcutService;

import com.omgservers.model.player.PlayerAttributesModel;
import com.omgservers.model.player.PlayerModel;
import com.omgservers.model.user.UserModel;
import io.smallrye.mutiny.Uni;

public interface ShortcutService {

    Uni<String> createToken(Long userId, String password);

    Uni<UserModel> validateCredentials(Long userId, String password);

    Uni<UserModel> getUser(Long id);

    Uni<PlayerModel> getPlayer(Long userId, Long id);

    Uni<PlayerModel> findPlayer(Long userId, Long stageId);

    Uni<PlayerAttributesModel> getPlayerAttributes(Long userId, Long playerId);
}
