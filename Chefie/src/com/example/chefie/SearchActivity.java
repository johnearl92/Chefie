package com.example.chefie;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.chefie.adapters.RecipeArrayAdapter;
import com.example.chefie.classes.ProcIngredient;
import com.example.chefie.classes.Recipe;
import com.example.chefie.classes.RecipeWrapper;

public class SearchActivity extends ListActivity {
	private RecipeWrapper recipeWrapper;
	private ArrayList<Recipe> allRecipes;
	private ArrayList<Recipe> result;
	private Recipe recipe;
	private RecipeArrayAdapter adapter;
	private ObjectOutputStream out;
	private EditText que;
	private ImageButton searchButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.v("searcher", this.getClass().getName() + " onCreate invoke");
		Intent intent = getIntent();
		recipeWrapper = (RecipeWrapper) intent.getSerializableExtra(MainActivity.EXTRA_WRAPPER);
		allRecipes = new ArrayList<Recipe>();
		for(Recipe r:recipeWrapper.getAppetizerList()){
			allRecipes.add(r);
		}
		
		for(Recipe r:recipeWrapper.getBreakBrunchList()){
			
				allRecipes.add(r);
			
		}
		//get the favorites in chicken
		for(Recipe r:recipeWrapper.getChickenList()){
			
				allRecipes.add(r);
			
		}
		
		for(Recipe r:recipeWrapper.getDessertsList()){
			allRecipes.add(r);
			
		}
		

		
		for(Recipe r:recipeWrapper.getMainDishList()){
			allRecipes.add(r);
			
		}
		
		for(Recipe r:recipeWrapper.getPastaList()){
			allRecipes.add(r);
			
		}
		
		for(Recipe r:recipeWrapper.getSaladList()){
			allRecipes.add(r);
			
		}
		

		
		for(Recipe r:recipeWrapper.getVegetarianList()){
			allRecipes.add(r);
			
		}
		Log.v("searcher", " creating header");
		View header = getLayoutInflater().inflate(R.layout.actionbar_search, null);
		getListView().addHeaderView(header);
		Log.v("searcher", "header created" + allRecipes.get(0).getRecipeName());
		
		que =(EditText) findViewById(R.id.query);
		searchButton = (ImageButton) findViewById(R.id.go_search);
		
		result =allRecipes;
		adapter = new RecipeArrayAdapter(this, result);
		setListAdapter(adapter);
		Log.v("searcher", this.getClass().getName() + " onCreate end");
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId()){
		case R.id.action_favorite:
			Intent intent = new Intent(this,FavoritesActivity.class);
			intent.putExtra(MainActivity.EXTRA_WRAPPER, this.recipeWrapper);
			startActivity(intent);
			finish();
			return true;
		case R.id.action_list:
			Intent intentList = new Intent(this,MainActivity.class);
			intentList.putExtra(MainActivity.EXTRA_WRAPPER, this.recipeWrapper);
			startActivity(intentList);
			finish();
			return true;
		default:
				return super.onOptionsItemSelected(item);
		}
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		saver();
	}
	
	public void saver(){
		Log.v("search", "destroyed");
		try {
			this.out = new ObjectOutputStream(openFileOutput(MainActivity.fileName, Context.MODE_PRIVATE));
			out.writeObject(recipeWrapper);
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.v("search", "destroyed end");
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		Intent intent = new Intent(this,RecipeActivity.class);
		intent.putExtra(MainActivity.EXTRA_RECIPE, result.get(position-1));
		startActivityForResult(intent, MainActivity.RECIPE_REQUEST_CODE);
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == RESULT_OK && requestCode == MainActivity.RECIPE_REQUEST_CODE){
			if(data.hasExtra(MainActivity.EXTRA_RECIPE)){
				recipe =(Recipe) data.getSerializableExtra(MainActivity.EXTRA_RECIPE);
				if(recipe.getCategory()==1){
					for(int i=0;i<recipeWrapper.getAppetizerList().size();i++){
						if(recipe.getRecipeName().equals(recipeWrapper.getAppetizerList().get(i).getRecipeName())){
							Log.v("search", "heloo");
							recipeWrapper.getAppetizerList().remove(i);
							recipeWrapper.getAppetizerList().add(i, recipe);
							break;
						}
					}
				}
				
				if(recipe.getCategory()==2){
					for(int i=0;i<recipeWrapper.getBreakBrunchList().size();i++){
						if(recipe.getRecipeName().equals(recipeWrapper.getBreakBrunchList().get(i).getRecipeName())){
							Log.v("search", "heloo");
							recipeWrapper.getBreakBrunchList().remove(i);
							recipeWrapper.getBreakBrunchList().add(i, recipe);
							break;
						}
					}
				}
				if(recipe.getCategory()==3){
					for(int i=0;i<recipeWrapper.getChickenList().size();i++){
						if(recipe.getRecipeName().equals(recipeWrapper.getBreakBrunchList().get(i).getRecipeName())){
							Log.v("search", "heloo");
							recipeWrapper.getBreakBrunchList().remove(i);
							recipeWrapper.getBreakBrunchList().add(i, recipe);
							break;
						}
					}
				}
				
				if(recipe.getCategory()==4){
					for(int i=0;i<recipeWrapper.getDessertsList().size();i++){
						if(recipe.getRecipeName().equals(recipeWrapper.getDessertsList().get(i).getRecipeName())){
							Log.v("search", "heloo");
							recipeWrapper.getDessertsList().remove(i);
							recipeWrapper.getDessertsList().add(i, recipe);
							break;
						}
					}
				}
				
				if(recipe.getCategory()==5){
					for(int i=0;i<recipeWrapper.getMainDishList().size();i++){
						if(recipe.getRecipeName().equals(recipeWrapper.getMainDishList().get(i).getRecipeName())){
							Log.v("search", "heloo");
							recipeWrapper.getPastaList().remove(i);
							recipeWrapper.getPastaList().add(i, recipe);
							break;
						}
					}
				}
				
				if(recipe.getCategory()==6){
					for(int i=0;i<recipeWrapper.getPastaList().size();i++){
						if(recipe.getRecipeName().equals(recipeWrapper.getPastaList().get(i).getRecipeName())){
							Log.v("search", "heloo");
							recipeWrapper.getPastaList().remove(i);
							recipeWrapper.getPastaList().add(i, recipe);
							break;
						}
					}
				}
				
				if(recipe.getCategory()==7){
					for(int i=0;i<recipeWrapper.getSaladList().size();i++){
						if(recipe.getRecipeName().equals(recipeWrapper.getSaladList().get(i).getRecipeName())){
							Log.v("search", "heloo");
							recipeWrapper.getSaladList().remove(i);
							recipeWrapper.getSaladList().add(i, recipe);
							break;
						}
					}
				}
				
				if(recipe.getCategory()==8){
					for(int i=0;i<recipeWrapper.getVegetarianList().size();i++){
						if(recipe.getRecipeName().equals(recipeWrapper.getVegetarianList().get(i).getRecipeName())){
							Log.v("search", "heloo");
							recipeWrapper.getVegetarianList().remove(i);
							recipeWrapper.getVegetarianList().add(i, recipe);
							break;
						}
					}
				}
			
				for(int i=0;i<allRecipes.size();i++){
					if(recipe.getRecipeName().equals(allRecipes.get(i).getRecipeName())){
						Log.v("search", "yesss");
						allRecipes.remove(i);
						allRecipes.add(i, recipe);
						break;
					}
				}
				
				for(int i=0;i<result.size();i++){
					if(recipe.getRecipeName().equals(result.get(i).getRecipeName())){
						Log.v("search", "yesss");
						result.remove(i);
						result.add(i, recipe);
						break;
					}
				}
				adapter.notifyDataSetChanged();
				saver();
			}
		}
	}
	
	public void onSearchClick(View v){
		Log.v("search", this.getClass().getName() + "onSearch invoke");
		String query = que.getText().toString();
		result = new ArrayList<Recipe>();
		String ings;
		for(Recipe r:allRecipes){
			ings="";
			for(ProcIngredient p:r.getIngredients())
			{	Log.v("search", "hiiiiiii");
				ings=ings+p.getProcIngred();
			}
			Log.v("search", ""+r.getRecipeName().toLowerCase().contains(query.toLowerCase()));
			if(r.getRecipeName().toLowerCase().contains(query.toLowerCase())||ings.toLowerCase().contains(query)){
				Log.v("search", "Helooooooo");
				result.add(r);
			}
		}
		if(result.size()>0){
			adapter = new RecipeArrayAdapter(this, result);
			setListAdapter(adapter);
		}
		Log.v("search", this.getClass().getName() + "onSearch end");
	}
}
