package com.omgservers.application.module.internalModule.model.event.body;

import com.omgservers.application.module.internalModule.model.event.EventBodyModel;
import com.omgservers.application.module.internalModule.model.event.EventQualifierEnum;
import com.omgservers.application.module.internalModule.model.job.JobType;
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
    JobType type;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.JOB_UPDATED;
    }

    @Override
    public Long getGroupId() {
        return entity;
    }
}
