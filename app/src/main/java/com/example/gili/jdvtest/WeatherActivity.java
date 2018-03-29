package com.example.gili.jdvtest;

import android.app.VoiceInteractor;
import android.content.ClipData;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gili.data.CustomAdaptor;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.InputSource;

import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class WeatherActivity extends AppCompatActivity {

    NetworkImageView symbolView;
    TextView txtTodayTemp;
    TextView txtTodayMax;
    TextView txtTodayMin;
    RecyclerView recyclerView;

    MyAdapter adapter;
    ArrayList<ItemData> list;

    RequestQueue queue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        txtTodayMax = (TextView)findViewById(R.id.txtTodayMax);
        txtTodayMin = (TextView)findViewById(R.id.txtTodayMin);
        txtTodayTemp = (TextView)findViewById(R.id.txtTemperature);
        symbolView = (NetworkImageView)findViewById(R.id.todayImage);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);

        list = new ArrayList<ItemData>();
        adapter = new MyAdapter(list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(WeatherActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new MyItemDecoration());
        recyclerView.setAdapter(adapter);
        queue = Volley.newRequestQueue(WeatherActivity.this);



        StringRequest currentRequest = new StringRequest(Request.Method.POST, "http://api.openweathermap.org/data/2.5/weather?q=seoul&mode=xml&units=metric&appid=e534f98698c54d562309ec63a5b5dcf5", new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                parseXMLCurrent(response);
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        StringRequest forecast = new StringRequest(Request.Method.POST, "http://api.openweathermap.org/data/2.5/forecast/daily?q=seoul&units=metric&mode=xml&appid=61f9b9306648d74a4ffb781d9f569a93", new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                parseXMLForecast(response);
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(currentRequest);
        queue.add(forecast);
    }

    private class ItemData {
        public String max;
        public String min;
        public String day;
        public Bitmap image;
    }

    private  class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView dayView;
        public TextView maxView;
        public TextView minView;
        public ImageView imageView;

        public MyViewHolder(View itemView){
            super(itemView);
            dayView = (TextView)itemView.findViewById(R.id.txtWeather);
            maxView = (TextView)itemView.findViewById(R.id.txtMax);
            minView = (TextView)itemView.findViewById(R.id.txtMin);
            imageView = (ImageView)itemView.findViewById(R.id.imgWeather);
        }
    }

    private  class MyAdapter extends RecyclerView.Adapter<MyViewHolder>{
        private final List<ItemData> list;
        public  MyAdapter(List<ItemData> list){
            this.list = list;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_item, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            ItemData vo = list.get(position);
            holder.dayView.setText(vo.day);
            holder.maxView.setText(vo.max);
            holder.minView.setText(vo.min);
            holder.imageView.setImageBitmap(vo.image);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    class MyItemDecoration extends RecyclerView.ItemDecoration{
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.set(10,10,10,10);
            view.setBackgroundColor(0x88929090);
        }
    }

    private void parseXMLCurrent(String response){
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc;
            doc = builder.parse(new InputSource(new StringReader(response)));
            doc.getDocumentElement().normalize();
            Element tempElement=(Element)(doc.getElementsByTagName("temperature").item(0));
            String temperature = tempElement.getAttribute("value");
            String min = tempElement.getAttribute("min");
            String max = tempElement.getAttribute("max");

            txtTodayTemp.setText(temperature);
            txtTodayMax.setText(max);
            txtTodayMin.setText(min);

            Element weatherElement = (Element)(doc.getElementsByTagName("weather").item(0));
            String symbol = weatherElement.getAttribute("icon");

            ImageLoader imageLoader = new ImageLoader(queue, new ImageLoader.ImageCache() {
                @Override
                public Bitmap getBitmap(String url) {
                    return null;
                }

                @Override
                public void putBitmap(String url, Bitmap bitmap) {

                }
            });
            symbolView.setImageUrl("http://openweathermap.org/img/w/"+symbol+".png", imageLoader);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void parseXMLForecast(String response){
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc;
            doc = builder.parse(new InputSource(new StringReader(response)));
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("time");
            for(int i=0;i<nodeList.getLength();i++){
                final ItemData vo = new ItemData();
                Element timeNode =(Element)nodeList.item(i);
                vo.day = timeNode.getAttribute("day").substring(5);
                Element tempElement = (Element)timeNode.getElementsByTagName("temperature").item(0);
                vo.max = tempElement.getAttribute("max");
                vo.min = tempElement.getAttribute("min");

                Element symbolNode = (Element)timeNode.getElementsByTagName("symbol").item(0);
                String symbol = symbolNode.getAttribute("var");

                String url = "http://openweathermap.org/img/w/"+symbol+".png";

                ImageRequest imageRequest = new ImageRequest(url, new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        vo.image = response;
                        adapter.notifyDataSetChanged();
                    }
                }, 0, 0, ImageView.ScaleType.CENTER_CROP, null, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

                queue.add(imageRequest);
                list.add(vo);
            }
            adapter.notifyDataSetChanged();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
