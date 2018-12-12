package com.antonch.earningscounter.domain;

import com.antonch.earningscounter.common.Currency;

/**
 * Объект хранит данные по курсам валют
 * и данные для рассчета прибыли/убытка
 * указанные пользователем
 *
 * @author anton.chekhlenkov@gmail.com
 * @since 12.12.2018
 */
public class CurrencyRateDataHolder {

    /**
     * Спред равен 0.5%
     */
    private static final double SPREAD = 0.005;

    private Double amount;

    private Double currentRate;
    private String currentRateDate;

    private Double historicalRate;
    private String historicalRateDate;

    public void setAmount(Double value) {
        amount = value;
    }

    public void clean() {
        amount = null;
        historicalRate = null;
        historicalRateDate = null;
    }

    public void updateRate(CurrencyRateData rateData) {
        if (rateData.isHistorical()) {
            historicalRate = rateData.getRates().get(Currency.RUB);
            historicalRateDate = rateData.getDate();
        } else {
            currentRate = rateData.getRates().get(Currency.RUB);
            currentRateDate = rateData.getDate();
        }
    }

    public String getCalcResult() {
        StringBuilder builder = new StringBuilder("EUR > RUB\n");
        builder.append(currentRate != null ? String.format("   Current rate (%s): %s\n", currentRateDate, currentRate) : '\n');
        builder.append(historicalRate != null ? String.format("   Exchange rate on %s: %s\n", historicalRateDate, historicalRate) : '\n');
        builder.append(readyToCalc() ? String.format("   Spread: %s%%\n", SPREAD * 100) : '\n');
        builder.append(readyToCalc() ? String.format("   %s: %s RUB\n", calc() < 0 ? "Loss" : "Income", Math.abs(calc())) : '\n');
        return builder.toString();
    }

    private boolean readyToCalc() {
        return currentRate != null && historicalRate != null && amount != null;
    }

    private Double calc() {
        return (amount * currentRate * (1 - SPREAD)) - (amount * historicalRate * (1 - SPREAD));
    }

}
