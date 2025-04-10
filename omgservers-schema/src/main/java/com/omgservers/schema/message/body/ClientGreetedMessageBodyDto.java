package com.omgservers.schema.message.body;

import com.omgservers.schema.message.MessageBodyDto;
import com.omgservers.schema.message.MessageQualifierEnum;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ClientGreetedMessageBodyDto extends MessageBodyDto {

    @NotNull
    Long tenantId;

    @NotNull
    Long versionId;

    @NotNull
    Instant versionCreated;

    @Override
    public MessageQualifierEnum getQualifier() {
        return MessageQualifierEnum.CLIENT_GREETED;
    }
}
