package com.omgservers.schema.model.message.body;

import com.omgservers.schema.model.message.MessageBodyDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class MatchmakerMessageBodyDto extends MessageBodyDto {

    @NotBlank
    @Size(max = 64)
    String mode;
}
