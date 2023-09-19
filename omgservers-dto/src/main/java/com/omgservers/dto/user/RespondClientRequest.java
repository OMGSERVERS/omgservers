package com.omgservers.dto.user;

import com.omgservers.model.message.MessageModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
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
