package com.kostylenko.common.common_mapper.domain.converter;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class TestObjectWithoutEmptyConstructor {

    private int testField;
    private int testField2;

    public TestObjectWithoutEmptyConstructor(int testField, int testField2) {
        this.testField = testField;
        this.testField2 = testField2;
    }
}
