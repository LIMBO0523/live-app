package org.live.web.starter.context;

import org.live.web.starter.constants.RequestConstants;

import java.util.Map;
import java.util.HashMap;

/**
 * @Author LIMBO0523
 * @Date 2024/10/3 15:22
 */
public class RequestContext {
    private static final ThreadLocal<Map<Object, Object>> resources = new InheritableThreadLocalMap<>();

    public static void put(Object key, Object value) {
        if (key == null) {
            throw new IllegalArgumentException("key cannot be null");
        }
        if (value == null) {
            resources.get().remove(key);
            return;
        }
        resources.get().put(key, value);
    }

    public static Long getUserId() {
        Object result = get(RequestConstants.USER_ID);
        return result == null ? null : Long.valueOf(String.valueOf(result));
    }

    private static Object get(Object key) {
        if (key == null) {
            throw new IllegalArgumentException("key cannot be null");
        }
        return resources.get().get(key);
    }

    public static void clear() {
        resources.remove();
    }

    private static final class InheritableThreadLocalMap<T extends Map<Object, Object>> extends InheritableThreadLocal<Map<Object, Object>> {

        @Override
        protected Map<Object, Object> initialValue() {
            return new HashMap();
        }

        @Override
        protected Map<Object, Object> childValue(Map<Object, Object> parentValue) {
            if (parentValue != null) {
                return (Map<Object, Object>) ((HashMap<Object, Object>) parentValue).clone();
            } else {
                return null;
            }
        }
    }
}
