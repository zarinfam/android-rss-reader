package ir.irancell.course.rss;


import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;




/**
 * Created by Amir7 on 12/08/2015.
 */
public class FragmentTest extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String title = "title";
    private String link = "link";
    private String description = "description";
    private String urlString = null;
    private XmlPullParserFactory xmlFactoryObject;
    public volatile boolean parsingComplete = true;
    TextView textViewTest;
    ImageButton AddSite;

    private ProgressDialog progress;
    public static final String HTTP_METHOD_GET = "GET";
    public static final String HTTP_METHOD_POST = "POST";
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {




        //Inflate the layout for this fragment
        setHasOptionsMenu(true);
        View view =inflater.inflate(R.layout.fragment_handler, container, false);

/*        ArrayList<CustomerInformation> arrayOfUsers = new ArrayList<CustomerInformation>();

        arrayOfUsers.add(new CustomerInformation("Maryam","02122851474","09352000433","Heravi"));
        arrayOfUsers.add(new CustomerInformation("Mona","02178686878","09352000787","Asghar Tape"));
        arrayOfUsers.add(new CustomerInformation("Roshanak","021879379799","09352000877","Shariyati"));

        CustomerAdapter adapter = new CustomerAdapter(getActivity(),arrayOfUsers);
        ListView listViewCustomer = (ListView) view.findViewById(R.id.listViewCustomer);
        listViewCustomer.setAdapter(adapter);

        */
        AddSite = (ImageButton) view.findViewById(R.id.AddSite);
        textViewTest = (TextView) view.findViewById(R.id.textViewTest);
        return view;






        new AsyncTask<String, Void, String>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progress = ProgressDialog.show(getActivity(), "",
                        "������ �� ���� ...", true);
            }

            @Override
            protected String doInBackground(String... params) {
                return connectToRESTServer(params);

            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                progress.dismiss();

                if (result != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);

                        GsonBuilder builder = new GsonBuilder();
                        Gson gson = builder.create();
                        Post post = gson.fromJson(result, Post.class);//convert result to Post object

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


            }
        }.execute("posts/1", HTTP_METHOD_GET);



    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        super.onCreateOptionsMenu(menu, inflater);

        //fav = menu.add("add");
        // fav.setIcon(R.drawable.disk);

        super.onCreateOptionsMenu(menu, inflater);
        //menu.clear();
       inflater.inflate(R.menu.testmenu, menu);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch(id) {
            // case R.id.action_settings:
            //  Log.i("6666666", "Search Bottom Clicked");
            // Intent intent = new Intent(this, CustomerSearch.class);
            // startActivity(intent);
            //  break;

            case R.id.action_test:

                Log.i("", "Menu Test is Clicked");
                textViewTest.setText("Menu Test Is Clicked");

                break;
/*
            case R.id.action_refresh:
                Log.i("Refresh is clicked","11111111111");

                break;*/


        }

        return super.onOptionsItemSelected(item);
    }




    public void FragmentHandler(String url) {
        this.urlString = url;
    }



    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getDescription() {
        return description;
    }



    public void parseXMLAndStoreIt(XmlPullParser myParser) {
        int event;
        String text = null;

        try {
            event = myParser.getEventType();

            while (event != XmlPullParser.END_DOCUMENT) {
                String name = myParser.getName();

                switch (event) {
                    case XmlPullParser.START_TAG:
                        break;

                    case XmlPullParser.TEXT:
                        text = myParser.getText();
                        break;

                    case XmlPullParser.END_TAG:

                        if (name.equals("title")) {
                            title = text;
                        } else if (name.equals("link")) {
                            link = text;
                        } else if (name.equals("description")) {
                            description = text;
                        } else {
                        }

                        break;
                }

                event = myParser.next();
            }

            parsingComplete = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fetchXML() {
        Thread thread = new Thread(new Runnable() {




            @Override
            public void run() {

                try {
                    URL url = new URL(urlString);
                    HttpURLConnection connect = (HttpURLConnection) url.openConnection();

                    connect.setReadTimeout(10000 /* milliseconds */);
                    connect.setConnectTimeout(15000 /* milliseconds */);
                    connect.setRequestMethod("GET");
                    connect.setDoInput(true);


                    connect.connect();
                    InputStream stream = connect.getInputStream();

                    xmlFactoryObject = XmlPullParserFactory.newInstance();
                    XmlPullParser myparser = xmlFactoryObject.newPullParser();

                    myparser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                    myparser.setInput(stream, null);

                    parseXMLAndStoreIt(myparser);
                    stream.close();
                } catch (Exception e) {
                }
            }
        });
        thread.start();
    }

    private String connectToRESTServer(String... params) {

        BufferedReader reader = null;

        try {
            String httpMethod = params[1];

            URL url = new URL(Config.BACKEND_BASE_URL + params[0]);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10000 /* milliseconds */);
            connection.setConnectTimeout(15000 /* milliseconds */);
            connection.setRequestMethod(httpMethod);

            if (httpMethod.equals(HTTP_METHOD_GET)) {
                connection.setRequestProperty("Accept", "application/json");
                connection.setDoInput(true);
            } else if (httpMethod.equals(HTTP_METHOD_POST)) {
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setDoOutput(true);
                connection.setChunkedStreamingMode(0);

                OutputStream os = new BufferedOutputStream(connection.getOutputStream());
                os.write(params[2].getBytes());
                os.flush();
            }

            // Starts the query
            connection.connect();
            int responseCode = connection.getResponseCode();

            reader = new BufferedReader(new InputStreamReader(
                    (connection.getInputStream())));

            // Convert the InputStream into a string
            StringBuilder contentAsString = new StringBuilder();

            String output;
            while ((output = reader.readLine()) != null) {
                contentAsString.append(output);
            }

            if (responseCode != 200) {
                // parse error json message
                throw new RuntimeException("Failed : HTTP error code : "
                        + responseCode);
            }

            connection.disconnect();

            return contentAsString.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }





}
