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
public class MatchCreatedEventBodyModel extends EventBodyModel {

    static public EventModel createEvent(final UUID matchmaker,
                                         final UUID uuid) {
        final var body = new MatchCreatedEventBodyModel(matchmaker, uuid);
        final var event = EventModel.create(uuid, EventQualifierEnum.MATCH_CREATED, body);
        return event;
    }

    UUID matchmaker;
    UUID uuid;
}
