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
public class ProjectDeletedEventBodyModel extends EventBodyModel {

    Long tenantId;
    Long id;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.PROJECT_DELETED;
    }

    @Override
    public Long getGroupId() {
        return tenantId;
    }
}