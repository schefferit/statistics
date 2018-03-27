package de.statistic.dto;

import java.util.Objects;

/**
 * Created by Sergey Gening on 26.03.18.
 */
public class Statistic {

    private Double sum;

    private Double avg;

    private Double max;

    private Double min;

    private Integer count;

    public Double getSum() {
        return sum;
    }

    public void setSum(final Double sum) {
        this.sum = sum;
    }

    public Double getAvg() {
        return avg;
    }

    public void setAvg(final Double avg) {
        this.avg = avg;
    }

    public Double getMax() {
        return max;
    }

    public void setMax(final Double max) {
        this.max = max;
    }

    public Double getMin() {
        return min;
    }

    public void setMin(final Double min) {
        this.min = min;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(final Integer count) {
        this.count = count;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Statistic statistic = (Statistic) o;
        return Objects.equals(sum, statistic.sum) &&
                Objects.equals(avg, statistic.avg) &&
                Objects.equals(max, statistic.max) &&
                Objects.equals(min, statistic.min) &&
                Objects.equals(count, statistic.count);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sum, avg, max, min, count);
    }

    @Override
    public String toString() {
        return "Statistic{" + "sum=" + sum +
                ", avg=" + avg +
                ", max=" + max +
                ", min=" + min +
                ", count=" + count +
                '}';
    }
}
