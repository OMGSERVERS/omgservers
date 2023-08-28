package com.omgservers.module.context.impl.operation.createPlayerContext.impl;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.luaj.vm2.LuaTable;

@Slf4j
@Builder
public class LuaPlayerContext extends LuaTable {

    final LuaPlayerRespondFunction respondFunction;
    final LuaPlayerSetAttributeFunction setAttributeFunction;
    final LuaPlayerGetAttributeFunction getAttributeFunction;

    public LuaPlayerContext(final LuaPlayerRespondFunction respondFunction,
                            final LuaPlayerSetAttributeFunction setAttributeFunction,
                            final LuaPlayerGetAttributeFunction getAttributeFunction) {
        this.respondFunction = respondFunction;
        this.setAttributeFunction = setAttributeFunction;
        this.getAttributeFunction = getAttributeFunction;

        set("respond", respondFunction);
        set("set_attribute", setAttributeFunction);
        set("get_attribute", getAttributeFunction);
    }
}
