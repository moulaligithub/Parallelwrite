package com.cognizant.walmart;

import io.netty.util.concurrent.Future;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSetFuture;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.policies.DCAwareRoundRobinPolicy;
import com.datastax.driver.core.policies.DowngradingConsistencyRetryPolicy;
import com.datastax.driver.core.policies.LoggingRetryPolicy;
import com.datastax.driver.core.policies.TokenAwarePolicy;

import org.json.JSONException;
import org.json.JSONObject;

public class Create_Data_WM {
	public static void main(String[] args) {
		try {
			/*
			 * Cluster cluster =
			 * Cluster.builder().addContactPoints(CassandraPropertiesRea
			 * .getCassandraPropertiesObject().getCassandraServers())
			 * .withRetryPolicy(new
			 * LoggingRetryPolicy(DowngradingConsistencyRetryPolicy.INSTANCE))
			 * .withLoadBalancingPolicy(new
			 * TokenAwarePolicy(DCAwareRoundRobinPolicy.builder()
			 * .withLocalDc(CassandraPropertiesReader
			 * .getCassandraPropertiesObject().getLocalDC()).build())) .build();
			 */
			Cluster cluster = Cluster.builder().addContactPoint("10.237.247.26").build();

			String threshold = "{\"msgSchema\":{\"msgVersion\":1},\"thresholdDetails\":{\"cc\":\"US\",\"vendorType\":\"N\",\"storeNo\":70001,\"reqTS\":\"'10/04/2016 09:47:00'\",\"resTS\":\"'10/04/2016 06:00:04'\",\"rackIndex\":3,\"modIndex\":43,\"sensorIndex\":3,\"sensorIOType\":\"OUTPUT\",\"sensorReadingType\":\"DIGITAL\",\"alrmIndex\":21,\"alrmName\":\"VFDF\",\"alrmMode\":\"ACTIVE\",\"alrmType\":\"CRITICAL\",\"alrmRespDelay\":0,\"alrmEnblEvt\":\"INACTIVE\",\"alarmEnableEventIOType\":\"\",\"alrmEnblEvtModIndex\":0,\"alrmEnblEvtSensorIndex\":0,\"eventActiveDelay\":0,\"siteEmergencyAlarm\":\"\",\"unitFault\":\"\",\"remoteReport\":\"\",\"outputFunction\":\"\",\"thresholdType\":\"OUTSIDE\",\"val1\":\"OPEN\",\"val2\":\"\"}}";

			
			
			
			JSONObject rootObject = new JSONObject(threshold);

			JSONObject thresholdDetails = rootObject.getJSONObject("thresholdDetails");
			
			String country_code = thresholdDetails.getString("cc");
			String vendorType = thresholdDetails.getString("vendorType");
			int store_nbr = Integer.parseInt(thresholdDetails.get("storeNo").toString());
			String req_ts = thresholdDetails.getString("reqTS");
			// String resTS = thresholdDetails.getString("resTS");
			int rack_index = Integer.parseInt(thresholdDetails.get("rackIndex").toString());
			int mod_index = (Integer) thresholdDetails.get("modIndex");
			int sensor_index = (Integer) thresholdDetails.get("sensorIndex");
			String sensor_io_type = thresholdDetails.getString("sensorIOType");
			String sensor_reading_type = thresholdDetails
					.getString("sensorReadingType");
			int alarm_index = (Integer) thresholdDetails.get("alrmIndex");
			String alarm_name = thresholdDetails.getString("alrmName");
			String alarm_mode = thresholdDetails.getString("alrmMode");
			String alarm_type = thresholdDetails.getString("alrmType");
			int alarm_response_delay = (Integer) thresholdDetails
					.get("alrmRespDelay");
			String alarm_enable_event = thresholdDetails.getString("alrmEnblEvt");
			/*String alarm_enable_sensor_io_type = thresholdDetails
					.getString("alarmEnableEventIOType")*/;
			String alarm_enable_sensor_io_type = "KA";
			int alarm_enable_mod_index = (Integer) thresholdDetails
					.get("alrmEnblEvtModIndex");

			/*int alarm_enable_sensor_index = (Integer) thresholdDetails
					.get("alrmEnblEvtSensorIndex");*/
			
			int alarm_enable_sensor_index = 0;

			// String eventActiveDelay =
			// thresholdDetails.getString("eventActiveDelay");

			// String siteEmergencyAlarm =
			// thresholdDetails.getString("siteEmergencyAlarm");

			// String unitFault = thresholdDetails.getString("unitFault");

			//String remoteReport = thresholdDetails.getString("remoteReport");
			//String outputFunction = thresholdDetails.getString("outputFunction");
			String threshold_type = thresholdDetails.getString("thresholdType");
			String val1 = thresholdDetails.getString("val1");
			//String val2 = thresholdDetails.getString("val2");
			String val2 = "val2";

			String query1 = "INSERT INTO reiot.thld_misc_stat_data (country_code,vendorType,store_nbr,req_ts,rack_index,mod_index,sensor_index,sensor_io_type,sensor_reading_type,alarm_index,alarm_name,alarm_mode,alarm_type,alarm_response_delay,alarm_enable_event,alarm_enable_sensor_io_type,alarm_enable_mod_index,alarm_enable_sensor_index,threshold_type,val1,val2)"
					+ " VALUES("
					+ country_code
					+ ","
					+ vendorType
					+ ","
					+ store_nbr
					+ ","
					+ req_ts
					+ ","
					+ rack_index
					+ ","
					+ mod_index
					+ ","
					+ sensor_index
					+ ","
					+ sensor_io_type
					+ ","
					+ sensor_reading_type
					+ ","
					+ alarm_index
					+ ","
					+ alarm_name
					+ ","
					+ alarm_mode
					+ ","
					+ alarm_type
					+ ","
					+ alarm_response_delay
					+ ","
					+ alarm_enable_event
					+ ","
					+ alarm_enable_sensor_io_type
					+ ","
					+ alarm_enable_mod_index
					+ ","+alarm_enable_sensor_index+","
					+ threshold_type
					+ "," 
					+ val1 
					+ "," + 
					val2 + ");";

			System.err.println("Query " + query1);
 
			// Creating Session object
			Session session = cluster.connect("reiot");

			// Executing the query
			session.execute(query1);

			System.out.println("Data created");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  

	}
}