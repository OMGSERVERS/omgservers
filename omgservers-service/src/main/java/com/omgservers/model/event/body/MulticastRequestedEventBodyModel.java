package com.omgservers.model.event.body;

import com.omgservers.model.event.EventBodyModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.recipient.Recipient;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class MulticastRequestedEventBodyModel extends EventBodyModel {

    @NotNull
    Long runtimeId;

    @NotEmpty
    List<Recipient> recipients;

    @NotNull
    Object message;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.MULTICAST_REQUESTED;
    }

    @Override
    public Long getGroupId() {
        return runtimeId;
    }
}
