package de.statistic.service;

import de.statistic.dto.Statistic;
import de.statistic.dto.Transaction;

import java.util.Queue;

/**
 * Created by Sergey Gening on 26.03.18.
 */
public interface CalculationService {

    /**
     * Calculates {@link Statistic} data:
     *   sum - total sum of transaction value,
     *   avg - average amount of transaction value
     *   min - lowest transaction value
     *   max - highest transaction value
     *   count - total number of transactions happened for specified time
     *
     * @param transactionQueue {@link Queue<Transaction>} transactions queue
     * @return {@link Statistic} data
     */
    Statistic calculateStatisticData(final Queue<Transaction> transactionQueue);
}
