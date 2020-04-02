package com.kostylenko.common.common_mapper.domain.converter;

import com.kostylenko.common.common_mapper.domain.exception.ConverterException;
import com.kostylenko.common.common_mapper.domain.mapper.Mapper;
import lombok.Getter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

@Getter
public abstract class BaseConverter<F, T> {

    private Key key;
    protected Mapper mapper;

    public BaseConverter() {
        initKey();
    }

    @SuppressWarnings("unchecked")
    public T convert(F from) {
        try {
            T to = (T) key.getTo().getConstructor().newInstance();
            return convert(from, to);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            return null;
        }
    }

    public abstract T convert(F from, T to);

    public void setMapper(Mapper mapper) {
        this.mapper = mapper;
    }

    @SuppressWarnings("unchecked")
    private void initKey() {
        Type[] genericTypes = ((ParameterizedType) getClass()
                .getGenericSuperclass())
                .getActualTypeArguments();
        Class<F> from = (Class<F>) genericTypes[0];
        Class<T> to = (Class<T>) genericTypes[1];
        ConverterScope[] converterScopes = getClass().getAnnotationsByType(ConverterScope.class);
        String context = null;
        if (converterScopes.length != 0) {
            context = converterScopes[0].value();
        }
        try {
            to.getConstructor();
        } catch (NoSuchMethodException e) {
            throw new ConverterException("'To' object must have default constructor!");
        }
        key = new Key(context, from, to);
    }
}