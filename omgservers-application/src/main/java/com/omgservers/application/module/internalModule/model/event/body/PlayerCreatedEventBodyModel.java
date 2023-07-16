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
public class PlayerCreatedEventBodyModel extends EventBodyModel {

    static public EventModel createEvent(final UUID user,
                                         final UUID stage,
                                         final UUID uuid) {
        final var body = new PlayerCreatedEventBodyModel(user, stage, uuid);
        final var event = EventModel.create(user, EventQualifierEnum.PLAYER_CREATED, body);
        return event;
    }

    UUID user;
    UUID stage;
    UUID uuid;
}
