package com.omgservers.module.script.impl.event.runtime;

import com.omgservers.module.script.impl.event.LuaEvent;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class DeleteClientLuaEvent extends LuaEvent {

    final Long clientId;

    public DeleteClientLuaEvent(final Long clientId) {
        super("delete_client");
        this.clientId = clientId;

        set("client_id", clientId.toString());
    }
}
