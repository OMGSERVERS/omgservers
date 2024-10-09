package com.omgservers.schema.model.clientMessage;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.omgservers.schema.model.message.MessageBodyDto;
import com.omgservers.schema.model.message.MessageQualifierEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
    @EqualsAndHashCode.Exclude
    Instant created;

    @NotNull
    @EqualsAndHashCode.Exclude
    Instant modified;

    @NotNull
    MessageQualifierEnum qualifier;

    @NotNull
    MessageBodyDto body;

    @NotNull
    Boolean deleted;
}
