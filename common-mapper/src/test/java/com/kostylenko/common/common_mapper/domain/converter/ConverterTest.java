package com.kostylenko.common.common_mapper.domain.converter;

import com.kostylenko.common.common_mapper.domain.exception.ConverterException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ConverterTest {

    private TestObject testObject;
    private TestConverter testConverter;

    @BeforeAll
    void init() {
        testConverter = new TestConverter();
        testObject = new TestObject();
        testObject.setTestField(1);
        testObject.setTestField2(2);
    }

    @Test
    void shouldReturnEqualsObjectButNotSame_ifObjectHasEmptyConstructor() {
        TestObject convert = testConverter.convert(testObject);
        assertAll(() -> assertEquals(convert, testObject),
                () -> assertNotSame(convert, testObject)
        );
    }

    @Test
    void converterKeyShouldBeWithScope_ifConverterScopeAnnotationPresent() {
        assertNotNull(testConverter.getKey().getScope());
    }

    @Test
    void shouldReturnEqualsObjectButNotSame_ifObjectHasEmptyConstructor2() {
        Exception exception = assertThrows(ConverterException.class, () -> {
            TestConverter2 testConverter2 = new TestConverter2();
        });
        assertEquals(exception.getMessage(), "'To' object must have default constructor!");
    }
}
