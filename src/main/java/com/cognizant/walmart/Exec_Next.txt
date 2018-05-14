package com.cognizant.walmart;

import java.sql.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.HostDistance;
import com.datastax.driver.core.PoolingOptions;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.policies.DCAwareRoundRobinPolicy;
import com.datastax.driver.core.policies.DowngradingConsistencyRetryPolicy;
import com.datastax.driver.core.policies.LoggingRetryPolicy;
import com.datastax.driver.core.policies.TokenAwarePolicy;

public class Exec_Next {

	/**
	 * @param args
	 *            20 million 300 sec 6666 1 sec
	 * @throws InterruptedException
	 */
	public static void main(String[] args) {

		ExecutorService executor = Executors.newFixedThreadPool(Integer.parseInt(args[0]));
		PoolingOptions poolingOptions = new PoolingOptions();
		poolingOptions.setCoreConnectionsPerHost(HostDistance.LOCAL, 5)
				.setMaxConnectionsPerHost(HostDistance.LOCAL, 100)
				.setMaxRequestsPerConnection(HostDistance.LOCAL, 2000);

/*		Cluster cluster = Cluster.builder().withPoolingOptions(poolingOptions)
//				.addContactPoints("10.237.247.26","10.237.247.25","10.237.247.40")
//				.withLoadBalancingPolicy(new RoundRobinPolicy()).build();
		//10.237.247.26","10.237.247.25","10.237.247.40","
		Cluster cluster = Cluster
				.builder()
				.addContactPoints("10.237.247.28"
						/*CassandraPropertiesReader
								.getCassandraPropertiesObject()
								.getCassandraServers())
				.withRetryPolicy(
						new LoggingRetryPolicy(
								DowngradingConsistencyRetryPolicy.INSTANCE))
				.withLoadBalancingPolicy(
						new TokenAwarePolicy(DCAwareRoundRobinPolicy
								.builder()
								.withLocalDc("Cassandra"/*"Walmart"*//*
										CassandraPropertiesReader
												.getCassandraPropertiesObject()
												.getLocalDC()).build()))
				.build();*/

		int counter = 0;
		/* Creating Session object
		Session session = cluster.connect("reiot");
		PreparedStatement prepareStmt = session
				.prepare("INSERT INTO enrich_event_test (country_code,lang_cd,store_nbr,rack_index,network_type,mod_index,sensor_io_type,sensor_index,sensor_reading_type,event_ts,config_type,alarm_class,alarm_enable_event,alarm_enable_mod_index,alarm_enable_sensor_index,alarm_enable_sensor_io_type,alarm_index,alarm_mode,alarm_name,alarm_response_delay,alarm_type,cassandra_ts,contacts,create_id,create_ts,cut_in,cut_out,defrost_duration,defrost_mod_index,defrost_sensor_index,dt,es_primary,es_secondary,esp_read_ts,fault_value1,fault_value2,high_cut_in,high_cut_out,kafka_recvd_ts,last_defrost_time,low_cut_in,low_cut_out,mod_label,mod_status,mod_type,mod_version,phase_loss,rack_label,rack_status,reading,req_ts,sensor_label,threshold_type,val1,val2,vendor_name) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);");

		 */
		long startTime = System.currentTimeMillis();
		System.out.println("start time : " + startTime);
		for (int i = 0; i < Integer.parseInt(args[1]); i++) {
			// int x = rand.nextInt(10400000);
			DBThresholdThread worker = new DBThresholdThread(counter++,args[2]);
			//DBThresholdThread worker = new DBThresholdThread(counter++, session);
			executor.execute(worker);
			// if ((i % 1000) == 0) {
			//
			// // System.err.println("Iteration number " + i);
			// try {
			// Thread.sleep(100);
			// } catch (InterruptedException e) {
			// e.printStackTrace();
			// }
			//
			// }
			// counter++;
			// System.out.println("counter : "+counter);
		}
		System.out.println("counter : " + counter);
		executor.shutdown();
		while (!executor.isTerminated()) {
		}
		// ResultSet
		// resultSet=session.execute("select * from thld_misc_stat_data;");
		// System.out.println("ResultCount : "+resultSet.all().size());
		long endTime = System.currentTimeMillis();
		System.err.println("total time taken in ms " + (endTime - startTime)
				+ " for processing " + counter);

	}
}
