package de.statistic.dto;

import java.util.Objects;

/**
 * Created by Sergey Gening on 26.03.18.
 */
public class Transaction {

    private Double amount;

    private Long timestamp;

    public Transaction() {
    }

    public Transaction(final Double amount, final Long timestamp) {
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(final Double amount) {
        this.amount = amount;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(final Long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Transaction that = (Transaction) o;
        return Objects.equals(amount, that.amount) &&
                Objects.equals(timestamp, that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, timestamp);
    }

    @Override
    public String toString() {
        return "Transaction{" + "amount=" + amount +
                ", timestamp=" + timestamp +
                '}';
    }
}
