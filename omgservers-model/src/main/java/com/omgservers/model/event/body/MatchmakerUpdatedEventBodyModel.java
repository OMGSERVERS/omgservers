package com.omgservers.model.event.body;

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
public class MatchmakerUpdatedEventBodyModel extends EventBodyModel {

    @NotNull
    Long id;

    @NotNull
    Long tenantId;

    @NotNull
    Long stageId;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.MATCHMAKER_UPDATED;
    }

    @Override
    public Long getGroupId() {
        return id;
    }
}
