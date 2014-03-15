package com.example.chefie.classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Recipe implements Serializable{
	private String recipeName;
	private String recipeDescription;
	private boolean favorite;
	private int recipeImage;
	private ArrayList<ProcIngredient> ingredients;
	private ArrayList<ProcIngredient> procedures;
	private int category;
	public int getCategory() {
		return category;
	}
	public void setCategory(int category) {
		this.category = category;
	}
	public Recipe() {
		// TODO Auto-generated constructor stub
		ingredients = new ArrayList<ProcIngredient>();
		procedures = new ArrayList<ProcIngredient>();
	}
	public String getRecipeName() {
		return recipeName;
	}
	public void setRecipeName(String recipeName) {
		this.recipeName = recipeName;
	}
	public String getDescription() {
		return recipeDescription;
	}
	public void setDescription(String recipeDescription) {
		this.recipeDescription = recipeDescription;
	}
	public boolean isFavorite() {
		return favorite;
	}
	public void setFavorite(boolean favorite) {
		this.favorite = favorite;
	}
	public int getRecipeImage() {
		return recipeImage;
	}
	public void setRecipeImage(int recipeImage) {
		this.recipeImage = recipeImage;
	}
	public ArrayList<ProcIngredient> getIngredients() {
		return ingredients;
	}
	public void setIngredients(ArrayList<ProcIngredient> ingredients) {
		this.ingredients = ingredients;
	}
	public ArrayList<ProcIngredient> getProcedures() {
		return procedures;
	}
	public void setProcedures(ArrayList<ProcIngredient> procedures) {
		this.procedures = procedures;
	}
	
}
