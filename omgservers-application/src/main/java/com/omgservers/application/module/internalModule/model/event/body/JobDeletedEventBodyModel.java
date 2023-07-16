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
public class JobDeletedEventBodyModel extends EventBodyModel {

    static public EventModel createEvent(final UUID shardKey, final UUID entity) {
        final var body = new JobDeletedEventBodyModel(shardKey, entity);
        final var event = EventModel.create(entity, EventQualifierEnum.JOB_DELETED, body);
        return event;
    }

    UUID shardKey;
    UUID entity;
}
