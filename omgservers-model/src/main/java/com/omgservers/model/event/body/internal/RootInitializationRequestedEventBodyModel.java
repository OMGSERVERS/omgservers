package com.omgservers.model.event.body.internal;

import com.omgservers.model.event.EventBodyModel;
import com.omgservers.model.event.EventQualifierEnum;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class RootInitializationRequestedEventBodyModel extends EventBodyModel {

    @NotNull
    Long rootId;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.ROOT_INITIALIZATION_REQUESTED;
    }
}
