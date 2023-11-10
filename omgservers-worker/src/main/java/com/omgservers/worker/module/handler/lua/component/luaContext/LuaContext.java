package com.omgservers.worker.module.handler.lua.component.luaContext;

import org.luaj.vm2.LuaValue;

import java.util.Map;
import java.util.Optional;

public class LuaContext {

    final Map<Long, LuaValue> attributesByUserId;
    final Map<Long, LuaValue> profileByUserId;

    public LuaContext(final Map<Long, LuaValue> attributesByUserId,
                      final Map<Long, LuaValue> profileByUserId) {
        this.attributesByUserId = attributesByUserId;
        this.profileByUserId = profileByUserId;
    }

    public Optional<LuaValue> getAttributes(final Long userId) {
        return Optional.ofNullable(attributesByUserId.get(userId));
    }

    public void updateAttributes(final Long userId, final LuaValue luaAttributes) {
        attributesByUserId.put(userId, luaAttributes);
    }

    public Optional<LuaValue> getProfile(final Long userId) {
        return Optional.ofNullable(profileByUserId.get(userId));
    }

    public void updateProfile(final Long userId, final LuaValue luaProfile) {
        attributesByUserId.put(userId, luaProfile);
    }
}
