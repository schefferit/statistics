package de.statistic.controller;

import de.statistic.dto.Transaction;
import de.statistic.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Sergey Gening on 26.03.18.
 */
@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private StatisticService statisticService;

    @Autowired
    public TransactionController(final StatisticService statisticService) {
        this.statisticService = statisticService;
    }

    @PostMapping
    public ResponseEntity transaction(@RequestBody final Transaction transaction) {
        final boolean created = statisticService.addTransactionAsync(transaction);
        return ResponseEntity.status(created ? HttpStatus.CREATED : HttpStatus.NO_CONTENT)
                .build();
    }
}
