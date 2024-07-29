package com.omgservers.schema.model.message.body;

import com.omgservers.schema.model.message.MessageBodyModel;
import com.omgservers.schema.model.runtime.RuntimeConfigModel;
import com.omgservers.schema.model.runtime.RuntimeQualifierEnum;
import com.omgservers.schema.model.message.MessageBodyModel;
import com.omgservers.schema.model.runtime.RuntimeConfigModel;
import com.omgservers.schema.model.runtime.RuntimeQualifierEnum;
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
}
