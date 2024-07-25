package com.omgservers.model.message.body;

import com.omgservers.model.message.MessageBodyModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ConnectionUpgradeMessageBodyModel extends MessageBodyModel {

    @NotNull
    Long clientId;

    @NotNull
    ConnectionUpgradeQualifierEnum protocol;

    /**
     * Field has value if websocket upgrade takes place.
     */
    WebSocketConfig webSocketConfig;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class WebSocketConfig {
        String wsToken;
    }
}
