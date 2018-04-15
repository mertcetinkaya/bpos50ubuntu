package com.example.sony.bpos50;

import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class UdpClientThread extends Thread{

    ListRoute listRoute;
    String dstAddress;
    int dstPort;
    private boolean running;
    ListRoute.UdpClientHandler handler;

    DatagramSocket socket;
    InetAddress address;

    public UdpClientThread(String addr, int port, ListRoute.UdpClientHandler handler) {
        super();
        dstAddress = addr;
        dstPort = port;
        this.handler = handler;
        listRoute=new ListRoute();
    }

    public void setRunning(boolean running){
        this.running = running;
    }

    private void sendState(String state){
        handler.sendMessage(
                Message.obtain(handler,
                        ListRoute.UdpClientHandler.UPDATE_STATE, state));
    }

    @Override
    public void run() {
        //sendState("connecting...");
/*

 public void client() throws IOException{


        DatagramSocket client_socket = new DatagramSocket(2362);
        InetAddress IPAddress =  InetAddress.getByName("10.80.1.95");

        //while (true)
        // {
                send_data = str.getBytes();
                //System.out.println("Type Something (q or Q to quit): ");

            DatagramPacket send_packet = new DatagramPacket(send_data,str.length(), IPAddress, 2362);
            client_socket.send(send_packet);
          //chandra
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                client_socket.receive(receivePacket);
                modifiedSentence = new String(receivePacket.getData());
                //System.out.println("FROM SERVER:" + modifiedSentence);
                if(modifiedSentence.charAt(2)=='%')
                 txt5.setText(modifiedSentence.substring(0, 3));
                else
                        txt1.setText(modifiedSentence);
                modifiedSentence=null;
                client_socket.close();

          // }

        }

 }
 */
        //running = true;

        try {
            //socket = new DatagramSocket();
            //address = InetAddress.getByName(dstAddress);

            // send request
            //byte[] buf = new byte[256];

            //DatagramPacket packet =
                    //new DatagramPacket(buf, buf.length, address, dstPort);
            //socket.send(packet);

            //sendState("connected");

            // get response
            //packet = new DatagramPacket(buf, buf.length);
            String messageStr=listRoute.Buffer;
            int server_port = 9999;
            DatagramSocket s = new DatagramSocket();
            InetAddress local = InetAddress.getByName("192.168.1.25");
            int msg_length=messageStr.length();
            byte[] message = messageStr.getBytes();
            DatagramPacket p = new DatagramPacket(message, msg_length,local,server_port);
            s.send(p);
            s.close(); //I added
            //socket.receive(packet);
            //String line = new String(packet.getData(), 0, packet.getLength());
            //handler.sendMessage(
              //      Message.obtain(handler, ListRoute.UdpClientHandler.UPDATE_MSG, line+" from client"));
            //handler.sendMessage(Message.obtain(handler,ListRoute.UdpClientHandler.UPDATE_MSG,"bu ne abi00"));

        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //if(socket != null){

                //socket.close();
                //handler.sendEmptyMessage(ListRoute.UdpClientHandler.UPDATE_END);
            //}
        }

    }
    void sendMessage(String buffer) throws IOException {

        String messageStr=buffer;
        int server_port = 9999;
        DatagramSocket s = new DatagramSocket();
        InetAddress local = InetAddress.getByName("192.168.1.25");
        int msg_length=messageStr.length();
        byte[] message = messageStr.getBytes();
        DatagramPacket p = new DatagramPacket(message, msg_length,local,server_port);
        s.send(p);
    }
}