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
public class JobDeletedEventBodyModel extends EventBodyModel {

    Long shardKey;
    Long entity;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.JOB_DELETED;
    }

    @Override
    public Long getGroupId() {
        return entity;
    }
}