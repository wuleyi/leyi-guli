package org.guli.common.util;

import org.springframework.util.StringUtils;

import java.util.function.Consumer;

public class LeyiFunctions<T> {

    private T source;

    public LeyiFunctions(T source) {
        this.source = source;
    }

    public LeyiFunctions consumer(Consumer<T> consumer) {
        consumer.accept(source);
        return this;
    }

    public LeyiFunctions consumerPlus(Boolean flag, Consumer<T> consumer) {
        if (flag) consumer(consumer);
        return this;
    }

    public <E> LeyiFunctions consumerStrPlus(E e, Consumer<T> consumer) {
        consumerPlus(!StringUtils.isEmpty(e), consumer);
        return this;
    }



}
