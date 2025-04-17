package com.omgservers.service.event.body.system;

import com.omgservers.service.event.EventBodyModel;
import com.omgservers.service.event.EventQualifierEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class IndexCreatedEventBodyModel extends EventBodyModel {

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.INDEX_CREATED;
    }
}
