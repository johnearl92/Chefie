package com.example.chefie.adapters;

import Fragments.RecipeFragments.RecipeDescription;
import Fragments.RecipeFragments.RecipeIngredient;
import Fragments.RecipeFragments.RecipeProcedure;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.example.chefie.MainActivity;
import com.example.chefie.classes.Recipe;

public class RecipeTabsAdapter extends FragmentPagerAdapter{
	private Recipe recipe;
	
	public RecipeTabsAdapter(FragmentManager fm,Recipe recipe) {
		
		super(fm);
		Log.v(MainActivity.SECOND_KEY, this.getClass().getName() + " constructor invoke");
		// TODO Auto-generated constructor stub
		this.recipe = recipe;
		Log.v(MainActivity.SECOND_KEY, this.getClass().getName() + " constructor end");
		
	}

	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		Log.v(MainActivity.SECOND_KEY, this.getClass().getName() + " getItem invoked" );
		switch(arg0){
		case 0:
			return new RecipeDescription(recipe);
		case 1:
			return new RecipeIngredient(recipe.getIngredients());
		case 2:
			return new RecipeProcedure(recipe.getProcedures());
		}
		
		return null;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 3;
	}

}
