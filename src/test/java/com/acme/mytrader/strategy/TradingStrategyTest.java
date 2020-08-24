package com.acme.mytrader.strategy;

import com.acme.mytrader.execution.ExecutionService;
import com.acme.mytrader.price.PriceListenerImpl;
import com.acme.mytrader.price.PriceSource;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;

public class TradingStrategyTest {

    @Mock
    private ExecutionService executionService;
    @Mock
    private PriceSource priceSource;

    private TradingStrategy tradingStrategy;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        tradingStrategy = new TradingStrategy(executionService, priceSource);
    }

    @Test
    public void testAutoBuyExecutor() {
        tradingStrategy.autoBuyExecutor("IBM", 55, 100);
        Mockito.verify(priceSource).addPriceListener(any(PriceListenerImpl.class));
        Mockito.verify(priceSource).removePriceListener(any(PriceListenerImpl.class));
    }
}