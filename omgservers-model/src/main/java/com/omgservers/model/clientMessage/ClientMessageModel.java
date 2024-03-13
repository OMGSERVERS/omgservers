package com.omgservers.model.clientMessage;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.omgservers.model.message.MessageBodyModel;
import com.omgservers.model.message.MessageQualifierEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonDeserialize(using = ClientMessageDeserializer.class)
public class ClientMessageModel {

    @NotNull
    Long id;

    @NotBlank
    String idempotencyKey;

    @NotNull
    Long clientId;

    @NotNull
    Instant created;

    @NotNull
    Instant modified;

    @NotNull
    MessageQualifierEnum qualifier;

    @NotNull
    MessageBodyModel body;

    @NotNull
    Boolean deleted;
}
