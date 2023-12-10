package com.omgservers.service.module.user.impl.service.shortcutService;

import com.omgservers.model.client.ClientModel;
import com.omgservers.model.player.PlayerAttributesModel;
import com.omgservers.model.player.PlayerModel;
import com.omgservers.model.user.UserModel;
import io.smallrye.mutiny.Uni;

public interface ShortcutService {

    Uni<UserModel> validateCredentials(Long userId, String password);

    Uni<UserModel> getUser(Long id);

    Uni<PlayerModel> getPlayer(Long userId, Long id);

    Uni<PlayerModel> findPlayer(Long userId, Long stageId);

    Uni<ClientModel> getClient(Long userId, Long clientId);

    Uni<Boolean> deleteClient(Long userId, Long clientId);

    Uni<PlayerAttributesModel> getPlayerAttributes(Long userId, Long playerId);
}
