package de.statistic.service.impl;

import de.statistic.dto.Statistic;
import de.statistic.dto.Transaction;
import de.statistic.service.CalculationService;
import org.junit.Assert;
import org.junit.Test;

import java.time.Instant;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

import static org.junit.Assert.assertEquals;

/**
 * Created by Sergey Gening on 26.03.18.
 */
public class CalculationServiceImplTest {

    @Test
    public void calculateStatisticDataTest() {
        final CalculationService calculationService = new CalculationServiceImpl();

        final Queue<Transaction> transactionQueue = new LinkedList<>();
        transactionQueue.add(new Transaction(15.95, Instant.now().toEpochMilli()));
        transactionQueue.add(new Transaction(3.75, Instant.now().toEpochMilli()));
        transactionQueue.add(new Transaction(87.23, Instant.now().toEpochMilli()));
        transactionQueue.add(new Transaction(98.56, Instant.now().toEpochMilli()));
        transactionQueue.add(new Transaction(43.9, Instant.now().toEpochMilli()));

        final Statistic statistic = calculationService.calculateStatisticData(transactionQueue);
        assertEquals(Integer.valueOf(5), statistic.getCount());
        assertEquals(Double.valueOf(249.39), statistic.getSum());
        assertEquals(Double.valueOf(49.878), statistic.getAvg());
        assertEquals(Double.valueOf(98.56), statistic.getMax());
        assertEquals(Double.valueOf(3.75), statistic.getMin());
    }
}