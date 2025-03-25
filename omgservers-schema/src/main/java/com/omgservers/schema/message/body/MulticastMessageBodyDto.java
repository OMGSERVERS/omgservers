package com.omgservers.schema.message.body;

import com.omgservers.schema.message.MessageBodyDto;
import com.omgservers.schema.message.MessageQualifierEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class MulticastMessageBodyDto extends MessageBodyDto {

    @NotBlank
    List<Long> clients;

    @NotNull
    @ToString.Exclude
    Object message;

    @Override
    public MessageQualifierEnum getQualifier() {
        return MessageQualifierEnum.MULTICAST_MESSAGE;
    }
}
