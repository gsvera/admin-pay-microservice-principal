package com.core.principal.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppConfig {
    @Value("${app.codefolio}")
    private String appCodeFolio;
    @Value("${app.statusoffpay}")
    private String appStatusOffPay;
    private final int PAY_LESS_TO_AMOUNT = 1;
    private final int PAY_EQUAL_TO_AMOUNT = 2;
    private final int PAY_MORE_TO_AMOUNT = 3;
    private final int PAY_MORE_TO_AMOUNT_BUT_DEVENGADO = 4;

    public String getAppCodeFolio() {
        return appCodeFolio;
    }
    public String getAppStatusOffPay(){ return appStatusOffPay;}
    public int getPayLessToAmount(){return this.PAY_LESS_TO_AMOUNT;}
    public int getPayEqualToAmount(){return this.PAY_EQUAL_TO_AMOUNT;}
    public int getPayMoreToAmount(){return this.PAY_MORE_TO_AMOUNT;}
    public int getPayMoreToAmountBuDevengado(){return this.PAY_MORE_TO_AMOUNT_BUT_DEVENGADO;}

}
