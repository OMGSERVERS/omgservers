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
public class ClientDisconnectedEventBodyModel extends EventBodyModel {

    static public EventModel createEvent(final UUID connection, final UUID client) {
        final var body = new ClientDisconnectedEventBodyModel(client);
        final var group = connection;
        final var eventModel = EventModel.create(group, EventQualifierEnum.CLIENT_DISCONNECTED, body);
        return eventModel;
    }

    UUID client;
}
