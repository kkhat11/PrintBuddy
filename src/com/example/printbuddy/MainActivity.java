package com.example.printbuddy;

import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

import com.example.printbuddy.MyView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.system.Os;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity{

 TextView textStatus;
 MyView GridLayout;
 String finalPicture = "";
 PendingIntent mPermissionIntent;
 private BluetoothDevice arduinoDevice;
 private BluetoothAdapter BA;
 private Set<BluetoothDevice> pairedDevices;
 private BluetoothSocket socket;
 private OutputStream os;

 
 
 @Override
 protected void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.activity_main);
 
  TextView tx = (TextView)findViewById(R.id.printbuddyLogo);
  Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/Pacifico.ttf");
  tx.setTypeface(custom_font);
  tx.setTextColor(Color.parseColor("#00BFFF"));
  
  BA = BluetoothAdapter.getDefaultAdapter();
  if(BA != null){
  if(!BA.isEnabled()){
	  Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
	  startActivityForResult(turnOn, 0);
	  
  }
	  pairedDevices = BA.getBondedDevices();
	  for(BluetoothDevice device : pairedDevices) {
		  if(device.getName().contains("HC-06"))
		  {
			  arduinoDevice = device;
			  break;
		  }
	  }
  }
  UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
  try{
	  socket = arduinoDevice.createInsecureRfcommSocketToServiceRecord(uuid);
  }
  catch(Exception e){
  }
  try{
	  socket.connect();
	  os = socket.getOutputStream();
  }
  catch(Exception e){}
  
  

  
  
  
  GridLayout = (MyView) findViewById(R.id.mygrid);

 }

public void sendImage(){
	String send = GridLayout.getStringInformation();
	try{
   	 os.write(send.getBytes());
    }
    catch(Exception e){}
}

public void Send(View view){
	new AlertDialog.Builder(this)
    .setMessage("Are you sure you want to print this design?")
    .setCancelable(false)
    .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int id) {
           sendImage();
       }
   })
    .setNegativeButton("View Again", null)
    .show(); 
 }

public void eraseAll(View view){
	GridLayout.eraseAll();
}


 
}