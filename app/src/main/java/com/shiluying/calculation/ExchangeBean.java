package com.shiluying.calculation;

public class ExchangeBean {
    private String date;
    private String base;
    private String time_last_updated;
    private String rates;

    public String getDate() {
        return date;
    }

    public String getBase() {
        return base;
    }

    public String getRates() {
        return rates;
    }

    public String getTime_last_updated() {
        return time_last_updated;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setRates(String rates) {
        this.rates = rates;
    }

    public void setTime_last_updated(String time_last_updated) {
        this.time_last_updated = time_last_updated;
    }
}
