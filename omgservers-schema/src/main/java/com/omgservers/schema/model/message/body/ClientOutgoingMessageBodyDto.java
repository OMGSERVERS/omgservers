package com.omgservers.schema.model.message.body;

import com.omgservers.schema.model.message.MessageBodyDto;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ClientOutgoingMessageBodyDto extends MessageBodyDto {

    @NotNull
    Object data;
}
