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
public class SetObjectRequestedEventBodyModel extends EventBodyModel {

    @NotNull
    Long runtimeId;

    @NotNull
    Long userId;

    @NotNull
    Long clientId;

    @NotNull
    Object object;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.SET_OBJECT_REQUESTED;
    }

    @Override
    public Long getGroupId() {
        return runtimeId;
    }
}
