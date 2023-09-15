package com.omgservers.model.message.body;

import com.omgservers.model.message.MessageBodyModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class EventMessageBodyModel extends MessageBodyModel {

    @NotNull
    Object event;
}
