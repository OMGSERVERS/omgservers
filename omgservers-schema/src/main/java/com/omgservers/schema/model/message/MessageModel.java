package com.omgservers.schema.model.message;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonDeserialize(using = MessageDeserializer.class)
public class MessageModel {

    @NotNull
    Long id;

    @NotNull
    MessageQualifierEnum qualifier;

    @NotNull
    @ToString.Exclude
    MessageBodyDto body;
}
