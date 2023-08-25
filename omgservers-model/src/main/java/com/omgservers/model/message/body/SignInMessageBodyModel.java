package com.omgservers.model.message.body;

import com.omgservers.model.message.MessageBodyModel;
import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SignInMessageBodyModel extends MessageBodyModel {

    Long tenantId;
    Long stageId;
    @ToString.Exclude
    String secret;
    Long userId;
    @ToString.Exclude
    String password;
}
