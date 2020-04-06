package com.kostylenko.common.common_mapper.domain.converter;

import java.util.stream.Stream;

public class EnumConverterUtils {

    public static <EC extends Enum> EC convertStringToEnum(String from, Class<EC> to) {
        return Stream.of(to.getEnumConstants())
                .filter(enumConstant -> enumConstant.name().equalsIgnoreCase(from))
                .findFirst()
                .orElse(null);
    }

    public static <F extends Enum<F>, EC extends Enum> EC convertEnumToEnum(Enum<F> from, Class<EC> to) {
        return Stream.of(to.getEnumConstants())
                .filter(enumConstant -> enumConstant.name().equalsIgnoreCase(from.name()))
                .findFirst()
                .orElse(null);
    }
}
