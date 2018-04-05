package com.sibi;

import java.io.IOException;
import java.io.PrintWriter;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class App {
	
	static String addr = null;

	public static void main(String args[]) throws InterruptedException, IOException {
		
		
		Firebase myFirebaseRef = new Firebase("https://dronedelivery-847c0.firebaseio.com/");
		myFirebaseRef.child("Name").addValueEventListener(new ValueEventListener()
		{
			public void onDataChange(DataSnapshot snapshot){
				addr = 	snapshot.getValue().toString();
			}	
			public void onCancelled(FirebaseError error){}
		}
		);
		while(addr == null)
			Thread.sleep(100);

		String command = "gsutil cp -r gs://dronedelivery-847c0.appspot.com/"+addr+" /home/pi/Desktop/DroneDelivery";
		Runtime rt = Runtime.getRuntime();
		Process pr = rt.exec(command);
		
		System.out.println("Downloading the Address Package is COMPLETE");
		
		PrintWriter writer = new PrintWriter("/home/pi/Desktop/DroneDelivery/Manifest.txt");
		writer.write(addr);
		writer.close();
		System.out.println("Manifest is created");
	}	
}
