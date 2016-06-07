package com.poorjar.http;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.net.URLEncoder;

public class HttpTestClient
{
    String hostName = null;
    Socket socket = null;

    public HttpTestClient(String host)
    {
        hostName = host;
        try
        {
            URL u = new URL(hostName);
            int port = 80;
            if (u.getPort() != -1)
            {
                port = u.getPort();
            }
            if (!(u.getProtocol().equalsIgnoreCase("http")))
            {
                System.err.println("Invalid protocol.");
            }
            socket = new Socket(u.getHost(), port);
        }
        catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
    }

    public void sendClidNotification(String hdid)
    {
        try
        {
            URL u = getClidUrl(hdid);
            OutputStream theOutput = socket.getOutputStream();

            // no auto-flushing
            PrintWriter pw = new PrintWriter(theOutput, false);

            // native line endings are uncertain so add them manually
            StringBuilder b = new StringBuilder();

            b.append("POST " + u.getFile() + " HTTP/1.1\r\n");
            b.append("HOST: " + u.getHost() + ":" + socket.getPort() + "\r\n");
            b.append("Accept: */*\r\n");
            b.append("Connection: keep-alive\r\n");
            b.append("Content-Length: 0\r\n");
            b.append("User-Agent: TEST\r\n");
            b.append("\r\n");

            System.out.print(b.toString());

            pw.print(b.toString());
            pw.flush();
            InputStream in = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(in);
            BufferedReader br = new BufferedReader(isr);

            int len = 0;
            int c;
            while ((c = br.read()) != -1 && (len < 22))
            {
                System.out.print((char) c);
                len++;
            }
            System.out.println("length=" + len);
        }
        catch (Exception ex)
        {
            System.err.println(ex);
        }

    }

    public void sendKeepAlive()
    {
        try
        {
            URL u = getKeepAlive();
            OutputStream theOutput = socket.getOutputStream();

            // no auto-flushing
            PrintWriter pw = new PrintWriter(theOutput, false);

            // native line endings are uncertain so add them manually
            StringBuilder b = new StringBuilder();

            b.append("POST " + u.getFile() + " HTTP/1.1\r\n");
            b.append("HOST: " + u.getHost() + ":" + socket.getPort() + "\r\n");
            b.append("Accept: */*\r\n");
            b.append("Connection: keep-alive\r\n");
            b.append("Content-Length: 0\r\n");
            b.append("User-Agent: TEST\r\n");
            b.append("\r\n");

            System.out.print(b.toString());

            pw.print(b.toString());
            pw.flush();
            InputStream in = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(in);
            BufferedReader br = new BufferedReader(isr);

            int len = 0;
            int c;
            while ((c = br.read()) != -1 && (len < 22))
            {
                System.out.print((char) c);
                len++;
            }
            System.out.println("length=" + len);
        }
        catch (Exception ex)
        {
            System.err.println(ex);
        }
    }

    private URL getClidUrl(String hdid) throws Exception
    {
        StringBuilder urlBuilder = new StringBuilder(hostName);
        urlBuilder.append("/newcall?mac=");
        urlBuilder.append(hdid);
        urlBuilder.append("&xml=");
        urlBuilder.append(URLEncoder.encode("<data><trig-cid><row time=\"0\"><stbuid>", "UTF-8"));
        urlBuilder.append(hdid);
        urlBuilder.append(URLEncoder.encode(
                "</stbuid><requestid>00023100000000000000</requestid><precaller>Call From:</precaller><caller>clidTestApp</caller><phone>6024564567</phone><ttl>15</ttl><datamask>F</datamask></row></trig-cid></data>",
                "UTF-8"));

        return new URL(urlBuilder.toString());
    }

    private URL getKeepAlive() throws Exception
    {
        StringBuilder urlBuilder = new StringBuilder(hostName);
        urlBuilder.append("/keep.jsp");
        return new URL(urlBuilder.toString());
    }
}
