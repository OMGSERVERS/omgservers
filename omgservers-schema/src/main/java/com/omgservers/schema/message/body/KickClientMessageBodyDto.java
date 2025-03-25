package com.omgservers.schema.message.body;

import com.omgservers.schema.message.MessageBodyDto;
import com.omgservers.schema.message.MessageQualifierEnum;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class KickClientMessageBodyDto extends MessageBodyDto {

    @NotNull
    Long clientId;

    @Override
    public MessageQualifierEnum getQualifier() {
        return MessageQualifierEnum.KICK_CLIENT;
    }
}
