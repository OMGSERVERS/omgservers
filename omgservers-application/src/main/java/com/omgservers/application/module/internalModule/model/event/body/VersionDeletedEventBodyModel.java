package com.omgservers.application.module.internalModule.model.event.body;

import com.omgservers.application.module.internalModule.model.event.EventBodyModel;
import com.omgservers.application.module.internalModule.model.event.EventQualifierEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class VersionDeletedEventBodyModel extends EventBodyModel {

    Long id;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.VERSION_DELETED;
    }

    @Override
    public Long getGroupId() {
        return id;
    }
}
