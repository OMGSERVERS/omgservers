package com.omgservers.schema.message.body;

import com.omgservers.schema.message.MessageBodyDto;
import com.omgservers.schema.message.MessageQualifierEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class RequestMatchmakingMessageBodyDto extends MessageBodyDto {

    @NotNull
    Long clientId;

    @NotBlank
    @Size(max = 64)
    String mode;

    @Override
    public MessageQualifierEnum getQualifier() {
        return MessageQualifierEnum.REQUEST_MATCHMAKING;
    }
}
