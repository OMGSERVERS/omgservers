package com.omgservers.model.message.body;

import com.omgservers.model.message.MessageBodyModel;
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
public class MatchmakerMessageBodyModel extends MessageBodyModel {

    @NotBlank
    @Size(max = 64)
    String mode;
}
