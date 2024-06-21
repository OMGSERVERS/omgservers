package com.omgservers.model.dto.player;

import com.omgservers.model.message.MessageModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InterchangePlayerRequest {

    @NotNull
    Long clientId;

    @NotNull
    List<MessageModel> outgoingMessages;

    @NotNull
    List<Long> consumedMessages;
}
