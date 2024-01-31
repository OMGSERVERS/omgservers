package com.omgservers.worker.module.handler.lua.component.luaContext;

import org.luaj.vm2.LuaValue;

import java.util.Map;
import java.util.Optional;

public class LuaContext {

    final Map<Long, LuaValue> attributesByClientId;
    final Map<Long, LuaValue> profileByClientId;

    public LuaContext(final Map<Long, LuaValue> attributesByClientId,
                      final Map<Long, LuaValue> profileByClientId) {
        this.attributesByClientId = attributesByClientId;
        this.profileByClientId = profileByClientId;
    }

    public Optional<LuaValue> getAttributes(final Long clientId) {
        return Optional.ofNullable(attributesByClientId.get(clientId));
    }

    public void updateAttributes(final Long clientId, final LuaValue luaAttributes) {
        attributesByClientId.put(clientId, luaAttributes);
    }

    public Optional<LuaValue> getProfile(final Long clientId) {
        return Optional.ofNullable(profileByClientId.get(clientId));
    }

    public void updateProfile(final Long clientId, final LuaValue luaProfile) {
        attributesByClientId.put(clientId, luaProfile);
    }
}
