package com.example.android.navigationdrawerexample;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;


import org.apache.http.message.BasicNameValuePair;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class self extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.order, container,false);
		final String sick=getArguments().getString("seller_name");
		final String de=getArguments().getString("de");
		Log.v("sick", sick+"---"+de);
		TextView name=(TextView)view.findViewById(R.id.self_name);
		name.setText("姓名："+sick);
		ImageView self=(ImageView)view.findViewById(R.id.self_image);
		asyncloadImage(self,getString(R.string.image)+"sick/"+sick+".gif");
		TextView department=(TextView)view.findViewById(R.id.self_department);
		TextView date=(TextView)view.findViewById(R.id.self_date);
		asyncloadString(date,department,sick);
		TextView old=(TextView)view.findViewById(R.id.textView2);
		asyncloadold(old, sick);
		Button allow=(Button)view.findViewById(R.id.button1);
		Button refuse=(Button)view.findViewById(R.id.button2);
		
		allow.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				new AlertDialog.Builder(getActivity()
						).setTitle("提示").setMessage("挂号结束").setPositiveButton("确定",
	        			 new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					new Thread(new Runnable() {
						
						@Override
						public void run() {
					
					DefaultHttpClient client = new DefaultHttpClient();//http客户端
					HttpPost httpPost = new HttpPost(getString(R.string.site)+"allow.php");
					ArrayList<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();
					pairs.add(new BasicNameValuePair("name", sick));
					pairs.add(new BasicNameValuePair("de", de));
					//pairs.add(new BasicNameValuePair("department", password));
					//Log.i("system.out", name+password+"post_PHP");
					try {
						UrlEncodedFormEntity p_entity = new UrlEncodedFormEntity(pairs, "UTF8");
						httpPost.setEntity(p_entity);
						HttpResponse response = client.execute(httpPost);
						// i=response.getStatusLine().getStatusCode();
				        HttpEntity entity = response.getEntity();
				        InputStream content = entity.getContent();
				       convertStreamToString cStreamToString=new convertStreamToString();
				  String returnConnection = cStreamToString.convertStreamToString(content); 

			
				      if (returnConnection.equals("Yes")) {
				    	    
				    	  Fragment sell=new indexFrament();
							FragmentManager sManager = getFragmentManager();
					        sManager.beginTransaction().replace(R.id.content_frame, sell).commit();
					}else {
						Log.v("System.out", "fail");
						//Toast.makeText(getActivity().getApplicationContext(), "登录失败", Toast.LENGTH_SHORT).show();
					}
					} catch (Exception e) {
						e.printStackTrace();
					}
						}
					}).start();
					
				}
			}
				).setNegativeButton("取消", null).show();	

				
				
			}
		});
				
		refuse.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

		
					new AlertDialog.Builder(view.getContext()).setTitle("提示").setMessage("残忍抛弃！").setPositiveButton("确定",
		        			 new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						new Thread(new Runnable() {
							
							@Override
							public void run() {
						
						DefaultHttpClient client = new DefaultHttpClient();//http客户端
						HttpPost httpPost = new HttpPost(getString(R.string.site)+"refuse.php");
						ArrayList<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();
						pairs.add(new BasicNameValuePair("name", sick));
						//pairs.add(new BasicNameValuePair("department", password));
						//Log.i("system.out", name+password+"post_PHP");
						try {
							UrlEncodedFormEntity p_entity = new UrlEncodedFormEntity(pairs, "UTF8");
							httpPost.setEntity(p_entity);
							HttpResponse response = client.execute(httpPost);
							// i=response.getStatusLine().getStatusCode();
					        HttpEntity entity = response.getEntity();
					        InputStream content = entity.getContent();
					       convertStreamToString cStreamToString=new convertStreamToString();
					  String returnConnection = cStreamToString.convertStreamToString(content); 

				
					      if (returnConnection.equals("Yes")) {
					    	    
					    	  Fragment indexFragment=new indexFrament();
								FragmentManager iManager = getFragmentManager();
						        iManager.beginTransaction().replace(R.id.content_frame, indexFragment).commit();
						}else {
							Log.v("System.out", "fail");
							//Toast.makeText(getActivity().getApplicationContext(), "登录失败", Toast.LENGTH_SHORT).show();
						}
						} catch (Exception e) {
							e.printStackTrace();
						}
							}
						}).start();
						
					}
				}
					).setNegativeButton("取消", null).show();	

					
					
				}
				
			
		});
		
		
		
		
		
		
		
		
		return view;
	}
	 private void asyncloadImage(ImageView iv_header, String path) {
	       // ContactService service = new ContactService();
	        AsyncImageTask task = new AsyncImageTask(iv_header);
	        task.execute(path);
	    }
	 private final class AsyncImageTask extends AsyncTask<String, Integer,Bitmap> {
		 
	       //private ContactService service;
	        private ImageView iv_header;
	 
	        public AsyncImageTask(ImageView iv_header) {
	     
	            this.iv_header = iv_header;
	        }
	 
	        // 后台运行的子线程子线程
	
	        protected Bitmap doInBackground(String... params) {
	        	HttpClient client = new DefaultHttpClient();
			       // publishProgress(30);
			      //  HttpGet hg = new HttpGet(params[0]);//获取csdn的logo
			        HttpPost httpPost = new HttpPost(params[0]);
			        final Bitmap bm;
			        try {
			        	Log.v("image","图片获取---"+params[0]);
			            HttpResponse hr = client.execute(httpPost);
			            Log.v("image", "HTTP");
			            bm = BitmapFactory.decodeStream(hr.getEntity().getContent());
			        } catch (Exception e) {
			        	Log.v("image","图片获取失败");
			            return null;
			        }
			        Log.v("image","图片获取结束");
			       
			        return bm;
	        }
	 
	        // 这个放在在ui线程中执行
	        protected void onPostExecute(Bitmap result) {
	            //super.onPostExecute(result); 
	            // 完成图片的绑定
	            if (iv_header != null && result != null) {
	                iv_header.setImageBitmap(result);
	            }
	        }
	    }
	 private void asyncloadString(TextView date,TextView iv_header, String name) {
	       // ContactService service = new ContactService();
	        AsyncStringTask task = new AsyncStringTask(date,iv_header);
	        task.execute(name);
	    }
	 private final class AsyncStringTask extends AsyncTask<String, Integer,String> {
		 private TextView tv_money;
		private TextView date;
		 public  AsyncStringTask(TextView date,TextView tv_money) {
			this.tv_money=tv_money;
			this.date=date;
		}
		 protected String doInBackground(String... params) {
			 final String num;
			 HttpClient client = new DefaultHttpClient();
			 //取出string。xml中的数据
		    String site=tv_money.getContext().getString(R.string.site)+"search_department.php";
		     Log.v("test===", site);
			 HttpPost httpPost = new HttpPost(site);
			 ArrayList<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();
				pairs.add(new BasicNameValuePair("id",params[0]));
				try {
		        	UrlEncodedFormEntity p_entity = new UrlEncodedFormEntity(pairs, "utf8");
					httpPost.setEntity(p_entity);
						HttpResponse response = client.execute(httpPost); 
						HttpEntity entity = response.getEntity();
				        InputStream content = entity.getContent();
					       convertStreamToString cStreamToString=new convertStreamToString();
					   num= cStreamToString.convertStreamToString(content); 

		        } catch (Exception e) {
		        	Log.v("image","图片获取失败");
		            return null;
		        }
		        Log.v("image","图片获取结束");
		       
		        return num;
		 }
		 protected void onPostExecute(String result) {
	            //super.onPostExecute(result); 
	            // 完成图片的绑定
	            if (tv_money != null && result != null) {
	            	Log.v("result", result);
	            	String time=result.substring(0, 10);
	            	String start=result.replace(time, "");
	            	String temp = start.replace("\\n", "\n");
	                tv_money.setText("科室："+"\n"+temp);
	                date.setText("日期："+time);
	            }
	        }
	 }
	 private void asyncloadold(TextView iv_header, String name) {
	       // ContactService service = new ContactService();
	        AsyncStringold task = new AsyncStringold(iv_header);
	        task.execute(name);
	    }
	 private final class AsyncStringold extends AsyncTask<String, Integer,String> {
		 private TextView tv_money;
		private TextView date;
		 public  AsyncStringold(TextView tv_money) {
			this.tv_money=tv_money;
			this.date=date;
		}
		 protected String doInBackground(String... params) {
			 final String num;
			 HttpClient client = new DefaultHttpClient();
			 //取出string。xml中的数据
		    String site=tv_money.getContext().getString(R.string.site)+"old_de.php";
		     Log.v("test===", site);
			 HttpPost httpPost = new HttpPost(site);
			 ArrayList<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();
				pairs.add(new BasicNameValuePair("id",params[0]));
				try {
		        	UrlEncodedFormEntity p_entity = new UrlEncodedFormEntity(pairs, "utf8");
					httpPost.setEntity(p_entity);
						HttpResponse response = client.execute(httpPost); 
						HttpEntity entity = response.getEntity();
				        InputStream content = entity.getContent();
					       convertStreamToString cStreamToString=new convertStreamToString();
					   num= cStreamToString.convertStreamToString(content); 

		        } catch (Exception e) {
		        	Log.v("image","图片获取失败");
		            return null;
		        }
		        Log.v("image","图片获取结束");
		       
		        return num;
		 }
		 protected void onPostExecute(String result) {
	            //super.onPostExecute(result); 
	            // 完成图片的绑定
	            if (tv_money != null && result != null) {
	            	Log.v("result", result);
	            	
	            	String temp = result.replace("\\n", "\n");
	                tv_money.setText(temp);

	            }
	        }
	 }
}
