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
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.chefie.adapters.RecipeArrayAdapter;
import com.example.chefie.classes.ProcIngredient;
import com.example.chefie.classes.Recipe;
import com.example.chefie.classes.RecipeWrapper;

public class NewSearchActivity extends ListActivity {
	private RecipeWrapper recipeWrapper;
	private ArrayList<Recipe> result;
	private ArrayList<Recipe> allRecipe;
	private RecipeArrayAdapter adapter;
	private ObjectOutputStream out;
	private Recipe recipe;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View v = getLayoutInflater().inflate(R.layout.actionbar_search, null);
		getListView().addHeaderView(v);
		Intent intent = getIntent();
		recipeWrapper = (RecipeWrapper) intent.getSerializableExtra(MainActivity.EXTRA_WRAPPER);
		allRecipe = new ArrayList<Recipe>();

		for(Recipe r:recipeWrapper.getAppetizerList()){
			allRecipe.add(r);
		}
		//do for the rest
		
		result = allRecipe;
		displayResult(result);
	}

	private void displayResult(ArrayList<Recipe> r) {
		// TODO Auto-generated method stub
		setListAdapter(new RecipeArrayAdapter(this, r));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.d(MainActivity.LOGGING_KEY, "destroyed");
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
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		Log.v(MainActivity.SECOND_KEY, this.getClass().getName() + "clicking and creating intent");
		Intent intent = new Intent(this,RecipeActivity.class);
		Log.v(MainActivity.SECOND_KEY, this.getClass().getName() + " created intent");
		intent.putExtra(MainActivity.EXTRA_RECIPE, result.get(position));
		Log.v(MainActivity.SECOND_KEY, this.getClass().getName() + " passed and extra" );
		startActivityForResult(intent, MainActivity.RECIPE_REQUEST_CODE);
		Log.v(MainActivity.SECOND_KEY, this.getClass().getName() + "creating intent end");
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if(resultCode == RESULT_OK && requestCode == MainActivity.RECIPE_REQUEST_CODE){
			if(data.hasExtra(MainActivity.EXTRA_RECIPE)){
			
				recipe =(Recipe) data.getSerializableExtra(MainActivity.EXTRA_RECIPE);
				for(ProcIngredient i:recipe.getIngredients()){
					if(i.isCheck()){
						Log.v("search", "true");
					}
					else{
						Log.v("search", "false");
					}
				}
				
				adapter.notifyDataSetChanged();
			}
		}
	}
	
	

}
