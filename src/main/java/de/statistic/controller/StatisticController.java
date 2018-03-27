package de.statistic.controller;

import de.statistic.dto.Statistic;
import de.statistic.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Sergey Gening on 26.03.18.
 */
@RestController
@RequestMapping("/statistics")
public class StatisticController {

    private final StatisticService statisticService;

    @Autowired
    public StatisticController(final StatisticService statisticService) {
        this.statisticService = statisticService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Statistic getStatistic() {
        return statisticService.getStatistic();
    }
}
