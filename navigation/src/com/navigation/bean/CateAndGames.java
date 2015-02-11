package com.navigation.bean;
 
import java.util.List;  
public class CateAndGames {
	
	public Category category ;
	public List<Picture> games ;
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public List<Picture> getGames() {
		return games;
	}
	public void setGames(List<Picture> games) {
		this.games = games;
	}
}
