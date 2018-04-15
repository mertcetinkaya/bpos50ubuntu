package com.example.sony.bpos50;



import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
//import java.net.Socket;

//import java.io.BufferedWriter;
//import java.io.OutputStreamWriter;


public class ListRoute extends AppCompatActivity {
    Button MainActivityButton;
    Button SendRouteButton;
    EditText WriteRouteText;
    Button ShowRouteButton;
    Button SaveList;
    UdpClientHandler udpClientHandler;
    UdpClientThread udpClientThread;

    ToggleButton Wafer;
    ToggleButton Biscuit;
    ToggleButton Water;
    ToggleButton Cola;
    ToggleButton Rice;
    ToggleButton Bulgur;
    List<String> ShoppingList = new ArrayList<String>(6);
    List<String> ChocolateList = new ArrayList<String>(2);
    List<String> BeverageList = new ArrayList<String>(2);
    List<String> LegumesList = new ArrayList<String>(2);
    List<String> Route = new ArrayList<String>(3);
    static String Buffer_ListRoute ="";
    static int Buffer_ListRoute_Control=0;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_route);

        MainActivityButton = (Button)findViewById(R.id.main_activity);
        SendRouteButton = (Button)findViewById(R.id.sendroutebutton);
        WriteRouteText = (EditText) findViewById(R.id.writeroutedittext);
        ShowRouteButton = (Button)findViewById(R.id.showroutebutton);
        SaveList = (Button)findViewById(R.id.savelistbutton);
        Wafer = (ToggleButton)findViewById(R.id.wafer);
        Biscuit = (ToggleButton)findViewById(R.id.biscuit);
        Water = (ToggleButton)findViewById(R.id.water);
        Cola = (ToggleButton)findViewById(R.id.cola);
        Rice = (ToggleButton)findViewById(R.id.rice);
        Bulgur = (ToggleButton)findViewById(R.id.bulgur);
        ChocolateList.add("Wafer");
        ChocolateList.add("Biscuit");
        BeverageList.add("Water");
        BeverageList.add("Cola");
        LegumesList.add("Bulgur");
        LegumesList.add("Rice");

        MainActivityButton.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                Intent intomain = new Intent(ListRoute.this, MainActivity.class);
                startActivity(intomain);
            }
        });

        SaveList.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                ShoppingList.clear();
                if(Wafer.isChecked())
                    ShoppingList.add("Wafer");
                if(Biscuit.isChecked())
                    ShoppingList.add("Biscuit");
                if(Water.isChecked())
                    ShoppingList.add("Water");
                if(Cola.isChecked())
                    ShoppingList.add("Cola");
                if(Rice.isChecked())
                    ShoppingList.add("Rice");
                if(Bulgur.isChecked())
                    ShoppingList.add("Bulgur");
            }
        });

        ShowRouteButton.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                WriteRouteText.setText("");
                Route.clear();
                if(!Collections.disjoint(ShoppingList,ChocolateList))
                    Route.add("Chocolate");
                if(!Collections.disjoint(ShoppingList,BeverageList))
                    Route.add("Beverage");
                if(!Collections.disjoint(ShoppingList,LegumesList))
                    Route.add("Legumes");
                for(int i=0;i<Route.size();i++)
                {
                    WriteRouteText.append(""+Route.get(i));
                    if(i<(Route.size()-1))
                        WriteRouteText.append(", ");
                }

            }
        });


        SendRouteButton.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {

                try{
                    Buffer_ListRoute ="";
                    if(Route.contains("Chocolate"))
                        Buffer_ListRoute +="1";
                    if(Route.contains("Beverage"))
                        Buffer_ListRoute +="2";
                    if(Route.contains("Legumes"))
                        Buffer_ListRoute +="3";
              /*      Socket sock = new Socket("192.168.1.25", 1234);
                    WriteRouteText.append("socketerror2");
                    OutputStreamWriter osw = new OutputStreamWriter(sock.getOutputStream());
                    BufferedWriter bw = new BufferedWriter(osw);
                    String sendMessage = Buffer_ListRoute;
                    bw.write(sendMessage);
                    bw.flush();
                    sock.close();*/

         /*     String messageStr="1";
              int server_port=1234;
                    DatagramSocket s=new DatagramSocket();
                    InetAddress local=InetAddress.getByName("192.168.1.25");
                    int msg_lemgtj=messageStr.length();
                    byte[] message=messageStr.getBytes();
                    DatagramPacket p=new DatagramPacket(message,msg_lemgtj,local,server_port);
                    s.send(p);

*/
                    Buffer_ListRoute_Control=1;
                    udpClientThread = new UdpClientThread(
                           "192.168.1.25",
                            Integer.parseInt("9999"),
                            udpClientHandler);
                    udpClientThread.start();


                }

                catch (Exception ex) {}

            }
        });
        /*try {
            udpClientThread.sendMessage(Buffer_ListRoute);
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        udpClientHandler = new UdpClientHandler(this);


    }




    public static class UdpClientHandler extends Handler {
        public static final int UPDATE_STATE = 0;
        public static final int UPDATE_MSG = 1;
        public static final int UPDATE_END = 2;
        private ListRoute parent;

        public UdpClientHandler(ListRoute parent) {
            super();
            this.parent = parent;
        }

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){
                case UPDATE_STATE:
                    parent.updateState((String)msg.obj);
                    break;
                case UPDATE_MSG:
                    parent.updateRxMsg((String)msg.obj);
                    break;
                case UPDATE_END:
                    parent.clientEnd();
                    break;
                default:
                    super.handleMessage(msg);
            }

        }
    }
    private void updateState(String state){
       // textViewState.setText(state);
    }

    private void updateRxMsg(String rxmsg){
     //   textViewRx.append(rxmsg + "\n");
    }

    private void clientEnd(){
        udpClientThread = null;
      //  textViewState.setText("bu mertten");
      //  buttonConnect.setEnabled(true);

    }

}