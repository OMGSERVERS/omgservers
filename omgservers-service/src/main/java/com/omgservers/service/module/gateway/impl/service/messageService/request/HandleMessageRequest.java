package com.omgservers.service.module.gateway.impl.service.messageService.request;

import com.omgservers.model.message.MessageModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HandleMessageRequest {

    @NotNull
    Long connectionId;

    @NotNull
    MessageModel message;
}
