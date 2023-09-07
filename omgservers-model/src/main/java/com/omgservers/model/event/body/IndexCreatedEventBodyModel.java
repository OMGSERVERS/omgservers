package com.omgservers.model.event.body;

import com.omgservers.model.event.EventBodyModel;
import com.omgservers.model.event.EventQualifierEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class IndexCreatedEventBodyModel extends EventBodyModel {

    Long id;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.INDEX_CREATED;
    }

    @Override
    public Long getGroupId() {
        return id;
    }
}
