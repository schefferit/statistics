package de.statistic.service.impl;

import de.statistic.dto.Statistic;
import de.statistic.dto.Transaction;
import de.statistic.service.StatisticService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.junit.Assert.*;

/**
 * Created by Sergey Gening on 26.03.18.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class StatisticServiceImplTest {

    @Autowired
    private StatisticService statisticService;

    @Test
    public void ignoreOldTransactionTest() {
        final Transaction transaction = new Transaction();
        transaction.setAmount(12.3);
        transaction.setTimestamp(1478192204000L);

        final boolean result = statisticService.addTransactionAsync(transaction);
        assertFalse(result);
    }

    @Test
    public void addTransactionsTest() {
        final Transaction transaction = new Transaction();
        transaction.setAmount(12.3);
        transaction.setTimestamp(Instant.now().minus(1, ChronoUnit.SECONDS).toEpochMilli());

        final Transaction transaction2 = new Transaction();
        transaction2.setAmount(15.0);
        transaction2.setTimestamp(Instant.now().toEpochMilli());

        final Transaction transaction3 = new Transaction();
        transaction3.setAmount(8.7);
        transaction3.setTimestamp(Instant.now().minus(4, ChronoUnit.SECONDS).toEpochMilli());

        assertTrue(statisticService.addTransactionAsync(transaction));
        assertTrue(statisticService.addTransactionAsync(transaction2));
        assertTrue(statisticService.addTransactionAsync(transaction3));
    }

    @Test
    public void successWorkflowTest() {
        final Transaction transaction = new Transaction();
        transaction.setAmount(3.61);
        transaction.setTimestamp(Instant.now().minus(1, ChronoUnit.SECONDS).toEpochMilli());

        final Transaction transaction2 = new Transaction();
        transaction2.setAmount(185.0);
        transaction2.setTimestamp(Instant.now().toEpochMilli());

        final Transaction transaction3 = new Transaction();
        transaction3.setAmount(9.7);
        transaction3.setTimestamp(Instant.now().minus(3, ChronoUnit.SECONDS).toEpochMilli());

        final Transaction transaction4 = new Transaction();
        transaction4.setAmount(12.4);
        transaction4.setTimestamp(Instant.now().minus(2, ChronoUnit.SECONDS).toEpochMilli());

        final Transaction transaction5 = new Transaction();
        transaction5.setAmount(28.55);
        transaction5.setTimestamp(Instant.now().minus(4, ChronoUnit.SECONDS).toEpochMilli());

        assertTrue(statisticService.addTransactionAsync(transaction));
        assertTrue(statisticService.addTransactionAsync(transaction2));
        assertTrue(statisticService.addTransactionAsync(transaction3));
        assertTrue(statisticService.addTransactionAsync(transaction4));
        assertTrue(statisticService.addTransactionAsync(transaction5));

        final Statistic statistic = statisticService.getStatistic();
        assertEquals(Integer.valueOf(5), statistic.getCount());
        assertEquals(Double.valueOf(239.26), statistic.getSum());
        assertEquals(Double.valueOf(47.852), statistic.getAvg());
        assertEquals(Double.valueOf(185.0), statistic.getMax());
        assertEquals(Double.valueOf(3.61), statistic.getMin());;
    }
}