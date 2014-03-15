package com.example.chefie.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chefie.MainActivity;
import com.example.chefie.R;
import com.example.chefie.RecipeActivity;
import com.example.chefie.classes.Recipe;

public class FavoritesArrayAdapter extends ArrayAdapter<Recipe>{
	

	


	private final Context context;
	private final ArrayList<Recipe> recipes;
	
	public FavoritesArrayAdapter(Context context,ArrayList<Recipe> recipes) {
		super(context,R.layout.list_recipe_row, recipes);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.recipes = recipes;
	}
	
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
	Log.v(MainActivity.LOGGING_KEY, this.getClass().getName() + " getView invoke");
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	Log.v(MainActivity.LOGGING_KEY, this.getClass().getName() + " getView creating rowView");
		View rowView;
		if(convertView==null){
			rowView = inflater.inflate(R.layout.list_recipe_row, parent,false);
		}
		else{
			rowView = convertView;
		}
	Log.v(MainActivity.LOGGING_KEY, this.getClass().getName() + " getView rowView created");
		
	Log.v(MainActivity.LOGGING_KEY, this.getClass().getName() + " getVIew Creating subViews");
		TextView titleview = (TextView) rowView.findViewById(R.id.recipeTitle);
		ImageView imageview = (ImageView) rowView.findViewById(R.id.recipeImage);
		ImageButton star = (ImageButton) rowView.findViewById(R.id.favoritesButton);
	Log.v(MainActivity.LOGGING_KEY, this.getClass().getName() + " getView subViews Created");
		
	Log.v(MainActivity.LOGGING_KEY, this.getClass().getName() + " getVIew adding values to subViews");
			titleview.setText(recipes.get(position).getRecipeName());
		Log.v(MainActivity.LOGGING_KEY, "1 "+ recipes.get(position).getRecipeName());
			imageview.setImageResource(recipes.get(position).getRecipeImage());
		Log.v(MainActivity.LOGGING_KEY, "2");
			if(recipes.get(position).isFavorite()){
			Log.v(MainActivity.LOGGING_KEY, "3");
				star.setImageResource(R.drawable.fav);
			Log.v(MainActivity.LOGGING_KEY,"4");
			}
			else{
			Log.v(MainActivity.LOGGING_KEY,"5");
				star.setImageResource(R.drawable.not);
			Log.v(MainActivity.LOGGING_KEY, "6");
			}
	Log.v(MainActivity.LOGGING_KEY, this.getClass().getName() + " getView values added to SubViews");
		final int pos = position;
		star.setFocusable(false);
		star.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				recipes.get(pos).setFavorite(false);
				recipes.remove(pos);
				notifyDataSetChanged();
			}
		});
//		rowView.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Log.v(MainActivity.SECOND_KEY, this.getClass().getName() + "clicking and creating intent");
//				Intent intent = new Intent(getContext(),RecipeActivity.class);
//				Log.v(MainActivity.SECOND_KEY, this.getClass().getName() + " created intent");
//				intent.putExtra(MainActivity.EXTRA_RECIPE, recipes.get(pos));
//				Log.v(MainActivity.SECOND_KEY, this.getClass().getName() + " passed and extra" );
//				getContext().startActivity(intent);
//				Log.v(MainActivity.SECOND_KEY, this.getClass().getName() + "creating intent end");
//			}
//		});
		Log.v(MainActivity.LOGGING_KEY, this.getClass().getName() + " getView end");
		return rowView;
		
		
	}
}
