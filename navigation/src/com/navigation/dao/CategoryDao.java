package com.navigation.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.navigation.bean.CateAndGames;
import com.navigation.bean.Category;
import com.navigation.bean.Game;
import com.navigation.bean.Picture;

public class CategoryDao {

	public static ArrayList<Category> getCategorys(List<Map<String, Object>> list) throws JSONException{
		ArrayList<Category> clist = new ArrayList<Category>();
		for(Map<String,Object> map:list){
			int id = Integer.parseInt(String.valueOf(map.get("id")));
			String name = String.valueOf(map.get("name"));
			int net = Integer.parseInt(String.valueOf(map.get("net"))); 
			Category category = new Category(id,name,net); 
			clist.add(category);
		}
		return clist;
	}
	public static ArrayList<CateAndGames> getCateAndGames(List<Map<String, Object>> list) throws JSONException{
		ArrayList<CateAndGames> cateAndGamelist = new ArrayList<CateAndGames>();
		for(Map<String,Object> map:list){
			ArrayList<Picture> pictures = new ArrayList<Picture>();
			CateAndGames cateAndGames = new CateAndGames();
			 Category category = new Category();
			 JSONObject jsonObject = new JSONObject(String.valueOf(map.get("category")));
			 category = new Category(jsonObject.getInt("id"), jsonObject.getString("name"), jsonObject.getInt("net"));
			 cateAndGames.setCategory(category);
			 JSONArray jsonArray = new JSONArray(String.valueOf(map.get("games"))); 
			 JSONObject jsonObj;
			 for(int i = 0 ;i<jsonArray.length();i++){
					Picture pic = new Picture();
					Game game = new Game();
					jsonObj = (JSONObject)jsonArray.get(i);
					pic.setUrl(jsonObj.getString("url")); 
					JSONObject json = new JSONObject(jsonObj.getString("gameid"));
					game.setId(json.getInt("id"));
					game.setName(json.getString("name"));
					pic.setGameid(game); 
					pictures.add(pic);
			} 
			cateAndGames.setGames(pictures); 
			cateAndGamelist.add(cateAndGames);
		} 
		return cateAndGamelist;
	}
}
