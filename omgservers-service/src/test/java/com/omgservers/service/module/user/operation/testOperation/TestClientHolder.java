package com.omgservers.service.module.user.operation.testOperation;

import com.omgservers.model.client.ClientModel;
import com.omgservers.model.player.PlayerModel;
import com.omgservers.model.user.UserModel;

public record TestClientHolder(UserModel user,
                               PlayerModel player,
                               ClientModel client) {
}
