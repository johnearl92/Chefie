package Fragments;

import java.util.ArrayList;

import com.example.chefie.MainActivity;
import com.example.chefie.RecipeActivity;
import com.example.chefie.adapters.RecipeArrayAdapter;
import com.example.chefie.classes.Recipe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

public class BreakBrunchFragment extends ListFragment{
	private ArrayList<Recipe> recipes;
	private Recipe recipe;
	private RecipeArrayAdapter adapter;
	
	public BreakBrunchFragment(ArrayList<Recipe> recipes) {
		// TODO Auto-generated constructor stub
		this.recipes = recipes;
	}
	

	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		Log.v(MainActivity.LOGGING_KEY, this.getClass().getName() + " onActivityCreate invoke");
		ArrayList<String> titles= new ArrayList<String>();
		for(Recipe x:recipes){
			titles.add(x.getRecipeName());
		}
		adapter = new RecipeArrayAdapter(getActivity(), recipes);
		setListAdapter(adapter);

		Log.v(MainActivity.LOGGING_KEY, this.getClass().getName() + " onActivity end");
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		Log.v(MainActivity.SECOND_KEY, this.getClass().getName() + "clicking and creating intent");
		Intent intent = new Intent(getActivity(),RecipeActivity.class);
		Log.v(MainActivity.SECOND_KEY, this.getClass().getName() + " created intent");
		intent.putExtra(MainActivity.EXTRA_RECIPE, recipes.get(position));
		Log.v(MainActivity.SECOND_KEY, this.getClass().getName() + " passed and extra" );
		startActivityForResult(intent, MainActivity.RECIPE_REQUEST_CODE);
		Log.v(MainActivity.SECOND_KEY, this.getClass().getName() + "creating intent end");
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		Log.v("search", this.getClass().getName() + " onActivityResult invoked");
		if(resultCode == MainActivity.RESULT_OK && requestCode == MainActivity.RECIPE_REQUEST_CODE){
			Log.v("search", "onActivity condition");
			if(data.hasExtra(MainActivity.EXTRA_RECIPE)){
				
				recipe =(Recipe) data.getSerializableExtra(MainActivity.EXTRA_RECIPE);
//				for(ProcIngredient i:recipe.getIngredients()){
//					if(i.isCheck()){
//						Log.v("search", "true");
//					}
//					else{
//						Log.v("search", "false");
//					}
//				}
				
				for(int i=0;i<recipes.size();i++){
					if(recipe.getRecipeName().equals(recipes.get(i).getRecipeName())){
						Log.v("search", "yesss");
						recipes.remove(i);
						recipes.add(i, recipe);
						break;
					}
				}
				adapter.notifyDataSetChanged();
			}
		}
		Log.v("search", this.getClass().getName() + " onActivityResult end");
	}
}
