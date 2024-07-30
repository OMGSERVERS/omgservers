package com.omgservers.schema.event.body.system;

import com.omgservers.schema.event.EventBodyModel;
import com.omgservers.schema.event.EventQualifierEnum;
import com.omgservers.schema.event.EventBodyModel;
import com.omgservers.schema.event.EventQualifierEnum;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class JobCreatedEventBodyModel extends EventBodyModel {

    @NotNull
    Long id;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.JOB_CREATED;
    }
}