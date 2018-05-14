package com.cognizant.walmart;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.policies.RoundRobinPolicy;

public class WM_DB {
	public static void main(String[] args) {
		try {
			Cluster cluster = Cluster
					.builder()
					.addContactPoints(
							"10.237.247.26","10.237.247.25","10.237.247.40")
					.withLoadBalancingPolicy(new RoundRobinPolicy()).build();

			// String query="select * from thld_misc_stat_data;";
			String query = "select count(1)   from reiot.vt_tomstone_test limit 100000;";
			// String truncate="truncate enrich_event_details;";
			System.out.println(query);

			ResultSet resultSetFuture = cluster.connect("reiot").execute(query);
			Row row = resultSetFuture.one();

			long expected = row.getLong(0);
			System.out.println("count : " + expected);

		} catch (Exception e) {
			System.out.println("Exception in getting data " + e);
		} finally {
			System.exit(1);
		}
	}
}
