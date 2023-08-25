package com.omgservers.model.event.body;

import com.omgservers.model.event.EventBodyModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.job.JobType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class JobCreatedEventBodyModel extends EventBodyModel {

    Long shardKey;
    Long entity;
    JobType type;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.JOB_CREATED;
    }

    @Override
    public Long getGroupId() {
        return entity;
    }
}
