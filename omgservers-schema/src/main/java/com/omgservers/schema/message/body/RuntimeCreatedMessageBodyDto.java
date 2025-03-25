package com.omgservers.schema.message.body;

import com.omgservers.schema.message.MessageBodyDto;
import com.omgservers.schema.message.MessageQualifierEnum;
import com.omgservers.schema.model.runtime.RuntimeConfigDto;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class RuntimeCreatedMessageBodyDto extends MessageBodyDto {

    @NotNull
    RuntimeConfigDto runtimeConfig;

    @Override
    public MessageQualifierEnum getQualifier() {
        return MessageQualifierEnum.RUNTIME_CREATED;
    }
}
