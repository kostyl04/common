package com.kostylenko.common.common_mapper.domain.converter;

import java.util.stream.Stream;

public class StringToEnumConverter {

    public static <EC extends Enum> EC convertToEnum(String from, Class<EC> to) {
        return Stream.of(to.getEnumConstants())
                .filter(enumConstant -> enumConstant.name().equalsIgnoreCase(from))
                .findFirst()
                .orElse(null);
    }
}
