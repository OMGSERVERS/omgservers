package com.omgservers.schema.message.body;

import com.omgservers.schema.message.MessageBodyDto;
import com.omgservers.schema.message.MessageQualifierEnum;
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
public class ConnectionUpgradedMessageBodyDto extends MessageBodyDto {

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

    @Override
    public MessageQualifierEnum getQualifier() {
        return MessageQualifierEnum.CONNECTION_UPGRADED;
    }
}
