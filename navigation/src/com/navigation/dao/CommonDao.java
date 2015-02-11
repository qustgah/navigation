package com.navigation.dao;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.navigation.HeadActivity;
import com.navigation.MainActivity;
import com.lt.navigation.speeddriftracing.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.CountDownTimer;

public class CommonDao {
	/**
	 * ��ȡjson�ַ���
	 * @param path
	 *            ��ַ
	 * @return json
	 * @throws Exception
	 *             �������Ӵ���
	 */
	public static String getDataFromServer(String path) throws Exception {
		URL url = new URL(path);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setAllowUserInteraction(true);
		conn.setAllowUserInteraction(true);
		conn.setConnectTimeout(5 * 1000);// �������ӳ�ʱ
		conn.setRequestMethod("GET");// ��get��ʽ��������
		if (conn.getResponseCode() != 200)
			throw new RuntimeException("����urlʧ��");
		InputStream is = conn.getInputStream();// �õ����緵�ص�������
		String result = readData(is, "utf-8");
		conn.disconnect();
		return result;
	} 
	/**
	 * ��������������ȡ�ַ���
	 * @param inSream ����������
	 * @param charsetName ���ݵı����ʽ
	 * @return �ַ���
	 * @throws IOException IO�쳣
	 */
	public static String readData(InputStream inSream, String charsetName) throws IOException {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = -1;
		while ((len = inSream.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		byte[] data = outStream.toByteArray();
		outStream.close();
		inSream.close();
		return new String(data, charsetName);
	}
	/**
	 * ��json�ַ���ת����JSONȻ���Ϊ��������
	 * @param data   json�ַ���
	 * @return       List<Map<String, Object>>
	 * @throws 		 JSONException 
	 */
	@SuppressWarnings("unchecked")
	public static List<Map<String, Object>> StringToJson(String data) throws JSONException{
		ArrayList<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		JSONObject ja1 = new JSONObject(data);
		String jsonstr = ja1.getString("jsonstr");
		JSONArray jsonArray = new JSONArray(jsonstr); 
		JSONObject jsonObj;
		for(int i = 0 ;i<jsonArray.length();i++){
			 jsonObj = (JSONObject)jsonArray.get(i);
             Iterator<String> keyIter= jsonObj.keys();  
             Map<String, Object> valueMap = new HashMap<String, Object>();  
             String key ;
             while (keyIter.hasNext()) {  
            	 key = keyIter.next();
                 valueMap.put(key, jsonObj.get(key));//��ȡjson�е�key,value����Map������
             }
             list.add(valueMap);
		}
		return list;
	}
	
	public static Date StringToDate(String str){
		SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd");
		Date d = null;
		try {
			d = sim.parse(str);
		} catch (ParseException e) {
			 System.out.println(e.getMessage().toString());
		}
		return d;
	}
	
	public static  boolean checkNetwork(Context context) {
        ConnectivityManager conn = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo net = conn.getActiveNetworkInfo();
        if (net != null && net.isConnected()) {
            return true;
        }
        return false;
    }
	public static void  netConnectDialog(final Context context){
		Builder builder = new Builder(context);
		builder.setTitle("Set up the network connection");
		builder.setMessage("Whether to set up the network");
		builder.setPositiveButton("YES", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				Intent intent = new Intent("android.settings.WIRELESS_SETTINGS");
				context.startActivity(intent);
			}
		});
		builder.setNegativeButton("NO", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				new CountDownTimer(2000,1000){
					@Override
					public void onTick(long millisUntilFinished) {
					}
					@Override
					public void onFinish() {
						Intent intent = new Intent();
						intent.setClass(context, HeadActivity.class);
						context.startActivity(intent);
						int VERSION=Integer.parseInt(android.os.Build.VERSION.SDK);
						if(VERSION >= 5){
							((Activity) context).overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
						}
						((Activity) context).finish();
					}
				}.start();
			}
		}); 
		 AlertDialog dialog = builder.create();    
		 dialog.show(); 
	}
}

























