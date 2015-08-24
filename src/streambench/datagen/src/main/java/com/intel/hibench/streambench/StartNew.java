package com.intel.hibench.streambench;

import com.intel.hibench.streambench.utils.ConfigLoader;

import java.util.ArrayList;

//Data generators are deployed in different nodes and run by lauching them near simultaneously in different nodes.
public class StartNew {

	public static void main(String[] args){

        if(args.length < 2){
            System.err.println("args: <ConfigFile> <DATA_DIR> need to be specified!");
            System.exit(1);
        }

        ConfigLoader cl = new ConfigLoader(args[0]);

        String benchName  = cl.getPropertiy("hibench.streamingbench.benchname").toLowerCase();
        String topic      = cl.getPropertiy("hibench.streamingbench.topic_name");
        String brokerList = cl.getPropertiy("hibench.streamingbench.broker_list_with_quote");
		long totalCount   = Long.parseLong(cl.getPropertiy("hibench.streamingbench.prepare.push.records"));
        String datadir    = args[2];

		ArrayList<byte[]> contents=null;

		if(benchName.equals("micro-statistics")){
			contents=FileDataGenNew.loadDataFromFile(datadir + "/test2.data");
		}else
			contents=FileDataGenNew.loadDataFromFile(datadir + "/test1.data");
		
		NewKafkaConnector con=new NewKafkaConnector(brokerList);
		
		con.publishData(contents, totalCount, topic);
	}
	
	
}
