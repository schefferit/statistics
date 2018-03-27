package de.statistic.service.impl;

import de.statistic.dto.Statistic;
import de.statistic.dto.Transaction;
import de.statistic.service.CalculationService;
import de.statistic.service.StatisticService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.Queue;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by Sergey Gening on 26.03.18.
 */
@Service
public class StatisticServiceImpl implements StatisticService {

    private static final Integer INITIAL_QUEUE_CAPACITY = 20;

    private static final Logger LOGGER = LoggerFactory.getLogger(StatisticServiceImpl.class);

    private final Queue<Transaction> transactionQueue;
    private final ScheduledExecutorService executorService;
    private final CalculationService calculationService;
    private final AtomicReference<Statistic> statisticData;

    @Value("${transaction.threshold:60}")
    private Long transactionThreshold;

    @Autowired
    public StatisticServiceImpl(final CalculationService calculationService) {
        this.transactionQueue = new PriorityBlockingQueue<>(INITIAL_QUEUE_CAPACITY, new InverseTransactionComparator());
        this.executorService = Executors.newSingleThreadScheduledExecutor();
        this.calculationService = calculationService;
        this.statisticData = new AtomicReference<>();
    }

    @PostConstruct
    public void init() {
        //Set initial empty statistic data
        statisticData.set(calculationService.calculateStatisticData(transactionQueue));

        executorService.scheduleAtFixedRate(() -> {
            //Remove old transaction (i.e. older than 60 seconds)
            while (isTransactionValid(transactionQueue.peek())) {
                transactionQueue.poll();
                //Recalculate statistic data
                statisticData.set(calculationService.calculateStatisticData(transactionQueue));
            }
            LOGGER.debug(statisticData.get().toString());
        }, 0, 1, TimeUnit.SECONDS);
    }

    @Override
    public boolean addTransactionAsync(final Transaction transaction) {
        //Ignore transaction if it is older then threshold (60 sec. by default)
        if (isTransactionValid(transaction)) {
            LOGGER.warn("Transaction {} will be ignored. Reason: transaction older than {} seconds",
                    transaction, transactionThreshold);
            return false;
        }
        executeAsync(() -> {
            transactionQueue.add(transaction);
            //If transaction queue not empty, calculate statistic data
            statisticData.set(calculationService.calculateStatisticData(transactionQueue));
        });
        return true;
    }

    @Override
    public Statistic getStatistic() {
        return statisticData.get();
    }

    private boolean isTransactionValid(final Transaction transaction) {
        if (transaction == null
                || transaction.getAmount() == null
                || transaction.getTimestamp() == null) {
            return false;
        }
        final Instant transactionTime = Instant.ofEpochMilli(transaction.getTimestamp());
        final long transactionTimeInSeconds = ChronoUnit.SECONDS.between(transactionTime, Instant.now());
        return transactionTimeInSeconds > transactionThreshold;
    }

    private void executeAsync(final Runnable runnable) {
        new Thread(runnable).run();
    }

    private class InverseTransactionComparator implements Comparator<Transaction> {
        @Override
        public int compare(final Transaction o1, final Transaction o2) {
            if (o1.getTimestamp().equals(o2.getTimestamp())) {
                return 0;
            }
            return o1.getTimestamp() < o2.getTimestamp() ? -1 : 1;
        }
    }
}
