package com.kostylenko.common.common_mapper.domain.mapper;

import com.kostylenko.common.common_mapper.domain.converter.BaseConverter;
import com.kostylenko.common.common_mapper.domain.converter.ConverterScope;
import com.kostylenko.common.common_mapper.domain.converter.TestObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static java.util.List.of;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DefaultMapperTest {

    private Mapper mapper;
    private TestObject testObject;
    private ConverterWithConverterScope converterWithScope;

    @BeforeEach
    void init() {
        converterWithScope = spy(new ConverterWithConverterScope());
        BaseConverter testConverter = new ConverterWithoutConverterScope();
        mapper = new DefaultMapper(of(testConverter, converterWithScope));
        testObject = new TestObject();
        testObject.setTestField2(1);
        testObject.setTestField(2);
    }

    @Test
    void testMapByObject() {
        TestObject testObject = new TestObject();
        TestObject map = mapper.map(this.testObject, testObject);
        assertAll(() -> assertEquals(this.testObject, map),
                () -> assertNotSame(this.testObject, map)
        );
    }

    @Test
    void testMapByClass() {
        TestObject map = mapper.map(this.testObject, TestObject.class);
        assertAll(() -> assertEquals(this.testObject, map),
                () -> assertNotSame(this.testObject, map)
        );
    }

    @SuppressWarnings("unchecked")
    @Test
    void testMapByObjectWithScope() {
        TestObject testObject = new TestObject();
        TestObject map = mapper.map(this.testObject, testObject, "test");
        assertAll(() -> assertEquals(this.testObject, map),
                () -> assertNotSame(this.testObject, map),
                () -> verify(converterWithScope, times(1)).convert(this.testObject, testObject)
        );
    }

    @Test
    void testMapByClassWithScope() {
        TestObject map = mapper.map(this.testObject, TestObject.class, "test");
        assertAll(() -> assertEquals(this.testObject, map),
                () -> assertNotSame(this.testObject, map),
                () -> verify(converterWithScope, times(1)).convert(eq(this.testObject), any())
        );
    }

    @ConverterScope("test")
    public class ConverterWithConverterScope extends BaseConverter<TestObject, TestObject> {

        @Override
        public TestObject convert(TestObject from, TestObject to) {
            to.setTestField2(from.getTestField2());
            to.setTestField(from.getTestField());
            return to;
        }
    }

    public class ConverterWithoutConverterScope extends BaseConverter<TestObject, TestObject> {

        @Override
        public TestObject convert(TestObject from, TestObject to) {
            to.setTestField2(from.getTestField2());
            to.setTestField(from.getTestField());
            return to;
        }
    }

}
