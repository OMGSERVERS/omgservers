package com.omgservers.worker.module.handler.lua.configuration;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;

import java.io.IOException;

public class LuaValueSerializer extends StdSerializer<LuaValue> {

    LuaValueSerializer(Class<LuaValue> clazz) {
        super(clazz);
    }

    @Override
    public void serialize(final LuaValue luaValue,
                          final JsonGenerator jsonGenerator,
                          final SerializerProvider serializerProvider) throws IOException {
        encode(jsonGenerator, luaValue);
    }

    void encode(JsonGenerator jsonGenerator, LuaValue luaValue) throws IOException {
        switch (luaValue.type()) {
            case LuaValue.TBOOLEAN:
                jsonGenerator.writeBoolean(luaValue.checkboolean());
                break;
            case LuaValue.TNUMBER:
                if (luaValue.isint()) {
                    jsonGenerator.writeNumber(luaValue.checklong());
                } else {
                    jsonGenerator.writeNumber(luaValue.checkdouble());
                }
                break;
            case LuaValue.TSTRING:
                jsonGenerator.writeString(luaValue.checkjstring());
                break;
            case LuaValue.TTABLE:
                LuaTable table = luaValue.checktable();
                if (isArray(table)) {
                    // Array
                    jsonGenerator.writeStartArray();
                    LuaValue k = LuaValue.NIL;
                    while (true) {
                        Varargs item = luaValue.next(k);
                        if ((k = item.arg1()).isnil()) {
                            break;
                        }
                        LuaValue v = item.arg(2);
                        encode(jsonGenerator, v);
                    }
                    jsonGenerator.writeEndArray();
                } else {
                    // Map
                    jsonGenerator.writeStartObject();
                    LuaValue k = LuaValue.NIL;
                    while (true) {
                        Varargs item = luaValue.next(k);
                        if ((k = item.arg1()).isnil()) {
                            break;
                        }
                        LuaValue v = item.arg(2);
                        jsonGenerator.writeFieldName(k.checkjstring());
                        encode(jsonGenerator, v);
                    }
                    jsonGenerator.writeEndObject();
                }
                break;
            case LuaValue.TNIL:
                jsonGenerator.writeNull();
                break;
            default:
                throw new IllegalArgumentException("Unknown lua type, type=" + luaValue.type());
        }
    }

    private boolean isArray(LuaValue luaValue) {
        int expected = 1;
        LuaValue k = LuaValue.NIL;
        while (true) {
            Varargs item = luaValue.next(k);
            k = item.arg1();
            if (k.isnil()) {
                break;
            }
            if (k.isint() && k.checkint() == expected) {
                expected = expected + 1;
            } else {
                return false;
            }
        }
        return true;
    }
}
