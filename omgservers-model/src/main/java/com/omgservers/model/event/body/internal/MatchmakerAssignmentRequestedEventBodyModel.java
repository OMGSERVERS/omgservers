package com.omgservers.model.event.body.internal;

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
public class MatchmakerAssignmentRequestedEventBodyModel extends EventBodyModel {

    @NotNull
    Long clientId;

    @NotNull
    Long tenantId;

    @NotNull
    Long versionId;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.MATCHMAKER_ASSIGNMENT_REQUESTED;
    }
}
