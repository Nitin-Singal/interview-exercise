package com.acme.mytrader.price;

import com.acme.mytrader.execution.ExecutionService;

public class PriceListenerImpl implements PriceListener {

    private final String security;
    private final double triggerLevel;
    private final int quantityToPurchase;
    private final ExecutionService executionService;

    private boolean tradeExecuted = false;

    public PriceListenerImpl(String security, double triggerLevel, int quantityToPurchase, ExecutionService executionService) {
        this.security = security;
        this.triggerLevel = triggerLevel;
        this.quantityToPurchase = quantityToPurchase;
        this.executionService = executionService;
    }

    @Override
    public void priceUpdate(String security, double price) {
        if (checkTrade(security, price)) {
            System.out.println("Auto Treading for Security: " + security + " , for Price: " + price);
            executionService.buy(security, price, quantityToPurchase);
            tradeExecuted = true;
        }
    }

    private boolean checkTrade(String security, double price) {
        return (!tradeExecuted) && this.security.equals(security) && (price < this.triggerLevel);
    }

    public boolean isTradeExecuted() {
        return tradeExecuted;
    }

    public void setTradeExecuted(boolean tradeExecuted) {
        this.tradeExecuted = tradeExecuted;
    }
}
