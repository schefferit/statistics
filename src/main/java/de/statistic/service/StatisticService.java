package de.statistic.service;

import de.statistic.dto.Statistic;
import de.statistic.dto.Transaction;

/**
 * Created by Sergey Gening on 26.03.18.
 */
public interface StatisticService {

    /**
     * Add asynchronously (in new thread) transaction to the queue
     * in constant time O(1)
     *
     * @param transaction {@link Transaction}
     * @return boolean false if transaction is older then threshold and will not be added to the queue, otherwise true
     */
    boolean addTransactionAsync(final Transaction transaction);

    /**
     * Get calculated statistic data for current moment
     * in constant time O(1)
     *
     * @return {@link Statistic} data
     */
    Statistic getStatistic();
}
