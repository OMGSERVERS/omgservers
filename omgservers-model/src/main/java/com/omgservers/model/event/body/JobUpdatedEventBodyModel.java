package com.omgservers.model.event.body;

import com.omgservers.model.event.EventBodyModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.job.JobTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class JobUpdatedEventBodyModel extends EventBodyModel {

    Long shardKey;
    Long entity;
    JobTypeEnum type;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.JOB_UPDATED;
    }

    @Override
    public Long getGroupId() {
        return entity;
    }
}
