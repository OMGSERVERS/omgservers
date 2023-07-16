package com.omgservers.application.module.internalModule.model.event.body;

import com.omgservers.application.module.internalModule.model.event.EventBodyModel;
import com.omgservers.application.module.internalModule.model.event.EventModel;
import com.omgservers.application.module.internalModule.model.event.EventQualifierEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class EventCreatedEventBodyModel extends EventBodyModel {

    static public EventModel createEvent(final EventModel event) {
        final var body = new EventCreatedEventBodyModel(event);
        final var eventModel = EventModel.create(event.getGroup(), EventQualifierEnum.EVENT_CREATED, body);
        return eventModel;
    }

    EventModel event;
}
