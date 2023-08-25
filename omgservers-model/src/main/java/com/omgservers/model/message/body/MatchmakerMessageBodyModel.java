package com.omgservers.model.message.body;

import com.omgservers.model.message.MessageBodyModel;
import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class MatchmakerMessageBodyModel extends MessageBodyModel {

    String mode;
}
