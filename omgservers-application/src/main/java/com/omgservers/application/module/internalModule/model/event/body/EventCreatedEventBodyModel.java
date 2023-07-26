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

    EventModel event;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.EVENT_CREATED;
    }

    @Override
    public Long getGroupId() {
        return event.getGroupId();
    }
}
