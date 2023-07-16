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
public class TenantDeletedEventBodyModel extends EventBodyModel {

    static public EventModel createEvent(final UUID uuid) {
        final var body = new TenantDeletedEventBodyModel(uuid);
        final var event = EventModel.create(uuid, EventQualifierEnum.TENANT_DELETED, body);
        return event;
    }

    UUID uuid;
}
