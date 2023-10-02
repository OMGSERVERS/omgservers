package com.omgservers.model.message.body;

import com.omgservers.model.message.MessageBodyModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class WelcomeMessageBodyModel extends MessageBodyModel {

    // TODO: fields for game server version/start configuration, etc
}
