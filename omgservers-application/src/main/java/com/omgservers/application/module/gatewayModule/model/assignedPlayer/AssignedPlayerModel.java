package com.omgservers.application.module.gatewayModule.model.assignedPlayer;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class AssignedPlayerModel {

    UUID tenant;
    UUID stage;
    UUID user;
    UUID player;
    UUID client;
}
