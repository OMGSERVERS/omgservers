package com.omgservers.service.event.body.internal;

import com.omgservers.service.event.EventBodyModel;
import com.omgservers.service.event.EventQualifierEnum;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ServiceBootstrapRequestedEventBodyModel extends EventBodyModel {

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.SERVICE_BOOTSTRAP_REQUESTED;
    }
}
