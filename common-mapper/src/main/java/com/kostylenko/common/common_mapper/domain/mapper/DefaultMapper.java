package com.kostylenko.common.common_mapper.domain.mapper;

import com.kostylenko.common.common_mapper.domain.converter.BaseConverter;
import com.kostylenko.common.common_mapper.domain.exception.ConverterException;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

import static java.lang.String.format;
import static java.util.Objects.isNull;

@SuppressWarnings("unchecked")
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
        BaseConverter converter = findConverter(getCollectionType(from), to, null);
        List<T> result = new ArrayList<>();
        addConvertedObjects(converter, from, result);
        return result;
    }

    @Override
    public <F, T> List<T> mapToList(Collection<F> from, Class<T> to, String scope) {
        if (isNull(to) || isNull(from)) {
            return null;
        }
        BaseConverter converter = findConverter(getCollectionType(from), to, scope);
        List<T> result = new ArrayList<>();
        addConvertedObjects(converter, from, result);
        return result;
    }

    @Override
    public <F, T> Set<T> mapToSet(Collection<F> from, Class<T> to) {
        if (isNull(to) || isNull(from)) {
            return null;
        }
        BaseConverter converter = findConverter(getCollectionType(from), to, null);
        Set<T> result = new HashSet<>();
        addConvertedObjects(converter, from, result);
        return result;
    }

    @Override
    public <F, T> Set<T> mapToSet(Collection<F> from, Class<T> to, String scope) {
        if (isNull(to) || isNull(from)) {
            return null;
        }
        BaseConverter converter = findConverter(getCollectionType(from), to, scope);
        Set<T> result = new HashSet<>();
        addConvertedObjects(converter, from, result);
        return result;
    }

    private <F, T> void addConvertedObjects(BaseConverter<F, T> converter,
                                            Collection<F> from,
                                            Collection<T> to) {
        from.forEach(fromItem -> to.add(converter.convert(fromItem)));
    }

    private <F> Class<F> getCollectionType(Collection<F> collection) {
        Type[] genericTypes = ((ParameterizedType) collection.getClass()
                .getGenericSuperclass())
                .getActualTypeArguments();
        return (Class<F>) genericTypes[0];
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
}
