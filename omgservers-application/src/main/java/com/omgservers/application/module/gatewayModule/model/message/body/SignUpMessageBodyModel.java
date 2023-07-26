package com.omgservers.application.module.gatewayModule.model.message.body;

import com.omgservers.application.module.gatewayModule.model.message.MessageBodyModel;
import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SignUpMessageBodyModel extends MessageBodyModel {

    Long tenantId;
    Long stageId;
    @ToString.Exclude
    String secret;
}
