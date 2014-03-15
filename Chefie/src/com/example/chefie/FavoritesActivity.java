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
import android.widget.ListView;
import android.widget.Toast;

import com.example.chefie.adapters.FavoritesArrayAdapter;
import com.example.chefie.classes.ProcIngredient;
import com.example.chefie.classes.Recipe;
import com.example.chefie.classes.RecipeWrapper;

public class FavoritesActivity extends ListActivity {
	private RecipeWrapper recipeWrapper;
	private ArrayList<Recipe> favorites;
	private ObjectOutputStream out;
	private FavoritesArrayAdapter adapter;
	private Recipe recipe;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		recipeWrapper = (RecipeWrapper) intent.getSerializableExtra(MainActivity.EXTRA_WRAPPER);
		favorites = new ArrayList<Recipe>();
		for(Recipe r:recipeWrapper.getAppetizerList()){
			if(r.isFavorite()){
				favorites.add(r);
			}
		}
		//get the favorites in break
		for(Recipe r:recipeWrapper.getBreakBrunchList()){
			if(r.isFavorite()){
				favorites.add(r);
			}
		}
		//get the favorites in chicken
		for(Recipe r:recipeWrapper.getChickenList()){
			if(r.isFavorite()){
				favorites.add(r);
			}
		}
		
		for(Recipe r:recipeWrapper.getDessertsList()){
			if(r.isFavorite()){
				favorites.add(r);
			}
		}
		

		
		for(Recipe r:recipeWrapper.getMainDishList()){
			if(r.isFavorite()){
				favorites.add(r);
			}
		}
		
		for(Recipe r:recipeWrapper.getPastaList()){
			if(r.isFavorite()){
				favorites.add(r);
			}
		}
		
		for(Recipe r:recipeWrapper.getSaladList()){
			if(r.isFavorite()){
				favorites.add(r);
			}
		}
		

		
		for(Recipe r:recipeWrapper.getVegetarianList()){
			if(r.isFavorite()){
				favorites.add(r);
			}
		}
		
		ArrayList<String> titles= new ArrayList<String>();
		for(Recipe x:favorites){
			titles.add(x.getRecipeName());
		}
		if(favorites.size()>0){
		adapter = new FavoritesArrayAdapter(this, favorites);
		setListAdapter(adapter);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		MenuItem item = menu.findItem(R.id.action_favorite);
		item.setEnabled(false);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId()){
		case R.id.action_list:
			Intent intent = new Intent(this,MainActivity.class);
			startActivity(intent);
			finish();

			return true;
			
		case R.id.action_search:
			Intent intentSearch = new Intent(this,SearchActivity.class);
			intentSearch.putExtra(MainActivity.EXTRA_WRAPPER, this.recipeWrapper);
			startActivity(intentSearch);
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

		Log.v(MainActivity.SECOND_KEY, this.getClass().getName() + "clicking and creating intent");
		Intent intent = new Intent(this,RecipeActivity.class);
		Log.v(MainActivity.SECOND_KEY, this.getClass().getName() + " created intent");
		intent.putExtra(MainActivity.EXTRA_RECIPE, favorites.get(position));
		Log.v(MainActivity.SECOND_KEY, this.getClass().getName() + " passed and extra" );
		startActivityForResult(intent, MainActivity.RECIPE_REQUEST_CODE);
		Log.v(MainActivity.SECOND_KEY, this.getClass().getName() + "creating intent end");
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if(resultCode == RESULT_OK && requestCode == MainActivity.RECIPE_REQUEST_CODE){
			if(data.hasExtra(MainActivity.EXTRA_RECIPE)){
				Log.v("search", "sulod sa condition");
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
				
				//do for the other categories
				Log.v("search", ""+favorites.size());
				for(int i=0;i<favorites.size();i++){
					if(recipe.getRecipeName().equals(favorites.get(i).getRecipeName())){
						Log.v("search", "yesss");
						favorites.remove(i);
						favorites.add(i, recipe);
						break;
					}
				}
				for(Recipe r:favorites){
					Log.v("favorites", r.getRecipeName() + " "+r.getIngredients().get(0).isCheck());
				}
				adapter.notifyDataSetChanged();
				saver();
			}
		}
	}

}
