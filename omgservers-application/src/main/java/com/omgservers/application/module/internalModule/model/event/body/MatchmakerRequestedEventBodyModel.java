package com.omgservers.application.module.internalModule.model.event.body;

import com.omgservers.application.module.internalModule.model.event.EventBodyModel;
import com.omgservers.application.module.internalModule.model.event.EventModel;
import com.omgservers.application.module.internalModule.model.event.EventQualifierEnum;
import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class MatchmakerRequestedEventBodyModel extends EventBodyModel {

    static public EventModel createEvent(final UUID tenant,
                                         final UUID stage,
                                         final UUID user,
                                         final UUID player,
                                         final UUID client,
                                         final String mode,
                                         final String pool) {
        final var body = new MatchmakerRequestedEventBodyModel(tenant, stage, user, player, client, mode, pool);
        final var group = user;
        final var event = EventModel.create(group, EventQualifierEnum.MATCHMAKER_REQUESTED, body);
        return event;
    }

    UUID tenant;
    UUID stage;
    UUID user;
    UUID player;
    UUID client;
    String mode;
    String pool;
}
