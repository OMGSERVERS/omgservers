package com.omgservers.schema.message.body;

import com.omgservers.schema.message.MessageBodyDto;
import com.omgservers.schema.message.MessageQualifierEnum;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.net.URI;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ConnectionUpgradedMessageBodyDto extends MessageBodyDto {

    @NotNull
    ConnectionUpgradeQualifierEnum protocol;

    DispatcherConfig dispatcherConfig;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DispatcherConfig {
        URI connectionUrl;

        @ToString.Exclude
        String secWebSocketProtocol;
    }

    @Override
    public MessageQualifierEnum getQualifier() {
        return MessageQualifierEnum.CONNECTION_UPGRADED;
    }
}
