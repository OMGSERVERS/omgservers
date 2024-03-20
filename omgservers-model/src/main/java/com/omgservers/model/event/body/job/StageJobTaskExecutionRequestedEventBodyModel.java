package com.omgservers.model.event.body.job;

import com.omgservers.model.event.EventBodyModel;
import com.omgservers.model.event.EventQualifierEnum;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class StageJobTaskExecutionRequestedEventBodyModel extends EventBodyModel {

    @NotNull
    Long tenantId;

    @NotNull
    Long stageId;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.STAGE_JOB_TASK_EXECUTION_REQUESTED;
    }
}
