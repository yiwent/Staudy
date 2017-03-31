package com.yiwen.httpculconnection;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public final static String TAG = "MainActivity";
    private TextView responseText;
    private final static String xml = "<apps>\n" +
            "<app>\n"+
            "<id>1</id>\n"+
            "<name>Google Maps</name>\n"+
            "<version>1.0</version>\n"+
            "</app>\n" +
            "<app>\n" +
            "<id>2</id>\n" +
            "<name>Chrome</name>\n"+
            "<version>2.1</version>\n"+
            "</app>\n" +
            "<app>\n" +
            "<id>3</id>\n"+
            "<name>Google Play</name>\n"+
            "<version>2.3</version>\n"+
            "</app>\n" +
            "</apps>";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button send = (Button) findViewById(R.id.bt_send);
        responseText = (TextView) findViewById(R.id.respone_text);
        send.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bt_send) {
            //            sendRequestwithHttpURLConnection();
            //                        sendRequestwithOKhttp();
            //            paseXMLWithPull(xml);
//            paseXMLWithSAX(xml);
        }
    }

    private void paseXMLWithSAX(String xml) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            XMLReader xmlReader = factory.newSAXParser().getXMLReader();
            ContentHandler handler = new ContentHandler();
            //设置handler实例到Reader
            xmlReader.setContentHandler(handler);
            //解析
            xmlReader.parse(new InputSource(new StringReader(xml)));
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendRequestwithOKhttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
//                            .url("http://www.baidu.com")
                            .url("http://localhost/get_data.xml")
                            .build();
                    Response respone = client.newCall(request).execute();
                    String responesData = respone.body().string();
//                    showResponse(responesData);
                    paseXMLWithPull(xml);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void paseXMLWithPull(String responesData) {
        try {
            XmlPullParserFactory factort = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factort.newPullParser();
            parser.setInput(new StringReader(responesData));
            int eventType = parser.getEventType();
            String id = "";
            String name = "";
            String version = "";
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String nodeName = parser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG: {
                        if ("id".equals(nodeName)) {
                            id = parser.nextText();
                        } else if ("name".equals(nodeName)) {
                            name = parser.nextText();
                        } else if ("version".equals(nodeName)) {
                            version = parser.nextText();
                        }
                    }
                    break;

                    case XmlPullParser.END_TAG: {
                        if ("app".equals(nodeName)) {
                            Log.d(TAG, "paseXMLWithPull: id:" + id);
                            Log.d(TAG, "paseXMLWithPull: name:" + name);
                            Log.d(TAG, "paseXMLWithPull: version:" + version);
                        }
                        break;
                    }
                    default:
                        break;
                }
                eventType = parser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendRequestwithHttpURLConnection() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader reader = null;
                try {
                    URL url = new URL("https://www.baidu.com");
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    InputStream in = connection.getInputStream();
                    //读取
                    reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    showResponse(response.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }

    private void showResponse(final String response) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                responseText.setText(response);
            }
        });
    }
}
