package com.example.chefie;

import Fragments.RecipeFragments.RecipeIngredient;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.chefie.adapters.RecipeArrayAdapter;
import com.example.chefie.adapters.RecipeTabsAdapter;
import com.example.chefie.classes.ProcIngredient;
import com.example.chefie.classes.Recipe;

public class RecipeActivity extends FragmentActivity implements ActionBar.TabListener{
	private ViewPager viewPager;
	private RecipeTabsAdapter adap;
	private ActionBar actionBar;
	private String[] tabs = {"Description", "Ingredients","Procedures"};
	private Recipe recipe;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.v(MainActivity.SECOND_KEY, this.getClass().getName() + " onCreate invoke");
		setContentView(R.layout.activity_recipe);
		// Show the Up button in the action bar.
		viewPager = (ViewPager) findViewById(R.id.recipepager);
		actionBar = getActionBar();
		Intent intent = getIntent();
		recipe = (Recipe) intent.getSerializableExtra(MainActivity.EXTRA_RECIPE);
		this.adap = new RecipeTabsAdapter(getSupportFragmentManager(), recipe);
		
		this.viewPager.setAdapter(adap);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayHomeAsUpEnabled(true);
		for(String tab_name:tabs){
			actionBar.addTab(actionBar.newTab().setText(tab_name).setTabListener(this));
		}
		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				actionBar.setSelectedNavigationItem(arg0);
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		Log.v(MainActivity.SECOND_KEY, this.getClass().getName() + " onCreate end");
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.recipe, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabSelected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		viewPager.setCurrentItem(arg0.getPosition());
	}

	@Override
	public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}
	
	public void favRemover(View v){
		ImageButton star = (ImageButton)v;
		if(recipe.isFavorite()){
			recipe.setFavorite(false);
			star.setImageResource(R.drawable.not);
			Toast.makeText(this, "This recipe is removed from favorites", Toast.LENGTH_SHORT).show();
		}else{
			recipe.setFavorite(true);
			star.setImageResource(R.drawable.fav);
			Toast.makeText(this, "This Recipe is added to your favorites", Toast.LENGTH_SHORT).show();
		}
	}
	
	@Override
	public void finish() {
		// TODO Auto-generated method stub
		Intent data = new Intent();
		data.putExtra(MainActivity.EXTRA_RECIPE, recipe);
		setResult(RESULT_OK, data);
		super.finish();
	}
	
//	public void clearer(View v){
//		Log.v("success", "testing");
//		RecipeIngredient r = (RecipeIngredient) getFragmentManager().getBackStackEntryAt(2);
//		Log.v("success", "testing1");
//		for(ProcIngredient p:r.getIngredients()){
//			p.setCheck(false);
//		}
//		RecipeArrayAdapter adap = (RecipeArrayAdapter) r.getListAdapter();
//		adap.notifyDataSetChanged();
//	}

}
