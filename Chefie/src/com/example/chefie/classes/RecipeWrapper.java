package com.example.chefie.classes;

import java.io.Serializable;
import java.util.ArrayList;

public class RecipeWrapper implements Serializable{
	private ArrayList<Recipe> appetizerList;
	private ArrayList<Recipe> breakBrunchList;
	private ArrayList<Recipe> chickenList;
	private ArrayList<Recipe> dessertsList;
	private ArrayList<Recipe> mainDishList;
	private ArrayList<Recipe> pastaList;
	private ArrayList<Recipe> saladList;
	private ArrayList<Recipe> vegetarianList;
	
	
	public RecipeWrapper(){
		this.appetizerList = new ArrayList<Recipe>();
		this.breakBrunchList = new ArrayList<Recipe>();
		this.chickenList = new ArrayList<Recipe>();
		this.dessertsList = new ArrayList<Recipe>();
		this.mainDishList = new ArrayList<Recipe>();
		this.pastaList = new ArrayList<Recipe>();
		this.saladList = new ArrayList<Recipe>();
		this.vegetarianList = new ArrayList<Recipe>();
		
	}


	public ArrayList<Recipe> getAppetizerList() {
		return appetizerList;
	}


	public void setAppetizerList(ArrayList<Recipe> appetizerList) {
		this.appetizerList = appetizerList;
	}


	public ArrayList<Recipe> getBreakBrunchList() {
		return breakBrunchList;
	}


	public void setBreakBrunchList(ArrayList<Recipe> breakBrunchList) {
		this.breakBrunchList = breakBrunchList;
	}


	public ArrayList<Recipe> getChickenList() {
		return chickenList;
	}


	public void setChickenList(ArrayList<Recipe> chickenList) {
		this.chickenList = chickenList;
	}


	public ArrayList<Recipe> getDessertsList() {
		return dessertsList;
	}


	public void setDessertsList(ArrayList<Recipe> dessertsList) {
		this.dessertsList = dessertsList;
	}





	public ArrayList<Recipe> getMainDishList() {
		return mainDishList;
	}


	public void setMainDishList(ArrayList<Recipe> mainDishList) {
		this.mainDishList = mainDishList;
	}


	public ArrayList<Recipe> getPastaList() {
		return pastaList;
	}


	public void setPastaList(ArrayList<Recipe> pastaList) {
		this.pastaList = pastaList;
	}


	public ArrayList<Recipe> getSaladList() {
		return saladList;
	}


	public void setSaladList(ArrayList<Recipe> saladList) {
		this.saladList = saladList;
	}





	public ArrayList<Recipe> getVegetarianList() {
		return vegetarianList;
	}


	public void setVegetarianList(ArrayList<Recipe> vegetarianList) {
		this.vegetarianList = vegetarianList;
	}
	
	
}
