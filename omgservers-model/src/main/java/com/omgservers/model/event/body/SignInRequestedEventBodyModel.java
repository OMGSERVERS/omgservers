package com.omgservers.model.event.body;

import com.omgservers.model.event.EventBodyModel;
import com.omgservers.model.event.EventQualifierEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.net.URI;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SignInRequestedEventBodyModel extends EventBodyModel {

    URI server;
    Long connectionId;
    Long tenantId;
    Long stageId;
    String secret;
    Long userId;
    String password;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.SIGN_IN_REQUESTED;
    }

    @Override
    public Long getGroupId() {
        return connectionId;
    }
}
