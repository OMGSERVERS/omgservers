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
public class VersionCreatedEventBodyModel extends EventBodyModel {

    static public EventModel createEvent(final UUID tenant,
                                         final UUID stage,
                                         final UUID uuid) {
        final var body = new VersionCreatedEventBodyModel(tenant, stage, uuid);
        final var event = EventModel.create(stage, EventQualifierEnum.VERSION_CREATED, body);
        return event;
    }

    UUID tenant;
    UUID stage;
    UUID uuid;
}
