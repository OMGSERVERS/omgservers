package com.omgservers.model.event.body;

import com.omgservers.model.event.EventBodyModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.request.RequestModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class RequestDeletedEventBodyModel extends EventBodyModel {

    RequestModel request;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.REQUEST_DELETED;
    }

    @Override
    public Long getGroupId() {
        return request.getMatchmakerId();
    }
}
