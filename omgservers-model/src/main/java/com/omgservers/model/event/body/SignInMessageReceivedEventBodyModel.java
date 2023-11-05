package com.omgservers.model.event.body;

import com.omgservers.model.event.EventBodyModel;
import com.omgservers.model.event.EventQualifierEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.net.URI;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SignInMessageReceivedEventBodyModel extends EventBodyModel {

    @NotNull
    URI server;

    @NotNull
    Long connectionId;

    @NotNull
    Long tenantId;

    @NotNull
    Long stageId;

    @NotBlank
    @Size(max = 1024)
    String secret;

    @NotNull
    Long userId;

    @NotNull
    @Size(max = 1024)
    String password;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.SIGN_IN_MESSAGE_RECEIVED;
    }

    @Override
    public Long getGroupId() {
        return connectionId;
    }
}
