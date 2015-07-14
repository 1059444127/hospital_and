package com.example.android.navigationdrawerexample;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;

import android.app.Fragment;
import android.app.FragmentTransaction;
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

public class now extends Fragment {
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.see, container,false);
		TextView name=(TextView)view.findViewById(R.id.sellername1);
		name.setText("今日挂号");
		 final ListView listview2=(ListView)view.findViewById(R.id.buy_order1);
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
		    		  		    HttpPost httpPost = new HttpPost(getString(R.string.site)+"all_order.php");
		    		  		  ArrayList<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();
		    		  			pairs.add(new BasicNameValuePair("time", "now"));
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
		    		  				    	  map.put("s_time",json.getJSONObject(i).getString("d_name")+"("+json.getJSONObject(i).getString("dc_name")+")");
		    		  				    	  if(json.getJSONObject(i).getString("state").equals("1")){
		    		  				    		map.put("v_money", "就诊已完毕。。。");  
		    		  				    	  }
		    		  				    	  if (json.getJSONObject(i).getString("state").equals("0")) {
												map.put("v_money", "就诊进行中...");
											}
		    		  				    	 if (json.getJSONObject(i).getString("state").equals("-1")) {
													map.put("v_money", "挂号已过期...");
												}
		    		  				    	  
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
		    		 listview2.setOnItemClickListener(new OnItemClickListener(){
		    	      	  public void onItemClick(AdapterView<?> parent, View view, int position,
		    	  					long id) {
		    	      		Log.v("tash","de_name:==");
		    	  		     TextView textView=(TextView) view.findViewById(R.id.s_name);
		    	  		     String seller_name=textView.getText().toString();
		    	  		     TextView de=(TextView)view.findViewById(R.id.order_time);
		    	  		     String de_name=de.getText().toString();
		    	  		     String name=de_name.substring(0,2);
		    	  		   Log.v("tash","de_name:=="+ name);
		    	  		  
		    	  		     Fragment sellFragment=new self();
		    	  		     Bundle bundle=new Bundle();
		    	  		     bundle.putString("seller_name", seller_name);
		    	  		     
		    	  		     bundle.putString("de", name);
		    	  		     sellFragment.setArguments(bundle);
		    	  		     FragmentTransaction transaction = getFragmentManager().beginTransaction();
		    	  	  		 transaction.replace(R.id.content_frame, sellFragment);
		    	  	         transaction.addToBackStack(null);
		    	  	         transaction.commit();}
		    	        });
		return view;

	}}
