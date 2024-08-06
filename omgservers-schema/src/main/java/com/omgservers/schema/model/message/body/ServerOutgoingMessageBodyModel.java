package com.omgservers.schema.model.message.body;

import com.omgservers.schema.model.message.MessageBodyModel;
import com.omgservers.schema.model.message.MessageBodyModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ServerOutgoingMessageBodyModel extends MessageBodyModel {

    @NotNull
    Object message;
}
