package com.cognizant.walmart;

import java.util.concurrent.Semaphore;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.ResultSetFuture;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.policies.DCAwareRoundRobinPolicy;
import com.datastax.driver.core.policies.DowngradingConsistencyRetryPolicy;
import com.datastax.driver.core.policies.LoggingRetryPolicy;
import com.datastax.driver.core.policies.TokenAwarePolicy;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;

public class WM_write {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			// TODO Auto-generated method stub
			Cluster cluster = Cluster.builder()
					.addContactPoint("10.237.247.26").build();
			String query = "";
			
			final Semaphore semaphore = new Semaphore(100);

			semaphore.acquire();

			ResultSetFuture resultSetFuture = cluster.connect("reiot")
					.executeAsync(query);

			Futures.addCallback(resultSetFuture,
					new FutureCallback<ResultSet>() {

						public void onSuccess(ResultSet result) {
							// TODO Auto-generated method stub
							semaphore.release();
						}

						public void onFailure(Throwable t) {
							// TODO Auto-generated method stub
							semaphore.release();

						}
					});
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

