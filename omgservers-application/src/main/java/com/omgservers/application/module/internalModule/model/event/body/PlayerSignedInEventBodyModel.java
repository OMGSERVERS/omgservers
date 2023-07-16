package com.omgservers.application.module.internalModule.model.event.body;

import com.omgservers.application.module.internalModule.model.event.EventBodyModel;
import com.omgservers.application.module.internalModule.model.event.EventModel;
import com.omgservers.application.module.internalModule.model.event.EventQualifierEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PlayerSignedInEventBodyModel extends EventBodyModel {

    static public EventModel createEvent(final UUID tenant,
                                         final UUID stage,
                                         final UUID user,
                                         final UUID player,
                                         final UUID client) {
        final var body = new PlayerSignedInEventBodyModel(tenant, stage, user, player, client);
        final var event = EventModel.create(user, EventQualifierEnum.PLAYER_SIGNED_IN, body);
        return event;
    }

    UUID tenant;
    UUID stage;
    UUID user;
    UUID player;
    UUID client;
}
