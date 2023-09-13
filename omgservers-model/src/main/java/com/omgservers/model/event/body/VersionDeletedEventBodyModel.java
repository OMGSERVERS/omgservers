package com.omgservers.model.event.body;

import com.omgservers.model.event.EventBodyModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.version.VersionModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class VersionDeletedEventBodyModel extends EventBodyModel {

    VersionModel version;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.VERSION_DELETED;
    }

    @Override
    public Long getGroupId() {
        return version.getTenantId();
    }
}
