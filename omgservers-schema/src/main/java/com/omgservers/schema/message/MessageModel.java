package com.omgservers.schema.message;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.Valid;
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
    MessageQualifierEnum qualifier;

    @Valid
    @NotNull
    @ToString.Exclude
    MessageBodyDto body;
}
