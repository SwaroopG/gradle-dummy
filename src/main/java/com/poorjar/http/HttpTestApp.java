package com.poorjar.http;

import java.util.ArrayList;
import java.util.List;

/**
 * Http App!
 */
public class HttpTestApp
{
    public static void main(String[] args) throws Exception
    {
        HttpTestClient httpClient1 = new HttpTestClient("http://int-vod-001.hesvr.digeo.dgosvc-priv.com:29586");
        Thread notificationThread = new Thread(new HttpClientThread(httpClient1, getMacIds()));
        notificationThread.start();

        Thread keepAliveThread = new Thread(new HttpKeepAliveThread(httpClient1));
        keepAliveThread.start();
    }

    private static List<String> getMacIds()
    {

        List<String> macIds = new ArrayList<String>();

        for (int i = 0; i < 50; i++)
        {
            String ii = "";
            if (i < 10)
            {
                ii = "D" + i;
            }
            else
            {
                ii = "" + i;
            }

            macIds.add("FFF449D1D9" + ii);
        }
        return macIds;
    }
}

class HttpClientThread extends Thread
{
    private List<String> macIds;
    private HttpTestClient httpClient;

    HttpClientThread(HttpTestClient httpClient, List<String> macIds)
    {
        this.httpClient = httpClient;
        this.macIds = macIds;
    }

    public void run()
    {
        try
        {
            long start = System.nanoTime();
            for (String macId : macIds)
            {
                httpClient.sendClidNotification(macId);
                Thread.sleep(200);
            }
            long end = System.nanoTime();
            System.out.println("Completed in " + (end - start) / 1000000 + "ms");

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}

class HttpKeepAliveThread extends Thread
{
    private HttpTestClient httpClient;

    HttpKeepAliveThread(HttpTestClient httpClient)
    {
        this.httpClient = httpClient;
    }

    public void run()
    {
        try
        {
            int index = 1;
            long start = System.nanoTime();
            while (index <= 25)
            {
                httpClient.sendKeepAlive();
                Thread.sleep(500);
                index++;
            }
            long end = System.nanoTime();
            System.out.println("Completed in " + (end - start) / 1000000 + "ms");

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}