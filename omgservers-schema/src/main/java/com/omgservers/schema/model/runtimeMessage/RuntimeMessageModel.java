package com.omgservers.schema.model.runtimeMessage;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.omgservers.schema.message.MessageBodyDto;
import com.omgservers.schema.message.MessageQualifierEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonDeserialize(using = RuntimeMessageDeserializer.class)
public class RuntimeMessageModel {

    @NotNull
    Long id;

    @NotBlank
    @ToString.Exclude
    String idempotencyKey;

    @NotNull
    Long runtimeId;

    @NotNull
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    Instant created;

    @NotNull
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    Instant modified;

    @NotNull
    MessageQualifierEnum qualifier;

    @NotNull
    @ToString.Exclude
    MessageBodyDto body;

    @NotNull
    Boolean deleted;
}
