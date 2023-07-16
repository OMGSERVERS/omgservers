package com.omgservers.application.module.internalModule.model.event.body;

import com.omgservers.application.module.internalModule.model.event.EventBodyModel;
import com.omgservers.application.module.internalModule.model.event.EventModel;
import com.omgservers.application.module.internalModule.model.event.EventQualifierEnum;
import com.omgservers.application.module.internalModule.model.job.JobType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class JobCreatedEventBodyModel extends EventBodyModel {

    static public EventModel createEvent(final UUID shardKey,
                                         final UUID entity,
                                         final JobType type) {
        final var body = new JobCreatedEventBodyModel(shardKey, entity, type);
        final var event = EventModel.create(entity, EventQualifierEnum.JOB_CREATED, body);
        return event;
    }

    UUID shardKey;
    UUID entity;
    JobType type;
}
