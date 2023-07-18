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
public class SignInRequestedEventBodyModel extends EventBodyModel {

    static public EventModel createEvent(final URI server,
                                         final UUID connection,
                                         final UUID tenant,
                                         final UUID stage,
                                         final String secret,
                                         final UUID user,
                                         final String password) {
        final var body = new SignInRequestedEventBodyModel(server, connection, tenant, stage, secret, user, password);
        final var group = connection;
        final var event = EventModel.create(group, EventQualifierEnum.SIGN_IN_REQUESTED, body);
        return event;
    }

    URI server;
    UUID connection;
    UUID tenant;
    UUID stage;
    String secret;
    UUID user;
    String password;
}
