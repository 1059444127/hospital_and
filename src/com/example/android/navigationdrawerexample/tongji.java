package com.example.android.navigationdrawerexample;

import java.io.InputStream;
import java.security.PublicKey;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class tongji extends Fragment {
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.tongji, container,false);
		final ImageView image=(ImageView)view.findViewById(R.id.imageView1);
		final TextView name=(TextView)view.findViewById(R.id.textView2);
		final TextView month_num=(TextView)view.findViewById(R.id.textView3);
		final TextView day_num=(TextView)view.findViewById(R.id.textView4);
		final TextView introudce=(TextView)view.findViewById(R.id.textView5);
		RadioGroup group = (RadioGroup)view.findViewById(R.id.select);
	    group.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				 //��ȡ������ѡ�����ID
				int radioButtonId = group.getCheckedRadioButtonId();
				//����ID��ȡRadioButton��ʵ��
				RadioButton rb = (RadioButton)view.findViewById(radioButtonId);
				if (rb.getText().equals("����")) {
					AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
	                builder.setIcon(R.drawable.ic_launcher);
	                builder.setTitle("ѡ��һ������");
	                //    ָ�������б����ʾ����
	                final String[] cities = {"�ǿ�", "����", "�ۿ�", "�ǿ�", "�ֿ�"};
	                //    ����һ���������б�ѡ����
	                builder.setItems(cities, new DialogInterface.OnClickListener()
	                {
	                    @Override
	                    public void onClick(DialogInterface dialog, int which)
	                    {
	                        Toast.makeText(getActivity(), "ѡ��ĳ���Ϊ��" + cities[which], Toast.LENGTH_SHORT).show();
	                        gengxin(image,name,month_num,introudce,day_num,cities[which],"0");
	                    }

				

				
	                });
	                builder.show();
				}
				if (rb.getText().equals("����")) {
					AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
					builder.setIcon(R.drawable.ic_launcher);
					builder.setTitle("���������ҽ����Ϣ");
					View view = LayoutInflater.from(getActivity()).inflate(R.layout.geren, null);
					builder.setView(view);
					final EditText username = (EditText)view.findViewById(R.id.username);
	                final EditText password = (EditText)view.findViewById(R.id.password);
	                builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener()
	                {
	                    @Override
	                    public void onClick(DialogInterface dialog, int which)
	                    {
	                        String a = username.getText().toString().trim();
	                        String b = password.getText().toString().trim();
	                        //    ��������û����������ӡ����
	                        Toast.makeText(getActivity(), "ҽ������: " + a + ", ����: " + b, Toast.LENGTH_SHORT).show();
	                        gengxin(image,name,month_num,introudce,day_num,a,b);
	                        
	                        
	                        
	                        
	                        
	                        
	                        
	                        
	                    } 
	                });
	                builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener()
	                {
	                    @Override
	                    public void onClick(DialogInterface dialog, int which)
	                    {
	                        
	                    }
	                });
	                builder.show();
	            
	        }
				
			}
		});
		
		
		
		
		return view;
	}
	public void gengxin(ImageView image, TextView name,
			TextView month_num,TextView intr, TextView day_num,
			String string,String b) {
		if (b.equals("0")) {
			String[] introuce=getResources().getStringArray(R.array.introuce);
			if (string.equals("�ǿ�")) {
				image.setImageResource(R.drawable.guke);
				intr.setText(introuce[0]);
			}
			if (string.equals("����")) {
				image.setImageResource(R.drawable.er);
				intr.setText(introuce[2]);
			}
			if (string.equals("�ۿ�")) {
				image.setImageResource(R.drawable.eye);
				intr.setText(introuce[1]);
			}
			
			name.setText("���ҵ�����: "+string);
			loading load=new loading(month_num,day_num);
			load.execute(string);
		}
		else {
			if (string.equals("zyz")&&b.equals("eye")) {
				image.setImageResource(R.drawable.zhangsan);
				intr.setText("��ʱ�޽��ܣ���������....");
			}
			name.setText("ҽ�����֣�"+string+""+"("+b+")");
			load_self load=new load_self(month_num,day_num);
			load.execute(string,b);
		}
		}
	private final class loading extends AsyncTask<String, Integer,String> {
		
		 private TextView month_num;
		 private TextView day_num;
		 
		 public loading(TextView month_num, TextView day_num){
			
			this.month_num=month_num;
			this.day_num=day_num;
			 }
		 protected String doInBackground(String... params) {
			 final String num;
			 Log.v("tongji", "doInBackground");
			 HttpClient client = new DefaultHttpClient();
			 //ȡ��string��xml�е�����
		    String site=month_num.getContext().getString(R.string.site)+"tongji.php";
		    Log.v("tongji", site);
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
                       Log.v("tongji", num);
		        } catch (Exception e) {
		        	Log.v("image","ͼƬ��ȡʧ��");
		            return null;
		        }
		        Log.v("image","ͼƬ��ȡ����");
		       
		        return num;
		 }
	 protected void onPostExecute(String result) {
	            //super.onPostExecute(result); 
	            // ���ͼƬ�İ�
	            if (result != null) {
	               String num_day=result.substring(0, 3);
	               Log.v("day_num", num_day);
	               day_num.setText("���¹�������"+num_day);
	               String num_month=result.substring(3,6);
	               month_num.setText("���չ�������"+num_month);
	            }
	        }
}
	private final class load_self extends AsyncTask<String, Integer,String> {
		
		 private TextView month_num;
		 private TextView day_num;
		 
		 public load_self(TextView month_num, TextView day_num){
			
			this.month_num=month_num;
			this.day_num=day_num;
			 }
		 protected String doInBackground(String... params) {
			 final String num;
			 Log.v("geren", "doInBackground");
			 HttpClient client = new DefaultHttpClient();
			 //ȡ��string��xml�е�����
		    String site=month_num.getContext().getString(R.string.site)+"geren.php";
		    Log.v("tongji", site);
		    HttpPost httpPost = new HttpPost(site);
			 ArrayList<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();
				pairs.add(new BasicNameValuePair("id",params[0]));
				pairs.add(new BasicNameValuePair("department",params[1]));
				try {
		        	UrlEncodedFormEntity p_entity = new UrlEncodedFormEntity(pairs, "utf8");
					httpPost.setEntity(p_entity);
						HttpResponse response = client.execute(httpPost); 
						HttpEntity entity = response.getEntity();
				        InputStream content = entity.getContent();
					       convertStreamToString cStreamToString=new convertStreamToString();
					   num= cStreamToString.convertStreamToString(content); 
                      Log.v("geren", num);
		        } catch (Exception e) {
		        	Log.v("image","ͼƬ��ȡʧ��");
		            return null;
		        }
		        Log.v("image","ͼƬ��ȡ����");
		       
		        return num;
		 }
	 protected void onPostExecute(String result) {
	            //super.onPostExecute(result); 
	            // ���ͼƬ�İ�
	            if (result != null) {
	               String num_day=result.substring(0, 3);
	               Log.v("day_num", num_day);
	               day_num.setText("���¹�������"+num_day);
	               String num_month=result.substring(result.length()-3,result.length());
	               month_num.setText("���չ�������"+num_month);
	            }
	        }
}
}