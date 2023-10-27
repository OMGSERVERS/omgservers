package com.omgservers.model.event.body;

import com.omgservers.model.client.ClientModel;
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
public class ClientDeletedEventBodyModel extends EventBodyModel {

    @NotNull
    ClientModel client;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.CLIENT_DELETED;
    }

    @Override
    public Long getGroupId() {
        return client.getUserId();
    }
}
