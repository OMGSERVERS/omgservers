package com.omgservers.model.message.body;

import com.omgservers.model.message.MessageBodyModel;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CredentialsMessageBodyModel extends MessageBodyModel {

    Long userId;
    @ToString.Exclude
    String password;
}
