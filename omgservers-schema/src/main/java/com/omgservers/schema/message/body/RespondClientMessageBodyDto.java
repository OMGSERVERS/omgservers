package com.omgservers.schema.message.body;

import com.omgservers.schema.message.MessageBodyDto;
import com.omgservers.schema.message.MessageQualifierEnum;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class RespondClientMessageBodyDto extends MessageBodyDto {

    @NotNull
    Long clientId;

    @NotNull
    @ToString.Exclude
    Object message;

    @Override
    public MessageQualifierEnum getQualifier() {
        return MessageQualifierEnum.RESPOND_CLIENT;
    }
}
