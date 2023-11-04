package com.omgservers.model.dto.gateway;

import com.omgservers.model.message.MessageModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URI;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespondMessageRequest {

    @NotNull
    URI server;

    @NotNull
    Long connectionId;

    @NotNull
    MessageModel message;
}
