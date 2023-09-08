package com.omgservers.module.context.impl.service.contextService.impl.cache;

import com.omgservers.base.cache.InMemoryCache;
import com.omgservers.module.context.impl.operation.createLuaInstance.impl.LuaInstance;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class LuaInstanceCache extends InMemoryCache<Long, LuaInstance> {
}
