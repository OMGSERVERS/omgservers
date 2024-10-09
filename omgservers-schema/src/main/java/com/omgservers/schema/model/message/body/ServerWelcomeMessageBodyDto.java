package com.omgservers.schema.model.message.body;

import com.omgservers.schema.model.message.MessageBodyDto;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ServerWelcomeMessageBodyDto extends MessageBodyDto {

    @NotNull
    Long tenantId;

    @NotNull
    Long tenantVersionId;

    @NotNull
    Instant tenantVersionCreated;
}
