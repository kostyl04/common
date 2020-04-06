package com.kostylenko.common.common_mapper.domain.mapper;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface Mapper {

    <F, T> T map(F from, T to);

    <F, T> T map(F from, Class<T> to);

    <F, T> T map(F from, T to, String scope);

    <F, T> T map(F from, Class<T> to, String scope);

    <T extends Enum<T>> T map(String from, Class<T> enumClazz);

    <F, T> List<T> mapToList(Collection<F> from, Class<T> to);

    <F, T> List<T> mapToList(Collection<F> from, Class<T> to, String scope);

    <F, T> Set<T> mapToSet(Collection<F> from, Class<T> to);

    <F, T> Set<T> mapToSet(Collection<F> from, Class<T> to, String scope);
}