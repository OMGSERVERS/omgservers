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
public class ClientDeletedMessageBodyDto extends MessageBodyDto {

    @NotNull
    DisconnectionReasonEnum reason;

    @Override
    public MessageQualifierEnum getQualifier() {
        return MessageQualifierEnum.CLIENT_DELETED;
    }
}
