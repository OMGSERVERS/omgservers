package com.omgservers.schema.model.incomingMessage;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.omgservers.schema.message.MessageBodyDto;
import com.omgservers.schema.message.MessageQualifierEnum;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonDeserialize(using = IncomingMessageDeserializer.class)
public class IncomingMessageModel {

    @NotNull
    Long id;

    @NotNull
    MessageQualifierEnum qualifier;

    @NotNull
    @ToString.Exclude
    MessageBodyDto body;
}
