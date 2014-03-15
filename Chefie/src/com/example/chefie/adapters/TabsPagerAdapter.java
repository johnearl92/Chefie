package com.example.chefie.adapters;

import Fragments.AppetizersFragment;
import Fragments.BreakBrunchFragment;
import Fragments.ChickenFragment;
import Fragments.DessertFragment;
import Fragments.MainDishFragment;
import Fragments.PastaFragment;
import Fragments.SaladFragment;
import Fragments.VegetarianFragment;
import android.app.ListActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.ListFragment;
import android.util.Log;

import com.example.chefie.MainActivity;
import com.example.chefie.classes.RecipeWrapper;

public class TabsPagerAdapter extends FragmentPagerAdapter{
	private RecipeWrapper recipeWrapper;
	
	
	public TabsPagerAdapter(FragmentManager fm, RecipeWrapper recipeWrapper) {
		// TODO Auto-generated constructor stub
		super(fm);
		Log.v(MainActivity.LOGGING_KEY, this.getClass().getName() + " contstructor invoked");
		this.recipeWrapper = recipeWrapper;
		Log.v(MainActivity.LOGGING_KEY, this.getClass().getName()+ " constructor end");
	}

	@Override
	public ListFragment getItem(int arg0) {
		// TODO Auto-generated method stub
		switch(arg0){
		case 0:
			return new AppetizersFragment(recipeWrapper.getAppetizerList());
		case 1:
			return new BreakBrunchFragment(recipeWrapper.getBreakBrunchList());
		case 2:
			return new ChickenFragment(recipeWrapper.getChickenList());
		case 3:
			return new DessertFragment(recipeWrapper.getDessertsList());
		case 4:
			return new MainDishFragment(recipeWrapper.getMainDishList());
		case 5:
			return new PastaFragment(recipeWrapper.getPastaList());
		case 6:
			return new SaladFragment(recipeWrapper.getSaladList());
		case 7:
			return new VegetarianFragment(recipeWrapper.getVegetarianList());
		}
		
		return null;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 8;
	}

}
