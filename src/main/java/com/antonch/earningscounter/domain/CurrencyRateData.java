package com.antonch.earningscounter.domain;

import java.util.Map;

/**
 * Объект ответа json-cервиса курсов валют
 *
 * @author anton.chekhlenkov@gmail.com
 * @since 12.12.2018
 */
public class CurrencyRateData {

    private boolean success;

    private ErrorData error;

    private Long timestamp;

    private boolean historical;

    private String base;

    private String date;

    private Map<String, Double> rates;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ErrorData getError() {
        return error;
    }

    public void setError(ErrorData error) {
        this.error = error;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isHistorical() {
        return historical;
    }

    public void setHistorical(boolean historical) {
        this.historical = historical;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Map<String, Double> getRates() {
        return rates;
    }

    public void setRates(Map<String, Double> rates) {
        this.rates = rates;
    }

    @Override
    public String toString() {
        return new StringBuilder("[CurrencyRateData: ")
                .append("success=").append(success)
                .append(", error=").append(error)
                .append(", timestamp=").append(timestamp)
                .append(", historical=").append(historical)
                .append(", base=").append(base)
                .append(", date=").append(date)
                .append(", rates=").append(rates)
                .append("]").toString();
    }

}