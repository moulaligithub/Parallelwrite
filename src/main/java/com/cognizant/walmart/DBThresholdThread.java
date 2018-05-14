package com.cognizant.walmart;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Socket;

import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Session;

public class DBThresholdThread implements Runnable {
	int pkValue;
	Session session;
	PreparedStatement prepareStmt;
	FileWriter fop = null;
	File file;String filepath;
	

 
	public DBThresholdThread(int pkValue, Session session,PreparedStatement prepareStmt) {
		this.pkValue = pkValue;
		this.session = session;
		this.prepareStmt=prepareStmt;
		
	}

	
	public DBThresholdThread(int pkValue,String filepath)
	{
		this.pkValue=pkValue;
		this.filepath=filepath;
	}
	
	
	

	public void run() {
		
		try {

		    //System.out.println("Thread name "+Thread.currentThread().getName());
	        processCommand(Thread.currentThread().getName(),filepath);
	        //System.out.println(Thread.currentThread().getName()+" End.");
	    		

		}
		
		catch (Exception e) {
			System.out.println("Exception occured time"
					+ System.currentTimeMillis());
			e.printStackTrace();
			
		}
	    }
	
		private void processCommand(String name,String filepath) {
        try {

        	

   			file = new File(filepath+name+pkValue);
    			fop = new FileWriter(file);

    			// if file doesnt exists, then create it
    			if (!file.exists()) {
    				file.createNewFile();
    			}

    			// get the content in bytes
    			String  contentInString="FF,,7890, pkValue, 4,,network_type,, 5, ,sensor_io_type,, 6,,sensor_reading_type,,new Date(System.currentTimeMillis()), ,CT,, ,alarm_class,,,alarm_enable_event,, 7, 8, ,alarm_enable_sensor_io_type,,9, ,alarm_mode,, ,alarm_name,, 10, ,alarm_type,, new Date(System.currentTimeMillis()), ,contacts,,,create_id,, new Date(System.currentTimeMillis()), 0.1f,0.2f, 11, 12, 13, ,dt,, ,es_primary,, ,es_secondary,,new Date(System.currentTimeMillis()), ,fault_value1,,,fault_value2,, 0.3f, 0.4f,new Date(System.currentTimeMillis()), ,last_defrost_time,,0.5f, 0.6f, ,mod_label,, ,mod_status,, ,mod_type,, 0.7f,,phase_loss,, ,rack_label,, ,rack_status,, ,reading,,new Date(System.currentTimeMillis()), ,sensor_label,,,threshold_type,, ,val1,, ,val2,, ,vendor_name";

    			fop.write(contentInString);
    			fop.flush();
    			fop.close();

    			//System.out.println("Done");

    		} catch (IOException e) {
    			e.printStackTrace();
    		
    		}
        	
        
        	finally {
			try {
				if (fop != null) {
					fop.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
    		}
        
        } 
           
        
	}

