package com.omgservers.service.event.body.module.client;

import com.omgservers.service.event.EventBodyModel;
import com.omgservers.service.event.EventQualifierEnum;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ClientMatchmakerRefCreatedEventBodyModel extends EventBodyModel {

    @NotNull
    Long clientId;

    @NotNull
    Long id;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.CLIENT_MATCHMAKER_REF_CREATED;
    }
}
