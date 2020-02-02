package com.kostylenko.common.common_mapper.domain.converter;

public class TestConverter2 extends BaseConverter<TestObjectWithoutEmptyConstructor, TestObjectWithoutEmptyConstructor> {

    @Override
    public TestObjectWithoutEmptyConstructor convert(TestObjectWithoutEmptyConstructor from, TestObjectWithoutEmptyConstructor to) {
        to.setTestField(from.getTestField());
        to.setTestField2(from.getTestField2());
        return to;
    }
}
