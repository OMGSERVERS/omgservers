package com.omgservers.application.module.gatewayModule.model.message.body;

import com.omgservers.application.module.gatewayModule.model.message.MessageBodyModel;
import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class MatchmakerMessageBodyModel extends MessageBodyModel {

    String mode;
}
