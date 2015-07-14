package com.example.android.navigationdrawerexample;



import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;

import com.example.android.navigationdrawerexample.R.id;
import com.example.android.navigationdrawerexample.R.string;





import android.R.integer;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class sellerFrament extends Fragment {
	Double sum=0.0;
	private File cache;
   protected ListView listview2;
	@Override
	public View onCreateView(final LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.inde_detail, container,false);
		final String seller_name=getArguments().getString("seller_name");
		TextView textView=(TextView) view.findViewById(R.id.sellername);
		textView.setText(seller_name);
		
//显示可买的蔬菜品种及价格-------------------------------------------------------------------
		   listview2=(ListView)view.findViewById(R.id.buy_order);
		   final Handler handler2=new Handler(){
			   public void handleMessage(Message msg){

		    		 switch (msg.what) {       
		    		 case 0:
		    			
					//	sellerAdapter mAdapter = new sellerAdapter(getActivity(),contacts,cache);
		                // mListView.setAdapter(mAdapter);
		    			 listview2.setAdapter((SimpleAdapter) msg.obj);
		    	
		    			 break;
		    		 default:

		    			 Toast.makeText(getActivity(), "handler fali", 1).show();

		    			 break;
		   }}};
		    		 new Thread(new Runnable() {
		    		  		List<Map<String, Object>> list2 = new ArrayList<Map<String, Object>>();
		    		  		public void run() {
		    		  		    HttpClient client = new DefaultHttpClient();
		    		  		    StringBuilder builder = new StringBuilder();
		    		  		    HttpPost httpPost = new HttpPost(getString(R.string.site)+"search_order.php");
		    		  		  ArrayList<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();
		    		  			pairs.add(new BasicNameValuePair("seller_name", seller_name));
		    		  			try {
		    		  				UrlEncodedFormEntity p_entity = new UrlEncodedFormEntity(pairs, "UTF8");
		    		  				httpPost.setEntity(p_entity);
		    		  				HttpResponse response = client.execute(httpPost);
		    		  				
		    		  				BufferedReader reader = new BufferedReader(new InputStreamReader(
		    		  				        response.getEntity().getContent()));
		    		  				        for (String s = reader.readLine(); s != null; s = reader.readLine()) {
		    		  				            builder.append(s);
		    		  				        }
  				        Log.v("index_detail",builder.toString());
		    		  				      JSONArray json=new JSONArray(builder.toString());
		    		  				    for(int i=0;i<json.length();i++){ 
		    		  				    	  Map<String, Object> map = new HashMap<String, Object>();
		    		  				    	  map.put("s_name",json.getJSONObject(i).getString("s_name"));
		    		  				    	  map.put("s_time",json.getJSONObject(i).getString("time"));
		    		  				    	  map.put("v_money", "挂号："+seller_name);
		    		  				    	  list2.add(map);}
		    		  				  Log.v("index_detail",list2.size()+"");
		    		  				  SimpleAdapter inadapter=new SimpleAdapter(getActivity(), list2, R.layout.index_detail_item, new String[]{"s_name","v_money","s_time"}, new int[]{R.id.s_name,R.id.d_name,R.id.order_time});
		    		  				Log.v("index_detail","simpleAdater");  
		    		  				  handler2.obtainMessage(0,inadapter).sendToTarget();}
		  				        catch(Exception e) {
		  							e.printStackTrace();
		  						
		  							}
		  		       
		  		}
		  	}).start(); 
//introuce 简介----------------------------------------------------------------------------------
		String[] introuce=getResources().getStringArray(R.array.introuce);
		Log.v("introuce", introuce[0]);
		TextView introuceview=(TextView)view.findViewById(R.id.introduction);
		introuceview.setText(introuce[0]);
//给列表添加监听
		listview2.setOnItemClickListener(new OnItemClickListener(){
      	  public void onItemClick(AdapterView<?> parent, View view, int position,
  					long id) {
  		     TextView textView2=(TextView) view.findViewById(R.id.s_name);
  		     String s_name=textView2.getText().toString();
  		     
  		     Fragment sellFragment=new self();
  		     Bundle bundle=new Bundle();
  		     bundle.putString("seller_name", s_name);
               bundle.putString("de", seller_name);
  		     sellFragment.setArguments(bundle);
  		     FragmentTransaction transaction = getFragmentManager().beginTransaction();
  	  		 transaction.replace(R.id.content_frame, sellFragment);
  	         transaction.addToBackStack(null);
  	         transaction.commit();}
        });
		
		
		
		
		
		    		  				    return view;
	}

}
