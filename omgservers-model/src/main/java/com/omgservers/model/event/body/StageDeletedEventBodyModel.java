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
public class StageDeletedEventBodyModel extends EventBodyModel {

    @NotNull
    Long tenantId;

    @NotNull
    Long id;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.STAGE_DELETED;
    }
}
