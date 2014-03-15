package com.example.chefie.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chefie.MainActivity;
import com.example.chefie.R;
import com.example.chefie.RecipeActivity;
import com.example.chefie.classes.ProcIngredient;
import com.example.chefie.classes.Recipe;

public class SearchAdapter extends ArrayAdapter<Recipe>{
	

	


	private final Context context;
	private ArrayList<Recipe> recipes;
	
	public SearchAdapter(Context context,ArrayList<Recipe> recipes) {
		super(context, R.layout.list_recipe_row,recipes);
		// TODO Auto-generated constructor stub
		Log.v(MainActivity.LOGGING_KEY, this.getClass().getName() + " constructor invoke");
		this.context=context;
		this.recipes=recipes;
		Log.v(MainActivity.LOGGING_KEY, this.getClass().getName() + " constructior end");
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
		star.setFocusable(false);
		Log.v(MainActivity.LOGGING_KEY, this.getClass().getName() + " getView subViews Created");
		
		Log.v(MainActivity.LOGGING_KEY, this.getClass().getName() + " getVIew adding values to subViews");
			titleview.setText(recipes.get(position).getRecipeName());
			Log.v(MainActivity.LOGGING_KEY, "1 "+ recipes.get(position).getRecipeName());
			imageview.setImageResource(recipes.get(position).getRecipeImage());
			Log.v(MainActivity.LOGGING_KEY, "2");
			int holder;
			if(recipes.get(position).isFavorite()){
				Log.v(MainActivity.LOGGING_KEY, "3");
				holder = R.drawable.fav;
				//star.setImageResource(R.drawable.fav);
				Log.v(MainActivity.LOGGING_KEY,"4");
			}
			else{
				Log.v(MainActivity.LOGGING_KEY,"5");
				holder = R.drawable.not;
				//star.setImageResource(R.drawable.not);
				Log.v(MainActivity.LOGGING_KEY, "6");
			}
			final int pos = position;
			star.setImageResource(holder);
			star.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					ImageButton imageStar = (ImageButton)v;
					if(recipes.get(pos).isFavorite()){
						recipes.get(pos).setFavorite(false);
						imageStar.setImageResource(R.drawable.not);
						Toast.makeText(getContext(), "removed from favorites", Toast.LENGTH_SHORT).show();
					}
					else{
						recipes.get(pos).setFavorite(true);
						imageStar.setImageResource(R.drawable.fav);
						Toast.makeText(getContext(), "added to favorites", Toast.LENGTH_SHORT).show();
					}
				}
			});
			
		Log.v(MainActivity.LOGGING_KEY, this.getClass().getName() + " getView values added to SubViews");
		

		Log.v(MainActivity.LOGGING_KEY, this.getClass().getName() + " getView end");
		return rowView;
		
		
	}
	
	@Override
	public Filter getFilter() {
		// TODO Auto-generated method stub
		return new Filter(){

			@Override
			protected FilterResults performFiltering(CharSequence constraint) {
				// TODO Auto-generated method stub
				final ArrayList<Recipe> filteredResults = new ArrayList<Recipe>();
				String ingAll = "";
				for(Recipe r:recipes){
					for(ProcIngredient ing:r.getIngredients()){
						ingAll = ingAll + ing.getProcIngred(); 
					}
					if(r.getRecipeName().toLowerCase().contains(constraint.toString().toLowerCase())||ingAll.toLowerCase().contains(constraint.toString().toLowerCase())){
						filteredResults.add(r);
					}
				}
				final FilterResults results = new FilterResults();
				results.values = filteredResults;
				return results;
			}

			
			@Override
			protected void publishResults(CharSequence constraint, FilterResults results) {
				// TODO Auto-generated method stub
				recipes = ((ArrayList<Recipe>) results.values);
				notifyDataSetChanged();
			}

			
			
		};
	}
	

}
