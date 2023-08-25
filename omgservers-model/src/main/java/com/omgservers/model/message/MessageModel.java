package com.omgservers.model.message;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonDeserialize(using = MessageDeserializer.class)
public class MessageModel {

    String id;
    MessageQualifierEnum qualifier;
    @ToString.Exclude
    MessageBodyModel body;
}
