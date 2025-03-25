package com.omgservers.schema.message.body;

import com.omgservers.schema.message.MessageBodyDto;
import com.omgservers.schema.message.MessageQualifierEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class StopMatchmakingMessageBodyDto extends MessageBodyDto {

    @Override
    public MessageQualifierEnum getQualifier() {
        return MessageQualifierEnum.STOP_MATCHMAKING;
    }
}
