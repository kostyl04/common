package com.kostylenko.common.common_mapper.domain.converter;

import lombok.Getter;
import lombok.Setter;

import static java.util.Objects.isNull;

@Getter
@Setter
public final class Key<F, T> {

    private String scope;
    private Class<F> from;
    private Class<T> to;

    Key(Class<F> from, Class<T> to) {
        this(null, from, to);
    }

    Key(String scope, Class<F> from, Class<T> to) {
        this.scope = scope;
        this.from = from;
        this.to = to;
    }

    public boolean accepts(Class from, Class to, String scope) {
        boolean fromEquals = this.from.equals(from);
        boolean toEquals = this.to.equals(to);
        if (fromEquals && toEquals) {
            if (isNull(scope)) {
                return isNull(this.scope);
            } else {
                return scope.equals(this.scope);
            }
        }
        return false;
    }
}
