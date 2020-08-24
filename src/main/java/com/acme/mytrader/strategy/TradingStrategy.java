package com.acme.mytrader.strategy;

import com.acme.mytrader.execution.ExecutionService;
import com.acme.mytrader.price.PriceListener;
import com.acme.mytrader.price.PriceListenerImpl;
import com.acme.mytrader.price.PriceSource;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class TradingStrategy {

	private ExecutionService executionService;
	private PriceSource priceSource;

	private static final List<String> SECURITIES = Arrays.asList("IBM", "TCS", "DB");

	private static final double RANGE_MIN = 1.00;
	private static final double RANGE_MAX = 200.00;

	public TradingStrategy() {
	}

	public TradingStrategy(ExecutionService executionService, PriceSource priceSource) {
		this.executionService = executionService;
		this.priceSource = priceSource;
	}

	public void autoBuyExecutor(String security, double priceThreshold, int volume) {

		PriceListener priceListener = new PriceListenerImpl(security, priceThreshold, volume, executionService);
		priceSource.addPriceListener(priceListener);

		ExecutorService executorService = Executors.newFixedThreadPool(1);
		try {
			Future future = executorService.submit(new Runnable() {
				@Override
				public void run() {
					Random rand = new Random();
					for (int i = 0; i < 10; i++) {
						String security = SECURITIES.get(rand.nextInt(SECURITIES.size()));
						double price = RANGE_MIN + (RANGE_MAX - RANGE_MIN) * rand.nextDouble();
						// Ideally PriceListener should come from PriceSource , as we are not
						// implementing PriceSource so I have used this directly
						priceListener.priceUpdate(security, price);
					}
				}
			});
			future.get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		} finally {
			executorService.shutdownNow();
			priceSource.removePriceListener(priceListener);
		}
	}

}
