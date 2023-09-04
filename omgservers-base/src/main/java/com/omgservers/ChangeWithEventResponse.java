package com.omgservers;

import com.omgservers.model.event.EventModel;
import com.omgservers.model.log.LogModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangeWithEventResponse {

    Boolean result;
    ExtendedResponse extendedResponse;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ExtendedResponse {
        LogModel changeLog;
        EventModel insertedEvent;
    }
}
