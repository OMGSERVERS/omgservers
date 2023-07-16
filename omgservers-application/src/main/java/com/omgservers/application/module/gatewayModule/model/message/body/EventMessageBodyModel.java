package com.omgservers.application.module.gatewayModule.model.message.body;

import com.omgservers.application.module.gatewayModule.model.message.MessageBodyModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class EventMessageBodyModel extends MessageBodyModel {

    Object event;
}
