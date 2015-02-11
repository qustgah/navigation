package com.navigation.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;

import com.navigation.bean.Category;
import com.navigation.bean.Game;
import com.navigation.bean.Picture;
import com.navigation.bean.ReleaseMan;
import com.navigation.bean.Store;

/**
 * 获取
 * @author Gaoahui
 */
public class GameDao {
	/**
	 * 转换成picture对象的集合
	 * @param     List<Map<String, Object>> 
	 * @return    ArrayList<Picture>
	 * @throws JSONException
	 */
	public static ArrayList<Picture> getPictures(List<Map<String, Object>> list) throws Exception{
		ArrayList<Picture> piclist = new ArrayList<Picture>();
		for(Map<String,Object> map:list){
			Picture picture = new Picture();
			picture.setDescription(String.valueOf(map.get("description")));
			picture.setId(Integer.parseInt(map.get("id").toString()));
			picture.setName(String.valueOf(map.get("name")));
			picture.setType(String.valueOf(map.get("type")));
			picture.setUrl(String.valueOf(map.get("url")));
			Store store;
			Category category;
			ReleaseMan releaseMan;
			Game game;
			if(map.get("gameid")==null){
				game = new Game();
			}else{
				JSONObject jsonObject = new JSONObject(String.valueOf(map.get("gameid")));
				if(jsonObject.getString("category").equals("null")){
					category = new Category();
				}else{
					JSONObject jsonObject2 = new JSONObject(jsonObject.getString("category"));
					category = new Category(jsonObject2.getInt("id"), jsonObject2.getString("name"), jsonObject2.getInt("net"));
				}
				if(jsonObject.getString("store").equals("null")){
					store = new Store();
				}else{
					JSONObject jsonObject3 = new JSONObject(jsonObject.getString("store"));
					store = new Store(jsonObject3.getInt("id"), jsonObject3.getString("name"), jsonObject3.getString("url"));
				}
				if(jsonObject.getString("releaseMan").equals("null")){
					releaseMan = new ReleaseMan();
				}
				else{
					JSONObject jsonObject4 = new JSONObject(jsonObject.getString("releaseMan"));
					releaseMan = new ReleaseMan(jsonObject4.getInt("id"), jsonObject4.getString("name"));
				} 
				int id = jsonObject.getInt("id");
				String name = jsonObject.getString("name");  
				Date releasetime;
				if(jsonObject.getString("releasetime").equals("null")){
					releasetime = null;
				}else{
					JSONObject json = new JSONObject(jsonObject.getString("releasetime")); 
					Date test = new Date(json.getLong("time"));  
					releasetime = test; 
				}
				String version =jsonObject.getString("versions");
				String description = jsonObject.getString("description");
				String size = jsonObject.getString("size"); 
				String flag = jsonObject.getString("flag");
				String loadnum = jsonObject.getString("loadnum");
				String loadurl = jsonObject.getString("loadurl");
				String belong = jsonObject.getString("belong"); 
				game = new Game(id, name, store, category, releaseMan, releasetime, version, description, size, flag, loadnum, loadurl, belong);
			} 
			picture.setGameid(game);
			piclist.add(picture);
		}
		return piclist;
	}
}
