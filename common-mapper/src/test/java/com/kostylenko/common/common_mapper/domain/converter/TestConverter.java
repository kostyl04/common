package com.kostylenko.common.common_mapper.domain.converter;

@ConverterScope("test")
public class TestConverter extends BaseConverter<TestObject, TestObject> {

    @Override
    public TestObject convert(TestObject from, TestObject to) {
        to.setTestField(from.getTestField());
        to.setTestField2(from.getTestField2());
        return to;
    }
}
