package com.omgservers.schema.message.body;

import com.omgservers.schema.message.MessageBodyDto;
import com.omgservers.schema.message.MessageQualifierEnum;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SetProfileMessageBodyDto extends MessageBodyDto {

    @NotNull
    Long clientId;

    @NotNull
    @ToString.Exclude
    Object profile;

    @Override
    public MessageQualifierEnum getQualifier() {
        return MessageQualifierEnum.SET_PROFILE;
    }
}
