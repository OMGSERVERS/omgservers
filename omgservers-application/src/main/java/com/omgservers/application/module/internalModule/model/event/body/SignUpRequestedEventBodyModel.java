package com.omgservers.application.module.internalModule.model.event.body;

import com.omgservers.application.module.internalModule.model.event.EventBodyModel;
import com.omgservers.application.module.internalModule.model.event.EventModel;
import com.omgservers.application.module.internalModule.model.event.EventQualifierEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.net.URI;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SignUpRequestedEventBodyModel extends EventBodyModel {

    static public EventModel createEvent(final URI server,
                                         final UUID connection,
                                         final UUID tenant,
                                         final UUID stage,
                                         final String secret) {
        final var body = new SignUpRequestedEventBodyModel(server, connection, tenant, stage, secret);
        final var group = UUID.randomUUID();
        final var event = EventModel.create(group, EventQualifierEnum.SIGN_UP_REQUESTED, body);
        return event;
    }

    URI server;
    UUID connection;
    UUID tenant;
    UUID stage;
    String secret;
}
