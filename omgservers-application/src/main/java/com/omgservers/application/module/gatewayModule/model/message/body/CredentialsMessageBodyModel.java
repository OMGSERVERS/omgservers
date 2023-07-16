package com.omgservers.application.module.gatewayModule.model.message.body;

import com.omgservers.application.module.gatewayModule.model.message.MessageBodyModel;
import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CredentialsMessageBodyModel extends MessageBodyModel {

    UUID user;
    @ToString.Exclude
    String password;
}
