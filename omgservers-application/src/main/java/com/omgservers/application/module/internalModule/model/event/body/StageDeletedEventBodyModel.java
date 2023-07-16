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
public class StageDeletedEventBodyModel extends EventBodyModel {

    static public EventModel createEvent(final UUID tenant,
                                         final UUID uuid) {
        final var body = new StageDeletedEventBodyModel(tenant, uuid);
        final var event = EventModel.create(tenant, EventQualifierEnum.STAGE_DELETED, body);
        return event;
    }

    UUID tenant;
    UUID uuid;
}
