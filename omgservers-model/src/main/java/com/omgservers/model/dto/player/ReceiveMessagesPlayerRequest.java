package com.omgservers.model.dto.player;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReceiveMessagesPlayerRequest {

    @NotNull
    Long clientId;

    @NotNull
    List<Long> consumedMessages;
}
