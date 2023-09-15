package com.omgservers.model.event.body;

import com.omgservers.model.event.EventBodyModel;
import com.omgservers.model.event.EventQualifierEnum;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ServiceAccountCreatedEventBodyModel extends EventBodyModel {

    @NotNull
    Long id;

    @NotNull
    @Size(max = 64)
    String username;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.SERVICE_ACCOUNT_CREATED;
    }

    @Override
    public Long getGroupId() {
        return id;
    }
}
