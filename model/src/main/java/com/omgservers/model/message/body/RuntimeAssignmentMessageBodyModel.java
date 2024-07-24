package com.omgservers.model.message.body;

import com.omgservers.model.message.MessageBodyModel;
import com.omgservers.model.runtime.RuntimeConfigModel;
import com.omgservers.model.runtime.RuntimeQualifierEnum;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class RuntimeAssignmentMessageBodyModel extends MessageBodyModel {

    @NotNull
    Long runtimeId;

    @NotNull
    RuntimeQualifierEnum runtimeQualifier;

    @NotNull
    RuntimeConfigModel runtimeConfig;

    @NotNull
    String wsToken;
}
