package org.guli.common.util;

import org.guli.common.exception.GuliException;
import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;

import java.util.Collection;

public class Assert {

    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throw new GuliException(message);
        }
    }

    public static void notNull(@Nullable Object object, String message) {
        if (object == null) {
            throw new GuliException(message);
        }
    }

    public static void isNull(@Nullable Object object, String message) {
        if (object != null) {
            throw new GuliException(message);
        }
    }

    public static void notEmpty(@Nullable Collection<?> collection, String message) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new GuliException(message);
        }
    }

    public static void isEmpty(@Nullable Collection<?> collection, String message) {
        if (!CollectionUtils.isEmpty(collection)) {
            throw new GuliException(message);
        }
    }

}
