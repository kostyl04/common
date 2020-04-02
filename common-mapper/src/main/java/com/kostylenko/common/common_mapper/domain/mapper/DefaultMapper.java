package com.kostylenko.common.common_mapper.domain.mapper;

import com.kostylenko.common.common_mapper.domain.converter.BaseConverter;
import com.kostylenko.common.common_mapper.domain.exception.ConverterException;

import java.util.*;

import static java.lang.String.format;
import static java.util.Objects.isNull;
import static org.apache.commons.lang3.ObjectUtils.isEmpty;

@SuppressWarnings({"unchecked", "Duplicates"})
public class DefaultMapper implements Mapper {

    private List<BaseConverter> converters;

    public DefaultMapper(List<BaseConverter> converters) {
        this.converters = converters;
        setMapper();
    }

    private void setMapper() {
        getConverters().forEach(converter -> converter.setMapper(this));
    }

    @Override
    public <F, T> T map(F from, T to) {
        if (isNull(to) || isNull(from)) {
            return null;
        }
        return (T) findConverter(from.getClass(), to.getClass(), null).convert(from, to);
    }

    @Override
    public <F, T> T map(F from, Class<T> to) {
        if (isNull(to) || isNull(from)) {
            return null;
        }
        return (T) findConverter(from.getClass(), to, null).convert(from);
    }

    @Override
    public <F, T> T map(F from, T to, String scope) {
        if (isNull(to) || isNull(from)) {
            return null;
        }
        return (T) findConverter(from.getClass(), to.getClass(), scope).convert(from, to);
    }

    @Override
    public <F, T> T map(F from, Class<T> to, String scope) {
        if (isNull(to) || isNull(from)) {
            return null;
        }
        return (T) findConverter(from.getClass(), to, scope).convert(from);
    }

    @Override
    public <F, T> List<T> mapToList(Collection<F> from, Class<T> to) {
        if (isNull(to) || isNull(from)) {
            return null;
        }
        if (isEmpty(from)) {
            return new ArrayList<>();
        }
        return (List<T>) mapCollection(from, to, new ArrayList<>(), null);
    }

    @Override
    public <F, T> List<T> mapToList(Collection<F> from, Class<T> to, String scope) {
        if (isNull(to) || isNull(from)) {
            return null;
        }
        if (isEmpty(from)) {
            return new ArrayList<>();
        }
        return (List<T>) mapCollection(from, to, new ArrayList<>(), scope);
    }

    @Override
    public <F, T> Set<T> mapToSet(Collection<F> from, Class<T> to) {
        if (isNull(to) || isNull(from)) {
            return null;
        }
        if (isEmpty(from)) {
            return new HashSet<>();
        }
        return (Set<T>) mapCollection(from, to, new HashSet<>(), null);
    }

    @Override
    public <F, T> Set<T> mapToSet(Collection<F> from, Class<T> to, String scope) {
        if (isNull(to) || isNull(from)) {
            return null;
        }
        if (isEmpty(from)) {
            return new HashSet<>();
        }
        return (Set<T>) mapCollection(from, to, new HashSet<>(), scope);
    }

    private List<BaseConverter> getConverters() {
        if (isNull(converters)) {
            converters = new ArrayList<>();
        }
        return converters;
    }

    private BaseConverter findConverter(Class from, Class to, String scope) {
        return getConverters().stream()
                .filter(converter -> converter.getKey().accepts(from, to, scope))
                .findFirst()
                .orElseThrow(
                        () -> new ConverterException(format("Converter not found! from: %s, to: %s",
                                from.getName(),
                                to.getName()))
                );
    }

    private <F, T> Collection<T> mapCollection(Collection<F> from, Class<T> to, Collection<T> result, String scope) {
        from.forEach(fromItem -> {
            BaseConverter converter = findConverter(fromItem.getClass(), to, scope);
            result.add((T) converter.convert(fromItem));
        });
        return result;
    }
}
