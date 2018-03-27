package de.statistic.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.statistic.Application;
import de.statistic.dto.Statistic;
import de.statistic.dto.Transaction;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * Created by Sergey Gening on 27.03.18.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = Application.class)
@AutoConfigureMockMvc
public class StatisticIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void successWorkflowIntegrationTest() throws Exception {
        final Transaction transaction = new Transaction();
        transaction.setAmount(64.61);
        transaction.setTimestamp(Instant.now().minus(1, ChronoUnit.SECONDS).toEpochMilli());

        final Transaction transaction2 = new Transaction();
        transaction2.setAmount(2.3);
        transaction2.setTimestamp(Instant.now().toEpochMilli());

        final Transaction transaction3 = new Transaction();
        transaction3.setAmount(17.2);
        transaction3.setTimestamp(Instant.now().minus(3, ChronoUnit.SECONDS).toEpochMilli());

        final Transaction transaction4 = new Transaction();
        transaction4.setAmount(12.0);
        transaction4.setTimestamp(Instant.now().minus(2, ChronoUnit.SECONDS).toEpochMilli());

        final Transaction transaction5 = new Transaction();
        transaction5.setAmount(28.88);
        transaction5.setTimestamp(Instant.now().minus(4, ChronoUnit.SECONDS).toEpochMilli());

        sendTransactionRequest(transaction);
        sendTransactionRequest(transaction2);
        sendTransactionRequest(transaction3);
        sendTransactionRequest(transaction4);
        sendTransactionRequest(transaction5);

        final MvcResult mvcResult = mvc.perform(get("/statistics"))
                .andReturn();
        final Statistic statistic = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Statistic.class);
        assertEquals(Integer.valueOf(5), statistic.getCount());
        assertEquals(Double.valueOf(124.99), statistic.getSum());
        assertEquals(Double.valueOf(24.998), statistic.getAvg());
        assertEquals(Double.valueOf(64.61), statistic.getMax());
        assertEquals(Double.valueOf(2.3), statistic.getMin());
    }

    private void sendTransactionRequest(final Transaction transaction) throws Exception {
        mvc.perform(post("/transactions")
                .content(objectMapper.writeValueAsString(transaction))
                .contentType(MediaType.APPLICATION_JSON));
    }
}
