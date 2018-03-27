package de.statistic.service.impl;

import de.statistic.dto.Statistic;
import de.statistic.dto.Transaction;
import de.statistic.service.CalculationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Queue;

/**
 * Created by Sergey Gening on 26.03.18.
 */
@Service
public class CalculationServiceImpl implements CalculationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CalculationServiceImpl.class);

    @Override
    public Statistic calculateStatisticData(final Queue<Transaction> transactionQueue) {
        //In case when transactions absent, return initial data
        if (transactionQueue.isEmpty()) {
            LOGGER.debug("There is no transactions in queue, return default value");
            return createInitialStatisticData();
        }
        final Statistic statistic = createInitialStatisticData();
        BigDecimal sum = new BigDecimal(0);
        for (final Transaction transaction : transactionQueue) {
            final Double amount = transaction.getAmount();
            if (statistic.getMin() == 0 || amount < statistic.getMin()) {
                statistic.setMin(amount);
            }
            if (amount > statistic.getMax()) {
                statistic.setMax(amount);
            }
            sum = sum.add(new BigDecimal(String.valueOf(amount)));
        }
        statistic.setSum(sum.doubleValue());
        statistic.setCount(transactionQueue.size());
        statistic.setAvg(sum.divide(new BigDecimal(transactionQueue.size()), 10, RoundingMode.HALF_UP).doubleValue());
        return statistic;
    }

    private Statistic createInitialStatisticData() {
        final Statistic defaultStatistic = new Statistic();
        defaultStatistic.setSum(0.0);
        defaultStatistic.setMax(0.0);
        defaultStatistic.setMin(0.0);
        defaultStatistic.setAvg(0.0);
        defaultStatistic.setCount(0);
        return defaultStatistic;
    }
}
