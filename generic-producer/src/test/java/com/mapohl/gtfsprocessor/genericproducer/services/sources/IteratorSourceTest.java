package com.mapohl.gtfsprocessor.genericproducer.services.sources;

import com.google.common.collect.Lists;
import com.mapohl.gtfsprocessor.genericproducer.utils.TimePeriod;
import com.mapohl.gtfsprocessor.test.domain.TestEntity;
import com.mapohl.gtfsprocessor.test.domain.TestEntityMapper;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;

import static com.mapohl.gtfsprocessor.test.utils.TestUtils.createEntity;
import static com.mapohl.gtfsprocessor.test.utils.TestUtils.createTimePeriod;
import static org.junit.jupiter.api.Assertions.*;

class IteratorSourceTest {

    @Test
    public void testIteration() {
        List<String> input = Lists.newArrayList("0", "12");
        IteratorSource<String, TestEntity> testInstance = new IteratorSource(input.iterator(), 1, new TestEntityMapper());

        TimePeriod timePeriod = createTimePeriod(0, 0, 1, 30);
        assertTrue(testInstance.hasNext());
        assertTrue(testInstance.hasNext(timePeriod));
        assertTrue(testInstance.upstreamHasNext());
        assertEquals(createEntity(0), testInstance.next(timePeriod));

        assertTrue(testInstance.hasNext());
        assertTrue(testInstance.hasNext(timePeriod));
        assertFalse(testInstance.upstreamHasNext());
        assertEquals(createEntity(1), testInstance.next(timePeriod));

        assertTrue(testInstance.hasNext());
        assertFalse(testInstance.hasNext(timePeriod));
        assertFalse(testInstance.upstreamHasNext());
        assertNull(testInstance.next(timePeriod));

        timePeriod = timePeriod.next();
        assertTrue(testInstance.hasNext());
        assertTrue(testInstance.hasNext(timePeriod));
        assertFalse(testInstance.upstreamHasNext());
        assertEquals(createEntity(2), testInstance.next(timePeriod));

        assertFalse(testInstance.hasNext());
        assertFalse(testInstance.hasNext(timePeriod));
        assertFalse(testInstance.upstreamHasNext());

        assertThrows(NoSuchElementException.class, () -> testInstance.next(createTimePeriod(1, 30, 3, 0)));
    }

}