package com.omgservers.dto.user;

import com.omgservers.model.message.MessageModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespondClientRequest {

    @NotNull
    Long userId;

    @NotNull
    Long clientId;

    @NotNull
    MessageModel message;
}
