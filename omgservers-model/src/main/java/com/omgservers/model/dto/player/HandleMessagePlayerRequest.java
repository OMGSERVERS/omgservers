package com.omgservers.model.dto.player;

import com.omgservers.model.message.MessageModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HandleMessagePlayerRequest {

    @NotNull
    Long clientId;

    @NotNull
    MessageModel message;
}
