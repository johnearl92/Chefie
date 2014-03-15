package com.example.chefie;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.chefie.adapters.TabsPagerAdapter;
import com.example.chefie.classes.ProcIngredient;
import com.example.chefie.classes.Recipe;
import com.example.chefie.classes.RecipeWrapper;

public class MainActivity extends FragmentActivity implements ActionBar.TabListener {

	public final static String LOGGING_KEY = "com.example.chefie.LOGS";
	public final static String SECOND_KEY = "com.example.chefie.SECOND_VIEWS";
	public final static String EXTRA_RECIPE	= "com.example.chefie.RECIPES";
	public final static String EXTRA_WRAPPER = "com.example.chefie.RECIPE_WRAPPER";
	public final static int RECIPE_REQUEST_CODE = 10;
	public final static String fileName = "chefiefile";
	private ObjectOutputStream out;
	private ObjectInputStream in;
	
	private ViewPager viewPager;
	private TabsPagerAdapter tabsAdapter;
	private ActionBar actionBar;
	private Recipe recipe;
	private RecipeWrapper recipeWrapper;
	private int category;
	
	private String[] tabs = {"Appetizer","Breakfast & Brunch","Chicken","Desserts","Main Dish","Pasta","Salad","Vegetarian"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.v(LOGGING_KEY, this.getClass().getName()+"onCreate invoked");
		setContentView(R.layout.activity_main);
		viewPager = (ViewPager) findViewById(R.id.pager);
		actionBar = getActionBar();
		Log.v(LOGGING_KEY, this.getClass().getName()+" creating recipeWrapper");
		//files
		File file = new File(getFilesDir(),fileName);
		if(file.exists()){
			try {
				this.in = new ObjectInputStream(openFileInput(fileName));
				recipeWrapper = (RecipeWrapper) in.readObject();
				in.close();
			} catch (StreamCorruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else{
		recipeWrapper = new RecipeWrapper();
		this.populateAppetizer(recipeWrapper.getAppetizerList());
		this.populateBreakBrunch(recipeWrapper.getBreakBrunchList());
		this.populateChicken(recipeWrapper.getChickenList());
		this.populateDessert(recipeWrapper.getDessertsList());
		this.populateMainDish(recipeWrapper.getMainDishList());
		this.populatePasta(recipeWrapper.getPastaList());
		this.populateSalad(recipeWrapper.getSaladList());
		this.populateVegetarian(recipeWrapper.getVegetarianList());
		}
		
		Log.v(LOGGING_KEY, this.getClass().getName() + "passes recipeWrapper");
		tabsAdapter = new TabsPagerAdapter(getSupportFragmentManager(),this.recipeWrapper);
		viewPager.setAdapter(tabsAdapter);
		actionBar.setHomeButtonEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
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
		Log.v(LOGGING_KEY, this.getClass().getName() + "onCreate end");
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId()){
		case R.id.action_favorite:
			Intent intent = new Intent(this,FavoritesActivity.class);
			intent.putExtra(EXTRA_WRAPPER, this.recipeWrapper);
			startActivity(intent);
			finish();
			return true;
		case R.id.action_search:
			Intent intentSearch = new Intent(this,SearchActivity.class);
			intentSearch.putExtra(EXTRA_WRAPPER, this.recipeWrapper);
			startActivity(intentSearch);
			finish();
			return true;
		default:
				return super.onOptionsItemSelected(item);
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		MenuItem item = menu.findItem(R.id.action_list);
		item.setEnabled(false);
		return true;
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
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.d(LOGGING_KEY, "destroyed");
		try {
			this.out = new ObjectOutputStream(openFileOutput(fileName, Context.MODE_PRIVATE));
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
	
	public void populateAppetizer(ArrayList<Recipe> recipeList){
		Log.v(LOGGING_KEY, this.getClass().getName()+"populateAppetizer invoked");
		category = 1;
		recipe = new Recipe();
		recipe.setRecipeName("Spinach and Kesong Puti Parcels");
		recipe.setRecipeImage(R.drawable.spinachandkesongputiparcels);
		recipe.getIngredients().add(new ProcIngredient("3 tablespoons olive oil"));
		recipe.getIngredients().add(new ProcIngredient("1 white onion, roughly chopped"));
		recipe.getIngredients().add(new ProcIngredient("2 large bunches of Baguio spinach, blanched, squeezed dry, and finely chopped"));
		recipe.getIngredients().add(new ProcIngredient("4 squares of kesong puti, roughly chopped"));
		recipe.getIngredients().add(new ProcIngredient("1/3 cup cream"));
		recipe.getIngredients().add(new ProcIngredient("2 large eggs, beaten"));
		recipe.getIngredients().add(new ProcIngredient("salt and ground pepper to taste"));
		recipe.getIngredients().add(new ProcIngredient("12 sheets phyllo pastry, slightly defrosted"));
		recipe.getIngredients().add(new ProcIngredient("6 tablespoons butter, melted"));
		recipe.getProcedures().add(new ProcIngredient("In a saucepan, heat the oil and sauté onions until soft. Add the spinach and combine thoroughly. Sauté until the spinach is completely wilted. Take the pan off the heat and stir in the kesong puti, cream, and eggs. Add a dash of salt and pepper. Set the mixture aside."));
		recipe.getProcedures().add(new ProcIngredient("On a work surface dusted with flour, make 2 four-layer stacks of phyllo sheets, with each sheet generously brushed with melted butter. Cut each stack into 6 equal squares."));
		recipe.getProcedures().add(new ProcIngredient("Oil 2 large muffin tin pans and line each muffin cup with a phyllo square. Fill each phyllo cup three-fourths full with the spinach-cheese mixture. Fold the pastry edges inward then bake the phyllo cups at 350ºF for 20 minutes, or until the pastry is golden brown and the spinach filling is set."));
		recipe.setDescription("The version of a spanakopita is quicker to make and more budget-friendly with the kesong puti substitution.");
		recipe.setCategory(category);
		recipe.setFavorite(false);
		recipeList.add(recipe);	
		
		recipe = new Recipe();
		recipe.setRecipeName("Crab Cakes with Roasted Red Bell Pepper Sauce");
		recipe.setRecipeImage(R.drawable.crabcakeswithroastedredbellpeppersauce);
		recipe.getIngredients().add(new ProcIngredient("For the roasted red bell pepper sauce: "));
		recipe.getIngredients().add(new ProcIngredient("1 large red bell pepper"));
		recipe.getIngredients().add(new ProcIngredient("1 cup mayonnaise"));
		recipe.getIngredients().add(new ProcIngredient("1 tablespoon red wine vinegar"));
		recipe.getIngredients().add(new ProcIngredient("1/8 teaspoon salt"));
		recipe.getIngredients().add(new ProcIngredient("1/8 teaspoon pepper"));
		recipe.getIngredients().add(new ProcIngredient("For the crab cakes: "));
		recipe.getIngredients().add(new ProcIngredient("1 pound crab meat"));
		recipe.getIngredients().add(new ProcIngredient("1 cup fresh breadcrumbs "));
		recipe.getIngredients().add(new ProcIngredient("1/3 cup scallions, chopped finely")); 
		recipe.getIngredients().add(new ProcIngredient("1/3 cup mayonnaise"));
		recipe.getIngredients().add(new ProcIngredient("1 tablespoon Dijon mustard"));
		recipe.getIngredients().add(new ProcIngredient("1 egg, beaten"));
		recipe.getIngredients().add(new ProcIngredient("1 tablespoon lemon rind"));
		recipe.getIngredients().add(new ProcIngredient("1 tablespoon lemon juice"));
		recipe.getIngredients().add(new ProcIngredient("1/4 tablespoon old bay seasoning"));
		recipe.getIngredients().add(new ProcIngredient("1/8 teaspoon salt"));
		recipe.getIngredients().add(new ProcIngredient("1/4 teaspoon pepper"));
		recipe.getIngredients().add(new ProcIngredient("1/3 cup Japanese breadcrumbs"));
		recipe.getIngredients().add(new ProcIngredient("corn oil for sautéing "));
		recipe.getProcedures().add(new ProcIngredient("Make the roasted red bell pepper sauce: Roast bell pepper over open flame and char skin. Once skin is charred all over, place in a paper or plastic bag. Allow to sweat for a few minutes. Remove skin. Slice bell pepper in half and remove seeds. Place in a blender together with the rest of the ingredients. Puree until smooth. Adjust seasoning according to taste. Transfer to a serving bowl. Serve with crab cakes.")); 
		recipe.getProcedures().add(new ProcIngredient("Make the crab cakes: Place all ingredients, except for Japanese breadcrumbs and corn oil, in a large bowl. Mix well to combine."));
		recipe.getProcedures().add(new ProcIngredient("Using a 1-tablespoon measuring spoon, scoop a heaped tablespoon of crab mixture and form into a patty. Repeat until done. "));
		recipe.getProcedures().add(new ProcIngredient("Dredge patties one by one in Japanese breadcrumbs until well-coated. ")); 
		recipe.getProcedures().add(new ProcIngredient("In a saute pan, heat oil over medium heat. Cook crab cakes for 3 to 4 minutes on each side or until golden. "));
		recipe.getProcedures().add(new ProcIngredient("Drain on paper towels. Serve with roasted red bell pepper sauce."));
		recipe.getProcedures().add(new ProcIngredient("On a work surface dusted with flour, make 2 four-layer stacks of phyllo sheets, with each sheet generously brushed with melted butter. Cut each stack into 6 equal squares."));
		recipe.getProcedures().add(new ProcIngredient("Oil 2 large muffin tin pans and line each muffin cup with a phyllo square. Fill each phyllo cup three-fourths full with the spinach-cheese mixture. Fold the pastry edges inward then bake the phyllo cups at 350ºF for 20 minutes, or until the pastry is golden brown and the spinach filling is set."));
		recipe.setDescription("While waiting for the meal's highlight, try a couple of these amazing crab cakes. They go well with everything - salads, mains, and even plain old bread! They’re best enjoyed with a generous dipping and dunking of red bell pepper dip. ");
		recipe.setCategory(category);
		recipe.setFavorite(false);
		recipeList.add(recipe);
		
		recipe = new Recipe();
		recipe.setRecipeName("Alaska Garlic and Liver Spread");
		recipe.setRecipeImage(R.drawable.alaskagarlicandliverspread);
		recipe.getIngredients().add(new ProcIngredient("1 pack Alaska Crema all-purpose cream"));
		recipe.getIngredients().add(new ProcIngredient("2 cups liver spread"));
		recipe.getIngredients().add(new ProcIngredient("3 tablespoons chopped garlic"));
		recipe.getIngredients().add(new ProcIngredient("½  teaspoon Worcestershire sauce/liquid seasoning"));
		recipe.getIngredients().add(new ProcIngredient("½  cup cream cheese, softened"));
		recipe.getIngredients().add(new ProcIngredient("¼  cup mayonnaise"));
		recipe.getIngredients().add(new ProcIngredient("1/8  teaspoon pepper"));
		recipe.getProcedures().add(new ProcIngredient("Place all ingredients in a food processor until well blended."));
		recipe.getProcedures().add(new ProcIngredient("Chill before serving.")); 
		recipe.setDescription("Start your feast with a tasty and creamy spread.");
		recipe.setFavorite(false);
		recipe.setCategory(category);
		recipeList.add(recipe);
		
		Log.v(LOGGING_KEY, this.getClass().getName()+" populateAppetizer end");
	}
	
	public void populateBreakBrunch(ArrayList<Recipe> recipeList){
		category = 2;
		recipe = new Recipe();
		recipe.setRecipeName("Fish Congee");
		recipe.setRecipeImage(R.drawable.fishcongee);
		recipe.setFavorite(false);
		//Serves 2 to 3
		//Prep Time 10 minutes,  plus marinating time
		//Cooking Time 20 minutes 
		recipe.getIngredients().add(new ProcIngredient("300 grams white fish fillet, sliced into 2-inch pieces"));  
		recipe.getIngredients().add(new ProcIngredient("3/4 cup jasmine rice, washed"));  
		recipe.getIngredients().add(new ProcIngredient("1/4 cup glutinous rice (malagkit), washed"));  
		recipe.getIngredients().add(new ProcIngredient("4 cups water (vegetable or chicken stock can also be used)"));  
		recipe.getIngredients().add(new ProcIngredient("1 (1/2-cm) piece ginger, grated"));  
		recipe.getIngredients().add(new ProcIngredient("salt, to taste"));  
		recipe.getIngredients().add(new ProcIngredient("chopped green onions, fried wanton strips, "));  
		recipe.getIngredients().add(new ProcIngredient("fried sliced shallots, and ginger strips, for topping"));  
		recipe.getIngredients().add(new ProcIngredient("soy sauce, sesame oil, and white pepper, to taste"));
		recipe.getIngredients().add(new ProcIngredient("For the marinade: "));  
		recipe.getIngredients().add(new ProcIngredient("1/2 tablespoon oyster sauce"));  
		recipe.getIngredients().add(new ProcIngredient("1/2 tablespoon soy sauce"));  
		recipe.getIngredients().add(new ProcIngredient("1/2 tablespoon sesame oil"));  
		recipe.getIngredients().add(new ProcIngredient("1/2 tablespoon Chinese rice wine"));  
		recipe.getIngredients().add(new ProcIngredient("white pepper, to taste"));  
		recipe.getProcedures().add(new ProcIngredient("Make the marinade: mix together all ingredients in a medium-sized bowl."));
		recipe.getProcedures().add(new ProcIngredient("Add fish and marinate for 10 minutes."));
		recipe.getProcedures().add(new ProcIngredient("Place rice, water or stock, ginger, and salt in the rice cooker. Set to cook. Stir it once or twice."));
		recipe.getProcedures().add(new ProcIngredient("After 10 minutes, add fish fillets. Let it cook until the rice cooker switches to the warm setting."));
		recipe.getProcedures().add(new ProcIngredient("Divide congee among bowls. Sprinkle green onions, fried wonton strips, fried shallots, and ginger strips on top. Adjust seasoning by adding soy sauce, sesame oil, and white pepper."));
		recipe.setDescription("Cooking congee in the rice cooker frees you up for other chores, making this the perfect breakfast option for those busy mornings. ");
		recipe.setCategory(category);
		recipeList.add(recipe);	
		
		recipe = new Recipe();
		recipe.setRecipeName("Carrot Cake Pancakes with Maple Cream Cheese Glaze");
		recipe.setRecipeImage(R.drawable.carrotcakepancakeswithmaplecreamcheeseglaze);
		recipe.setFavorite(false);
		//Makes 6 to 8 medium pancakes
		//Prep Time 10 minutes
		//Cooking Time 15 minutes
		recipe.getIngredients().add(new ProcIngredient("For the pancakes: "));
		recipe.getIngredients().add(new ProcIngredient("1 cup all-purpose flour"));
		recipe.getIngredients().add(new ProcIngredient("1 teaspoon baking powder"));
		recipe.getIngredients().add(new ProcIngredient("1 teaspoon baking soda"));
		recipe.getIngredients().add(new ProcIngredient("pinch of salt, to taste"));
		recipe.getIngredients().add(new ProcIngredient("1/2 teaspoon ground cinnamon"));
		recipe.getIngredients().add(new ProcIngredient("1/2 teaspoon ground nutmeg"));
		recipe.getIngredients().add(new ProcIngredient("1 cup grated carrots, plus extra for garnish"));
		recipe.getIngredients().add(new ProcIngredient("1/4 cup chopped walnuts, plus extra for garnish"));
		recipe.getIngredients().add(new ProcIngredient("1/4 cup chopped raisins"));
		recipe.getIngredients().add(new ProcIngredient("1/4 cup melted butter "));
		recipe.getIngredients().add(new ProcIngredient("1 large egg"));
		recipe.getIngredients().add(new ProcIngredient("1 teaspoon vanilla extract"));
		recipe.getIngredients().add(new ProcIngredient("1/4 cup brown sugar "));
		recipe.getIngredients().add(new ProcIngredient("1 cup buttermilk"));
		recipe.getIngredients().add(new ProcIngredient("For the glaze: "));
		recipe.getIngredients().add(new ProcIngredient("1 cup cream cheese, softened to room temperature"));
		recipe.getIngredients().add(new ProcIngredient("1/2 cup maple syrup"));
		recipe.getProcedures().add(new ProcIngredient("In a large bowl, combine flour, baking powder, baking soda, salt, and spices. mix until combined."));
		recipe.getProcedures().add(new ProcIngredient("Add carrots, nuts, and raisins, making sure each one is coated with the dry ingredients. Set aside. "));
		recipe.getProcedures().add(new ProcIngredient("In another bowl, combine butter, egg, vanilla extract, sugar, and buttermilk. Combine with dry ingredients and mix with a spatula until no lumps remain."));
		recipe.getProcedures().add(new ProcIngredient("Make the glaze:  In a bowl, mix together cream cheese and maple syrup until smooth. Set aside."));
		recipe.getProcedures().add(new ProcIngredient("Place a non-stick pan over medium heat. To test if the pan is ready, sprinkle water over the surface. If the droplets sizzle, proceed with cooking the pancakes."));
		recipe.getProcedures().add(new ProcIngredient("Ladle  1/2 cup batter into the  hot pan.  Wait until bubbles form. Once most of the bubbles have dissipated, use a spatula to gently flip the pancake. Cook for about 2 to 3 minutes more until pancake is golden brown."));
		recipe.getProcedures().add(new ProcIngredient("Place pancake on a plate and spread glaze on top. Add more pancakes and glaze until you make a stack."));
		recipe.getProcedures().add(new ProcIngredient("Top with more glaze. Garnish with carrots and chopped walnuts."));
		recipe.setDescription("We took everyone's favorite pancakes and made them even better. This will definitely get your weekend off to a great start! ");
		recipe.setCategory(category);
		recipeList.add(recipe);
		
		recipe = new Recipe();
		recipe.setRecipeName("Bangus Fried Rice");
		recipe.setRecipeImage(R.drawable.bangusfriedrice);
		recipe.setFavorite(false);
		//Serves 2 to 3
		recipe.getIngredients().add(new ProcIngredient("6 tablespoons olive oil"));
		recipe.getIngredients().add(new ProcIngredient("1 tablespoon minced garlic "));
		recipe.getIngredients().add(new ProcIngredient("2 tablespoons chopped onions"));
		recipe.getIngredients().add(new ProcIngredient("1/2 cup diced carrots "));
		recipe.getIngredients().add(new ProcIngredient("1/2 cup thawed green peas"));
		recipe.getIngredients().add(new ProcIngredient("1 (183-gram) can bangus, drained"));
		recipe.getIngredients().add(new ProcIngredient("2 cups cooked red rice"));
		recipe.getIngredients().add(new ProcIngredient("salt and pepper,  to taste"));
		recipe.getProcedures().add(new ProcIngredient("Heat olive oil in a frying pan"));
		recipe.getProcedures().add(new ProcIngredient("Sauté garlic and onions; cook until onions are translucent. Add carrots and green peas. Cook until vegetables are tender. Add bangus and cooked red rice. Mix until thoroughly combined. Season to taste."));
		recipe.setDescription("You'll just 15 minutes to whip up this tasty brekkie. You can have this for dinner, too! ");
		recipe.setCategory(category);
		recipeList.add(recipe);
		
		recipe = new Recipe();
		recipe.setRecipeName("Peanut Butter and Banana Oatmeal");
		recipe.setRecipeImage(R.drawable.peanutbutterandbananaoatmeal);
		recipe.setFavorite(false);
		//Serves 2
		recipe.getIngredients().add(new ProcIngredient("2 large bananas, chopped"));
		recipe.getIngredients().add(new ProcIngredient("1 tablespoon butter "));
		recipe.getIngredients().add(new ProcIngredient("1/2 cup rolled oats"));
		recipe.getIngredients().add(new ProcIngredient("1 1/2 cups vanilla-flavored almond milk"));
		recipe.getIngredients().add(new ProcIngredient("1 1/2 teaspoons all-natural peanut butter"));
		recipe.getIngredients().add(new ProcIngredient("1/4 teaspoon ground cinnamonv"));
		recipe.getIngredients().add(new ProcIngredient("honey for drizzling "));
		recipe.getIngredients().add(new ProcIngredient("bananas and nuts, to serve"));
		recipe.getProcedures().add(new ProcIngredient("Combine bananas and butter in a microwavable bowl. Cover and microwave on high for 1 minute. Add oats, milk, peanut butter, and cinnamon. Microwave on high, covered, for 8 to 10 minutes, stirring occasionally. "));
		recipe.getProcedures().add(new ProcIngredient("Drizzle honey over each bowl and serve with bananas and nuts on the side."));
		recipe.setDescription("We used ripe bananas and almond milk to sweeten this recipe—no sugar required!");
		recipe.setCategory(category);
		recipeList.add(recipe);
		
		recipe = new Recipe();
		recipe.setRecipeName("Ham and Egg Breakfast Casserole");
		recipe.setRecipeImage(R.drawable.hamandeggbreakfastcasserole);
		recipe.setFavorite(false);
		//Serves 6 to 8
		recipe.getIngredients().add(new ProcIngredient("10 slices white bread"));
		recipe.getIngredients().add(new ProcIngredient("1 /2 cup diced ham"));
		recipe.getIngredients().add(new ProcIngredient("1 /4 cup diced bacon"));
		recipe.getIngredients().add(new ProcIngredient("1 /4 cup shredded Cheddar cheese"));
		recipe.getIngredients().add(new ProcIngredient("1 /4 cup shredded quick melting cheese"));
		recipe.getIngredients().add(new ProcIngredient("3 eggs"));
		recipe.getIngredients().add(new ProcIngredient("1 cup milk"));
		recipe.getIngredients().add(new ProcIngredient("salt and pepper, to taste"));
		recipe.getIngredients().add(new ProcIngredient("2 cups unsweetened cornflakes, slightly crushed "));
		recipe.getIngredients().add(new ProcIngredient("4 tablespoons melted butter"));
		recipe.getProcedures().add(new ProcIngredient("Preheat oven to 350°F. ")); 
		recipe.getProcedures().add(new ProcIngredient("Cut white bread into cubes; set aside. ")); 
		recipe.getProcedures().add(new ProcIngredient("Place half the bread cubes in an even layer on the bottom of a square baking dish. Add ham, bacon,  cheese, and quick melting cheese. Mix well. Top with remaining bread cubes. ")); 
		recipe.getProcedures().add(new ProcIngredient("In a bowl, beat together eggs and milk; season with salt and pepper. Pour egg mixture over bread  cubes."));  
		recipe.getProcedures().add(new ProcIngredient("In a separate bowl, combine cornflakes and butter.")); 
		recipe.getProcedures().add(new ProcIngredient("Spread on top and bake in preheated oven for 15 to 20 minutes or until cornflakes are golden brown. Serve immediately."));
		recipe.setDescription("Prepare this dish the night before and pop it in the oven the next day for a tummy-filling breakfast on Christmas morning.");
		recipe.setCategory(category);
		recipeList.add(recipe);
	}
	
	public void populateChicken(ArrayList<Recipe> recipeList){
		category = 3;
		recipe = new Recipe();
		recipe.setRecipeName("Glazed Chicken Popcorn");
		recipe.setRecipeImage(R.drawable.glazedchickenpopcorn);
		recipe.setFavorite(false);
		//Makes 4 servings  
		//Prep Time 30 minutes  
		//Cooking Time 20 minutes
		recipe.getIngredients().add(new ProcIngredient("250 grams boneless chicken breast fillet"));
		recipe.getIngredients().add(new ProcIngredient("1/4 teaspoon salt"));
		recipe.getIngredients().add(new ProcIngredient("egg white from 1 egg"));
		recipe.getIngredients().add(new ProcIngredient("1/4 cup cornstarch"));
		recipe.getIngredients().add(new ProcIngredient("oil for deep-frying"));
		recipe.getIngredients().add(new ProcIngredient("For the glaze: "));
		recipe.getIngredients().add(new ProcIngredient("1/4 cup soy sauce"));
		recipe.getIngredients().add(new ProcIngredient("1/4 cup honey"));
		recipe.getIngredients().add(new ProcIngredient("1/4 cup rice wine"));
		recipe.getIngredients().add(new ProcIngredient("1 tablespoon minced garlic"));
		recipe.getIngredients().add(new ProcIngredient("1 teaspoon grated ginger"));
		recipe.getIngredients().add(new ProcIngredient("1 tablespoon brown sugar"));
		recipe.getProcedures().add(new ProcIngredient("Cut chicken fillet into small cubes. Marinate in salt for 40 minutes. Add egg white and cornstarch; mix."));
		recipe.getProcedures().add(new ProcIngredient("Deep-fry chicken pieces in hot oil until crisp; drain."));
		recipe.getProcedures().add(new ProcIngredient("In a saucepan, combine all ingredients for the glaze. Let simmer and allow to reduce for 5 minutes. Add fried chicken pieces; toss. Serve immediately."));
		recipe.setDescription("Popular with kids and adults alike, these pop-in-the-mouth treats are great for a quick snack or as an appetizer. (Rice lovers can also turn it into a Sweet and Sour Chicken Popper Rice, while the health-conscious can make a Chicken Pop Salad with Grapes and Cheese.) Wonderful for movie-watching too—paired with ice-cold soda, of course!  ");
		recipe.setCategory(category);
		recipeList.add(recipe);
		
		recipe = new Recipe();
		recipe.setRecipeName("Ginger-Garlic Chicken");
		recipe.setRecipeImage(R.drawable.gingergarlicchicken);
		recipe.setFavorite(false);
		//Serves 3 to 4 
		//Prep Time 20 minutes 
		//Cooking Time 20 minutes
		recipe.getIngredients().add(new ProcIngredient("1 kilo chicken breast fillet, cut into big strips"));
		recipe.getIngredients().add(new ProcIngredient("salt and pepper to season"));
		recipe.getIngredients().add(new ProcIngredient("juice and zest of half a lemon"));
		recipe.getIngredients().add(new ProcIngredient("1 cup cornstarch"));
		recipe.getIngredients().add(new ProcIngredient("2 tablespoons chopped chives"));
		recipe.getIngredients().add(new ProcIngredient("For the sauce: "));
		recipe.getIngredients().add(new ProcIngredient("1 tablespoon sesame oil"));
		recipe.getIngredients().add(new ProcIngredient("1 teaspoon turmeric powder"));
		recipe.getIngredients().add(new ProcIngredient("3 tablespoons ginger, minced"));
		recipe.getIngredients().add(new ProcIngredient("2 tablespoons garlic, minced"));
		recipe.getIngredients().add(new ProcIngredient("2 tablespoons leeks, chopped"));
		recipe.getIngredients().add(new ProcIngredient("2 pieces dried chili"));
		recipe.getIngredients().add(new ProcIngredient("1 1/2 cups chicken stock"));
		recipe.getIngredients().add(new ProcIngredient("1 teaspoon fish sauce"));
		recipe.getIngredients().add(new ProcIngredient("1 tablespoon liquid seasoning"));
		recipe.getIngredients().add(new ProcIngredient("1/4 cup sugar"));
		recipe.getIngredients().add(new ProcIngredient("1 tablespoon corn starch dissolved in 3 tablespoons cold water"));
		recipe.getProcedures().add(new ProcIngredient("In a small sauce pot, heat oil, add the turmeric then brown the garlic and ginger. Mix in leeks and dried chili. Deglaze with chicken stock and season with fish sauce, liquid seasoning and sugar. Bring to a boil and add cornstarch mixture. Simmer for another minute to thicken. Set aside."));
		recipe.getProcedures().add(new ProcIngredient("Season chicken strips, toss in lemon juice with zest, then lightly coat with cornstarch. Deep fry in oil until golden brown and toss in sauce. Plate and top with chopped chives."));
		recipe.setDescription("This is much better than the off-the-box chicken nuggets, and the kids will agree. Enjoy it with steamed rice and a glass of icy cold lemonade.");
		recipe.setCategory(category);
		recipeList.add(recipe);
		
		recipe = new Recipe();
		recipe.setRecipeName("Roasted Chicken and Mushroom Crepe");
		recipe.setRecipeImage(R.drawable.roastedchickenandmushroomcrepe);
		recipe.setFavorite(false);
		//Serves 6 to 8  
		//Prep Time 15 minutes  
		//Cooking Time 30 minutes
		recipe.getIngredients().add(new ProcIngredient("For the sauteed mushrooms"));
		recipe.getIngredients().add(new ProcIngredient("4 tablespoons unsalted butter, divided"));
		recipe.getIngredients().add(new ProcIngredient("1 shallot, sliced thinly"));
		recipe.getIngredients().add(new ProcIngredient("570 grams (about 7 1/2 cups) white mushrooms, trimmed and halved"));
		recipe.getIngredients().add(new ProcIngredient("350 grams (about 4 1/2 cups) shiitake mushrooms, stemmed and sliced into 1/2-inch-thick pieces"));
		recipe.getIngredients().add(new ProcIngredient("3/4 teaspoon salt "));
		recipe.getIngredients().add(new ProcIngredient("2 teaspoons minced fresh thyme"));
		recipe.getIngredients().add(new ProcIngredient("2 cloves garlic, minced"));
		recipe.getIngredients().add(new ProcIngredient("1/2 cup white wine"));
		recipe.getIngredients().add(new ProcIngredient("salt and pepper, to taste"));
		recipe.getIngredients().add(new ProcIngredient("For the bechamel sauce : "));
		recipe.getIngredients().add(new ProcIngredient("4 tablespoons unsalted butter"));
		recipe.getIngredients().add(new ProcIngredient("1/4 cup all-purpose flour"));
		recipe.getIngredients().add(new ProcIngredient("4 cups whole milk"));
		recipe.getIngredients().add(new ProcIngredient("salt and freshly ground black pepper, to taste"));
		recipe.getIngredients().add(new ProcIngredient("1 recipe Basic Savory Crepes "));
		recipe.getIngredients().add(new ProcIngredient("3 pieces roasted chicken breast, shredded or sliced into thick chunks"));
		recipe.getProcedures().add(new ProcIngredient("Make the sauteed mushrooms: Melt 2 tablespoons butter in a 12-inch skillet over medium heat. Add shallots and cook until softened, about 1 to 2 minutes. Add white mushrooms, shiitake mushrooms, and salt then increase heat to medium-high. Cover and cook, stirring occasionally, until mushrooms have released their moisture, about 8 to 10 minutes. Remove lid, add remaining butter and cook, stirring occasionally, until mushrooms are deep golden brown and tender, about 8 to 10 minutes. Stir in thyme and garlic and cook until fragrant, about 30 seconds. Add wine and cook, scraping up any browned bits, until liquid is nearly evaporated, about 1 minute. Season with salt and pepper. Set aside."));
		recipe.getProcedures().add(new ProcIngredient("Make the bechamel sauce: Melt butter in a saucepan over medium heat. Add flour and stir constantly until paste cooks and bubbles, about 1 1/2 minutes. Gradually whisk in milk. Cook and bring to a boil, stirring constantly until sauce thickens. Add salt and pepper then reduce to a simmer. Transfer béchamel to a bowl and set aside until ready to use."));
		recipe.getProcedures().add(new ProcIngredient("To assemble, fold a piece of crepe into a triangle. Place it on a serving plate. Spread about 2 tablespoons bechamel sauce. Top with shredded chicken and 3 tablespoons sautéed mushrooms. Repeat to make a total of 12 crepes. Serve warm."));
		recipe.setDescription("Give leftover roasted chicken a French makeover: Pair with neatly folded crepes and dress it up with creamy bechamel sauce and sauteed fresh mushrooms. Ooh la la!");
		recipe.setCategory(category);
		recipeList.add(recipe);
		
		recipe = new Recipe();
		recipe.setRecipeName("Roast Chicken");
		recipe.setRecipeImage(R.drawable.roastchicken);
		recipe.setFavorite(false);
		//Serves 5 to 6 
		recipe.getIngredients().add(new ProcIngredient("1 whole chicken, rinsed and patted dry"));
		recipe.getIngredients().add(new ProcIngredient("4 cloves garlic, pounded"));
		recipe.getIngredients().add(new ProcIngredient("2 lemons, zested"));
		recipe.getIngredients().add(new ProcIngredient("1 tablespoon dried oregano "));
		recipe.getIngredients().add(new ProcIngredient("2 tablespoons dried rosemary "));
		recipe.getIngredients().add(new ProcIngredient("1 tablespoon dried thyme "));
		recipe.getIngredients().add(new ProcIngredient("2 teaspoons freshly cracked pepper"));
		recipe.getIngredients().add(new ProcIngredient("3 tablespoons olive oil"));
		recipe.getIngredients().add(new ProcIngredient("2 tablespoons salt"));
		recipe.getProcedures().add(new ProcIngredient("In a bowl, mix garlic, lemon zest, oregano, rosemary, thyme, and pepper. Set aside. Preheat turbo oven at 350ºF."));
		recipe.getProcedures().add(new ProcIngredient("Cut off the tips of the chicken wings, because they burn easily during roasting. Season chicken with salt and rub skin with olive oil. Follow with a rubbing of the pre-mixed herb mixture. Put some of the herb mixture between chicken skin and meat for more flavor."));
		recipe.getProcedures().add(new ProcIngredient("Prick the zested lemons all over with a fork. Place inside the chicken cavity. Use kitchen twine to tie the chicken for roasting if desired, then cover with two sheets of aluminum foil. (Place one sheet of foil on your working table and set the chicken on it, breast side up. Cover with another sheet, then pinch together foil perimeters to seal)."));
		recipe.getProcedures().add(new ProcIngredient("Place chicken inside the turbo oven breast side up and roast for 30 to 40 minutes. Remove foil on the upper half then roast again for 15 to 20 more minutes until skin turns brown. Use a meat thermometer to check the doneness of the chicken (at least 180ºF) or pierce with the fork and see if the juices run clear. When chicken is thoroughly cooked, remove lemons from the cavity and squeeze all over the chicken. Let chicken rest for 10 minutes before slicing."));
		recipe.setDescription("Contrary to what most people think, Roast Chicken is one of the easiest dishes to cook. All you need are the basics--chicken, salt and pepper, rosemary, and olive oil.");
		recipe.setCategory(category);
		recipeList.add(recipe);
		
		recipe = new Recipe();
		recipe.setRecipeName("Chicken Popsicles");
		recipe.setRecipeImage(R.drawable.chickenpopsicles);
		recipe.setFavorite(false);
		recipe.getIngredients().add(new ProcIngredient("6 chicken tenders "));
		recipe.getIngredients().add(new ProcIngredient("2/3 cup flour "));
		recipe.getIngredients().add(new ProcIngredient("2 eggs, beaten "));
		recipe.getIngredients().add(new ProcIngredient("2/3 cup panko or coarse Japanese breadcrumbs "));
		recipe.getIngredients().add(new ProcIngredient("1/3 cup Parmesan cheese "));
		recipe.getIngredients().add(new ProcIngredient("1 tablespoon dried oregano "));
		recipe.getIngredients().add(new ProcIngredient("1 tablespoon dried thyme "));
		recipe.getIngredients().add(new ProcIngredient("2 tablespoons butter "));
		recipe.getIngredients().add(new ProcIngredient("3 tablespoons freshly squeezed lemon juice  "));
		recipe.getIngredients().add(new ProcIngredient("salt and pepper"));
		recipe.getProcedures().add(new ProcIngredient("Cut each chicken tender horizontally in half, creating 2 pieces; season. "));
		recipe.getProcedures().add(new ProcIngredient("Roll tenders in flour, dip in egg, and roll in a mixture of breadcrumbs, Parmesan, oregano, and thyme. Transfer to a plate. "));
		recipe.getProcedures().add(new ProcIngredient("Cook tenders on both sides until golden brown, about 3 minutes on each side. Transfer cooked tenders to a plate. "));
		recipe.getProcedures().add(new ProcIngredient("Remove pan from heat and add lemon juice. Stir for a few seconds to loosen brown bits; let the juice reduce. Drizzle a few drops over each tender and season with salt."));
		recipe.setDescription("Planning an out of town trip? Here's an easy to cook chicken recipe that your whole family will surely love!");
		recipe.setCategory(category);
		recipeList.add(recipe);
	}
	
	public void populateDessert(ArrayList<Recipe> recipeList){
		category = 4;
		recipe = new Recipe();
		recipe.setRecipeName("Perry's Fried Bananas with Condensed Milk Sauce");
		recipe.setRecipeImage(R.drawable.perrysfriedbananaswithcondensedmilksauce);
		recipe.setFavorite(false);
		//Serves 2 to 3 
		//Prep Time 5 minutes 
		//Cooking Time 15 minutes 
		recipe.getIngredients().add(new ProcIngredient("3 saba bananas"));
		recipe.getIngredients().add(new ProcIngredient("1/8 cup butter"));
		recipe.getIngredients().add(new ProcIngredient("1/2 can condensed milk"));
		recipe.getIngredients().add(new ProcIngredient("vanilla ice cream "));
		recipe.getProcedures().add(new ProcIngredient("Slice bananas thinly. Fry until golden brown. Drain on paper towels then set aside."));
		recipe.getProcedures().add(new ProcIngredient("In a pan, melt butter in condensed milk. Set aside."));
		recipe.getProcedures().add(new ProcIngredient("Serve fried bananas with milk sauce then top with ice cream. "));
		recipe.setDescription("It can't get any simpler or quicker than this recipe that Perry Quimbo's mom always whips up at home. It’s an instant sweet palate pleaser! ");
		recipe.setCategory(category);
		recipeList.add(recipe);
		
		recipe = new Recipe();
		recipe.setRecipeName("Cheese and Cocoa Pie");
		recipe.setRecipeImage(R.drawable.cheeseandcocoapie);
		recipe.setFavorite(false);
		//Makes about 2 large pies
		recipe.getIngredients().add(new ProcIngredient("For the pie dough: "));
		recipe.getIngredients().add(new ProcIngredient("1 1/2 cups flour "));
		recipe.getIngredients().add(new ProcIngredient("3 tablespoons cocoa powder  "));
		recipe.getIngredients().add(new ProcIngredient("2 1/2 teaspoons baking powder "));
		recipe.getIngredients().add(new ProcIngredient("1 cup sugar "));
		recipe.getIngredients().add(new ProcIngredient("1/4 cup eggs "));
		recipe.getIngredients().add(new ProcIngredient("1 cup butter "));
		recipe.getIngredients().add(new ProcIngredient("For the filling: "));
		recipe.getIngredients().add(new ProcIngredient("1 1/2 cups cream cheese "));
		recipe.getIngredients().add(new ProcIngredient("1/2 cup yogurt "));
		recipe.getIngredients().add(new ProcIngredient("1 cup sugar "));
		recipe.getIngredients().add(new ProcIngredient("1 cup melted butter, cooled "));
		recipe.getIngredients().add(new ProcIngredient("1 cup eggs "));
		recipe.getIngredients().add(new ProcIngredient("1 cup vanilla pudding instant mix"));
		recipe.getProcedures().add(new ProcIngredient("Mix all the ingredients for the dough. Knead for 3 minutes. Roll out dough and place on a baking ring. "));
		recipe.getProcedures().add(new ProcIngredient("In a bowl, mix cream cheese with the yogurt. Add sugar, eggs, vanilla, and melted butter. "));
		recipe.getProcedures().add(new ProcIngredient("Pour into the prepared dough. "));
		recipe.getProcedures().add(new ProcIngredient("Bake at 160ºC for 55 minutes."));
		recipe.setDescription("Makati Shangri-La's exuberant executive pastry chef shares his easy-to-make Cheese and Cocoa Pie. ");
		recipe.setCategory(category);
		recipeList.add(recipe);
		
		recipe = new Recipe();
		recipe.setRecipeName("Coffee Jelly with Vanilla Ice Cream");
		recipe.setRecipeImage(R.drawable.coffeejellywithvanillaicecream);
		recipe.setFavorite(false);
		//Serves 5 to 6 
		//Prep Time 10 minutes, plus setting time
		recipe.getIngredients().add(new ProcIngredient("1 cup cold water"));
		recipe.getIngredients().add(new ProcIngredient("3 to 4 (7-gram) sachets gelatin powder (depending on desired firmness)"));
		recipe.getIngredients().add(new ProcIngredient("5 cups water"));
		recipe.getIngredients().add(new ProcIngredient("1 cup sugar"));
		recipe.getIngredients().add(new ProcIngredient("2 tablespoons instant coffee powder (we used Nescafé)"));
		recipe.getIngredients().add(new ProcIngredient("store-bought vanilla ice cream, to serve"));
		recipe.getIngredients().add(new ProcIngredient("chocolate wafer sticks for garnish"));
		recipe.getProcedures().add(new ProcIngredient("Pour cold water over gelatin powder. Allow to bloom."));
		recipe.getProcedures().add(new ProcIngredient("Combine water, sugar, and coffee powder in a saucepan over high heat. Bring to a boil."));
		recipe.getProcedures().add(new ProcIngredient("Turn off heat and whisk in gelatin mixture."));
		recipe.getProcedures().add(new ProcIngredient("Strain mixture and pour into molds or a tray. Chill in the refrigerator for 2 to 3 hours until set."));
		recipe.getProcedures().add(new ProcIngredient("Cut coffee jelly into desired shapes. Transfer to cups or glasses and serve with vanilla ice cream. Garnish with chocolate wafer sticks."));
		recipe.setDescription("Luscious ice cream pairs well with bold-flavored jelly in this delicious dessert. Try other flavors of ice cream, too—caramel or chocolate would work just as well.");
		recipe.setCategory(category);
		recipeList.add(recipe);
		
		recipe = new Recipe();
		recipe.setRecipeName("Mango Tapioca in Coconut Milk");
		recipe.setRecipeImage(R.drawable.mangotapiocaincoconutmilk);
		recipe.setFavorite(false);
		//Serves 5 to 6
		//Prep Time 2 hours, including chilling time
		recipe.getIngredients().add(new ProcIngredient("1/2 cup uncooked mini tapioca pearls (sago)"));
		recipe.getIngredients().add(new ProcIngredient("1 (400-ml) can coconut milk"));
		recipe.getIngredients().add(new ProcIngredient("2/3 cup milk"));
		recipe.getIngredients().add(new ProcIngredient("1/4 cup sugar"));
		recipe.getIngredients().add(new ProcIngredient("flesh from the cheeks of 2 mangoes, diced"));
		recipe.getIngredients().add(new ProcIngredient("toasted desiccated coconut, for garnish"));
		recipe.getProcedures().add(new ProcIngredient("Cook tapioca pearls according to package directions."));
		recipe.getProcedures().add(new ProcIngredient("Combine coconut milk, milk, and sugar in a heavy saucepan over medium heat. Bring to a boil. Remove mixture from heat and allow to cool. Once cool, place in the refrigerator to chill for 1 hour."));
		recipe.getProcedures().add(new ProcIngredient("Combine tapioca pearls and coconut milk mixture in a bowl. Mix well, then divide among serving glasses. Top with diced mangoes and toasted desiccated coconut. "));
		recipe.setDescription("This Filipino favorite features soft tapioca pearls in coconut milk and a topping of sweet Philippine mangoes. It’s the perfect ending to a feast of local flavors.");
		recipe.setCategory(category);
		recipeList.add(recipe);
		
		recipe = new Recipe();
		recipe.setRecipeName("Earl Grey Fingers");
		recipe.setRecipeImage(R.drawable.earlgreyfingers);
		recipe.setFavorite(false);
		//Makes 24  
		//Prep Time 20 minutes  
		//Baking Time 50 to 60 minutes
		recipe.getIngredients().add(new ProcIngredient("butter for greasing pan"));
		recipe.getIngredients().add(new ProcIngredient("3/4 cup butter"));
		recipe.getIngredients().add(new ProcIngredient("1/2 cup sugar"));
		recipe.getIngredients().add(new ProcIngredient("1/2 teaspoon vanilla extract"));
		recipe.getIngredients().add(new ProcIngredient("1/8 teaspoon salt"));
		recipe.getIngredients().add(new ProcIngredient("1 teaspoon grated orange zest"));
		recipe.getIngredients().add(new ProcIngredient("1 1/2 tablespoons Earl Grey tea leaves (from about 5 teabags)"));
		recipe.getIngredients().add(new ProcIngredient("1 1/2 cups all-purpose flour"));
		recipe.getProcedures().add(new ProcIngredient("Butter the bottom and sides of an 8-inch square pan. Set aside. "));
		recipe.getProcedures().add(new ProcIngredient("Melt butter in a saucepan. Take off the heat and cool slightly. "));
		recipe.getProcedures().add(new ProcIngredient("Add sugar, vanilla, salt, orange zest, and Earl Grey tea leaves. "));
		recipe.getProcedures().add(new ProcIngredient("Add flour to butter mixture and stir until just incorporated."));
		recipe.getProcedures().add(new ProcIngredient("Scrape into prepared pan and pat dough evenly. Refrigerate for 2 hours or overnight."));
		recipe.getProcedures().add(new ProcIngredient("Preheated oven to 300ºF. Bake chilled dough for 50 to 60 minutes or until golden brown. Cool for 10 minutes."));
		recipe.getProcedures().add(new ProcIngredient("Carefully turn shortbread out of pan. Slice the square into 3 even strips. Cut each strip into 8 fingers."));
		recipe.setDescription("The addition of orange zest enhances the bergamot notes of Earl Grey tea. This delicate indulgence is best partnered with tea.");
		recipe.setCategory(category);
		recipeList.add(recipe);
	}
	
	public void populateMainDish(ArrayList<Recipe> recipeList){
		category = 5;
		recipe = new Recipe();
		recipe.setRecipeName("Grilled Lapu-Lapu with Grilled Tomato Sauce and Garlic Mayonnaise");
		recipe.setRecipeImage(R.drawable.grilledlapulapuwithgrilledtomatosauceandgarlicmayonnaise);
		recipe.setFavorite(false);
		//Serves 2 to 3 
		//Prep Time 15 minutes 
		//Cooking Time 10 minutes
		recipe.getIngredients().add(new ProcIngredient("2 (150-gram) lapu-lapu fillets"));
		recipe.getIngredients().add(new ProcIngredient("juice of half a lemon"));
		recipe.getIngredients().add(new ProcIngredient("1 tablespoon olive oil"));
		recipe.getIngredients().add(new ProcIngredient("salt and pepper to taste"));
		recipe.getIngredients().add(new ProcIngredient("leftover Grilled Tomato Soup "));
		recipe.getIngredients().add(new ProcIngredient("For the garlic mayonnaise: "));
		recipe.getIngredients().add(new ProcIngredient("1/2 teaspoon garlic, made into a paste"));
		recipe.getIngredients().add(new ProcIngredient("3 tablespoons Japanese mayonnaise "));
		recipe.getIngredients().add(new ProcIngredient("salt and white pepper to taste"));
		recipe.getProcedures().add(new ProcIngredient("Make the garlic mayonnaise: Mix all ingredients together. Set aside. "));
		recipe.getProcedures().add(new ProcIngredient("Rub fish with lemon juice, olive oil, salt, and pepper. Grill over charcoal or on a grill pan."));
		recipe.getProcedures().add(new ProcIngredient("Use the grilled tomato soup as a bed and top with garlic mayonnaise. Serve with lemon wedges if desired. "));
		recipe.setDescription("Been working hard? It’s important to replenish your energy. One way to do that is to get more iron into your system—and fish is a great source of iron.  Not a fish fan? You can use the lemon juice marinade with chicken and prawns too.");
		recipe.setCategory(category);
		recipeList.add(recipe);

		recipe = new Recipe();
		recipe.setRecipeName("Sole Fillet with Butter Cream Sauce");
		recipe.setRecipeImage(R.drawable.solefilletwithbuttercreamsauce);
		recipe.setFavorite(false);
		//Serves 2  
		//Prep Time 5 minutes  
		//Cooking Time 15 minutes
		recipe.getIngredients().add(new ProcIngredient("2 pieces sole fillet"));
		recipe.getIngredients().add(new ProcIngredient("salt and pepper"));
		recipe.getIngredients().add(new ProcIngredient("1 large egg, beaten"));
		recipe.getIngredients().add(new ProcIngredient("1 cup flour"));
		recipe.getIngredients().add(new ProcIngredient("For the sauce: "));
		recipe.getIngredients().add(new ProcIngredient("1 stick unsalted butter, cut into cubes"));
		recipe.getIngredients().add(new ProcIngredient("1/2 cup cream"));
		recipe.getIngredients().add(new ProcIngredient("1 teaspoon lemon juice"));
		recipe.getIngredients().add(new ProcIngredient("1 teaspoon chives,chopped"));
		recipe.getIngredients().add(new ProcIngredient("pinch of salt"));
		recipe.getIngredients().add(new ProcIngredient("1 teaspoon Spanish paprika"));
		recipe.getProcedures().add(new ProcIngredient("Season fish with salt and pepper. Dip both sides of the fish in egg, then dredge in flour. Shake off excess flour. "));
		recipe.getProcedures().add(new ProcIngredient("Fry on each side until golden brown."));
		recipe.getProcedures().add(new ProcIngredient("For the sauce, melt butter chunks in medium heat then slowly pour in the cream, mixing until you reach a thick consistency. Remove from heat and add the rest of the ingredients. Mix.3  Pour the sauce on top of the fish and garnish with chives. "));
		recipe.getProcedures().add(new ProcIngredient("Serve with brown buttered beans."));
		recipe.setDescription("After a quick pan-frying, simply mix the sauce and voila, you’ve got an easy main dish to woo someone.");
		recipe.setCategory(category);
		recipeList.add(recipe);

		recipe = new Recipe();
		recipe.setRecipeName("Oriental Pork Ribs with Mushrooms and Sausages");
		recipe.setRecipeImage(R.drawable.orientalporkribswithmushroomsandsausages);
		recipe.setFavorite(false);
		//Serves 4 
		//Prep Time 20 minutes, plus marinating time
		//Cooking Time 25 minutes
		recipe.getIngredients().add(new ProcIngredient("For the marinade: "));
		recipe.getIngredients().add(new ProcIngredient("2 tablespoons light soy sauce"));
		recipe.getIngredients().add(new ProcIngredient("2 tablespoons oyster sauce"));
		recipe.getIngredients().add(new ProcIngredient("1 tablespoon sweet soy sauce (kecap manis)"));
		recipe.getIngredients().add(new ProcIngredient("1 tablespoon Chinese cooking wine"));
		recipe.getIngredients().add(new ProcIngredient("1 tablespoon grated ginger"));
		recipe.getIngredients().add(new ProcIngredient("4 cloves garlic, finely chopped"));
		recipe.getIngredients().add(new ProcIngredient("1 teaspoon sesame oil"));
		recipe.getIngredients().add(new ProcIngredient("1 teaspoon baking soda"));
		recipe.getIngredients().add(new ProcIngredient("white pepper, to taste"));
		recipe.getIngredients().add(new ProcIngredient("1 teaspoon sugar"));
		recipe.getIngredients().add(new ProcIngredient("400 grams pork ribs, sliced into 1 1/2-inch pieces"));
		recipe.getIngredients().add(new ProcIngredient("5 fresh or dried and soaked shiitake mushrooms, stems discarded and caps sliced into strips"));
		recipe.getIngredients().add(new ProcIngredient("1 1/2 tablespoons vegetable oil"));
		recipe.getIngredients().add(new ProcIngredient("2 Chinese sausages, sliced diagonally"));
		recipe.getIngredients().add(new ProcIngredient("about 3 cups water"));
		recipe.getIngredients().add(new ProcIngredient("2 cups uncooked rice steamed or blanched bok choy,  to serve"));
		recipe.getProcedures().add(new ProcIngredient("Make the marinade: Combine all ingredients in a large bowl."));
		recipe.getProcedures().add(new ProcIngredient("Add pork ribs and mushrooms. Set aside and marinate for 30 minutes."));
		recipe.getProcedures().add(new ProcIngredient("Heat oil in a wok and lightly fry the Chinese sausages. Set aside."));
		recipe.getProcedures().add(new ProcIngredient("In the same wok, add pork ribs, mushrooms, marinade, and 1 1/2 cups water.  Bring to a boil then simmer until pork is tender. Add more water if the mixture is drying out."));
		recipe.getProcedures().add(new ProcIngredient("Remove meat and mushrooms; set aside. Add water to the sauce until it reaches 3 cups. This will be used to cook the rice."));
		recipe.getProcedures().add(new ProcIngredient("Pour the liquid and the uncooked rice into the rice cooker. Top with the pork, mushrooms, and sliced Chinese sausages. Turn on the rice cooker. Cook until the setting turns to warm. Serve with bok choy on the side."));
		recipe.setDescription("What's the secret to extra flavorful rice toppings? Cook the rice together with the pork ribs, marinade, mushrooms, and sausages. The result is a satisfying dish with delicious layers of flavor.");
		recipe.setCategory(category);
		recipeList.add(recipe);

		recipe = new Recipe();
		recipe.setRecipeName("Garlic Pepper Prawns");
		recipe.setRecipeImage(R.drawable.garlicpepperprawns);
		recipe.setFavorite(false);
		//Serves 3 to 4 
		//Prep Time 5 minutes
		//Cooking Time 10 minutes 
		recipe.getIngredients().add(new ProcIngredient("2 tablespoons butter"));
		recipe.getIngredients().add(new ProcIngredient("6 cloves garlic, minced"));
		recipe.getIngredients().add(new ProcIngredient("3/4 kilo medium-sized prawns"));
		recipe.getIngredients().add(new ProcIngredient("1 tablespoon freshly ground Szechuan pepper"));
		recipe.getIngredients().add(new ProcIngredient("1 tablespoon sugar"));
		recipe.getIngredients().add(new ProcIngredient("1/4 teaspoon Chinese five-spice powder"));
		recipe.getIngredients().add(new ProcIngredient("salt, to taste"));
		recipe.getIngredients().add(new ProcIngredient("juice of 1 lemon"));
		recipe.getProcedures().add(new ProcIngredient("Heat a wok over low heat. Add butter and let it brown lightly. Add garlic and sauté just until fragrant, being careful not to burn the butter and garlic."));
		recipe.getProcedures().add(new ProcIngredient("Increase heat to high and add prawns. Season with Szechuan pepper, sugar, five-spice powder, and salt. Cook prawns quickly, just until they curl and turn pinkish orange. Turn off heat and drizzle lemon juice all over. Transfer to a platter and serve hot."));
		recipe.setDescription("What’s the simplest way to add flavor to a dish? Season it with bold, exotic spices such as Szechuan pepper and five-spice powder.");
		recipe.setCategory(category);
		recipeList.add(recipe);

		recipe = new Recipe();
		recipe.setRecipeName("Seafood and Chicken Paella");
		recipe.setRecipeImage(R.drawable.seafoodchickenpaella);
		recipe.setFavorite(false);
		//Prep Time 25 minutes  
		//Cooking time 45 minutes
		recipe.getIngredients().add(new ProcIngredient("1 medium organic chicken, jointed"));
		recipe.getIngredients().add(new ProcIngredient("all-purpose or plain flour, for dusting chicken"));
		recipe.getIngredients().add(new ProcIngredient("salt and freshly ground black pepper"));
		recipe.getIngredients().add(new ProcIngredient("extra-virgin olive oil"));
		recipe.getIngredients().add(new ProcIngredient("1 chorizo sausage, sliced diagonally 1/4-inch-thick"));
		recipe.getIngredients().add(new ProcIngredient("6 slices bacon"));
		recipe.getIngredients().add(new ProcIngredient("1 onion, finely diced"));
		recipe.getIngredients().add(new ProcIngredient("4 cloves garlic, finely chopped"));
		recipe.getIngredients().add(new ProcIngredient("2 to 3 large pinches saffron, infused in a little of the hot stock"));
		recipe.getIngredients().add(new ProcIngredient("3 1/2 pints (2 liters) chicken stock"));
		recipe.getIngredients().add(new ProcIngredient("1 heaped teaspoon smoked paprika"));
		recipe.getIngredients().add(new ProcIngredient("500 grams paella rice (arroz con bomba or any short-grain rice like Arborio rice)"));
		recipe.getIngredients().add(new ProcIngredient("1 small bunch flat-leaf parsley"));
		recipe.getIngredients().add(new ProcIngredient("1 handful fresh peas"));
		recipe.getIngredients().add(new ProcIngredient("500 grams mussels"));
		recipe.getIngredients().add(new ProcIngredient("8 to10 large prawns, shells and veins removed"));
		recipe.getIngredients().add(new ProcIngredient("2 small squid, trimmed, gutted and cut open, then scored lightly in a criss-cross fashion and cut in to small pieces"));
		recipe.getIngredients().add(new ProcIngredient("1 lemon, to serve"));
		recipe.getIngredients().add(new ProcIngredient("hard-boiled eggs for topping"));
		recipe.getProcedures().add(new ProcIngredient("Preheat the oven to 275ºF (190ºC)."));
		recipe.getProcedures().add(new ProcIngredient("Remove the end bits of bone from the chicken legs, then cut them in half. Cut the chicken breasts in half. Coat all the chicken pieces with flour and salt and pepper."));
		recipe.getProcedures().add(new ProcIngredient("Put some oil in a hot large flat pan and brown the chicken legs and thighs, skin-side down, turning them to brown them all over. Transfer them to a small roasting tray and continue to cook in the preheated oven for 30 minutes"));
		recipe.getProcedures().add(new ProcIngredient("Add the chicken fillets to the pan and let them brown, then add the chorizo slices. Lay the slices of bacon over the top. Once crisp, turn the heat down, add the onion and garlic to the pan, and allow to soften. Pour the infused saffron and some of the stock into the pan, and add the smoked paprika and rice, stirring continuously."));
		recipe.getProcedures().add(new ProcIngredient("Finely chop the parsley stalks and add to the pan. When the rice is almost cooked, add the peas, mussels, prawns and squid, adding more stock if necessary. Cover the pan with foil. When the chicken is cooked, add it to the pan, then add a large handful of chopped parsley leaves. Check the seasoning. Top with hard-boiled eggs."));
		recipe.getProcedures().add(new ProcIngredient("Cut the lemon into wedges, place around the edge of the pan, and serve."));
		recipe.setDescription("Try to get as much shellfish as you can to make your pan of paella more authentic. Chicken stock is great for the flavor—add it gradually as you would with a risotto. You may need more or less depending on the rice so watch the pan.");
		recipe.setCategory(category);
		recipeList.add(recipe);
	}
	
	public void populatePasta(ArrayList<Recipe> recipeList){
		category = 6;
		recipe = new Recipe();
		recipe.setCategory(category);
		recipe.setRecipeName("Penne Al Quattro Formaggi (Four-Cheese Pasta)");
		recipe.setRecipeImage(R.drawable.pennealquattroformaggio);
		recipe.setFavorite(false);
		//Serves 3 
		//Prep Time 8 minutes
		//Cooking Time 15 minutes 
		recipe.getIngredients().add(new ProcIngredient("250 grams of penne pasta"));
		recipe.getIngredients().add(new ProcIngredient("1/4 cup butter "));
		recipe.getIngredients().add(new ProcIngredient("25 grams Gorgonzola cheese "));
		recipe.getIngredients().add(new ProcIngredient("3/4 cup whipping cream"));
		recipe.getIngredients().add(new ProcIngredient("1/4 cup mascarpone cheese"));
		recipe.getIngredients().add(new ProcIngredient("1/4 cup blue cheese"));
		recipe.getIngredients().add(new ProcIngredient("2 teaspoons grated Parmesan cheese"));
		recipe.getIngredients().add(new ProcIngredient("salt and pepper, to taste "));
		recipe.getIngredients().add(new ProcIngredient("a sprig of parsley for garnish"));
		recipe.getProcedures().add(new ProcIngredient("Cook penne pasta in boiling salted water until al dente. "));
		recipe.getProcedures().add(new ProcIngredient("In a saucepan, mix butter and Gorgonzola cheese until melted. Add whipping cream and simmer. Add mascarpone cheese, blue cheese, and 2grated Parmesan cheese; stir the cheeses until dissolved. "));
		recipe.getProcedures().add(new ProcIngredient("Toss in cooked penne pasta. Season with salt and pepper to taste and garnish with a sprig of parsley."));
		recipe.setDescription("Gorgonzola is Italian blue cheese with a strong flavor and aroma. Mascarpone is a mild-tasting soft cheese with a texture of whipped cream. Parmesan is a very firm cheese with a rich, sharp flavor. ");
		recipeList.add(recipe);

		recipe = new Recipe();
		recipe.setCategory(category);
		recipe.setRecipeName("Smoked Bacon and Macaroni Casserole");
		recipe.setRecipeImage(R.drawable.smokedbaconandmacaronicasserole);
		recipe.setFavorite(false);
		//Serves 5 to 6 
		//Prep Time 10 minutes  
		//Cooking Time 20 to 25 minutes
		recipe.getIngredients().add(new ProcIngredient("500-grams macaroni"));
		recipe.getIngredients().add(new ProcIngredient("1/4 kilo honey-cured bacon"));
		recipe.getIngredients().add(new ProcIngredient("2 tablespoons cooking oil"));
		recipe.getIngredients().add(new ProcIngredient("2 big white onions, sliced into rings"));
		recipe.getIngredients().add(new ProcIngredient("1 teaspoon dried basil"));
		recipe.getIngredients().add(new ProcIngredient("1 teaspoon pepper"));
		recipe.getIngredients().add(new ProcIngredient("salt to taste"));
		recipe.getIngredients().add(new ProcIngredient("1 can cream of mushroom soup "));
		recipe.getIngredients().add(new ProcIngredient("1 small box cheddar cheese, grated"));
		recipe.getIngredients().add(new ProcIngredient("1/4 cup Parmesan cheese"));
		recipe.getProcedures().add(new ProcIngredient("Cook macaroni pasta according to package directions."));
		recipe.getProcedures().add(new ProcIngredient("Fry bacon in its own fat until crisp. Drain on paper towels, then crumble. Sauté onion rings in oil.  Set both aside."));
		recipe.getProcedures().add(new ProcIngredient("In a bowl, mix cooked macaroni, basil, pepper, salt, cream of mushroom, crumbled bacon, and half of the cooked onion rings. Add grated cheese (leave some for topping). Transfer mixture to a buttered casserole. "));
		recipe.getProcedures().add(new ProcIngredient("Bake, covered, at 350ºF for 10 minutes."));
		recipe.getProcedures().add(new ProcIngredient("Remove from the oven. Top with the rest of the onion rings, cheddar cheese, and Parmesan cheese. Bake again for a couple of minutes, uncovered, until cheeses melt."));
		recipe.setDescription("For more zest and flavor, use red onion rings infused in olive oil as topping. And for a just-about-perfect dinner, pair this pasta dish with your favorite warm soup.");
		recipeList.add(recipe);

		recipe = new Recipe();
		recipe.setCategory(category);
		recipe.setRecipeName("Garlic Pasta with Adobo Flakes and Poached Eggs");
		recipe.setRecipeImage(R.drawable.garlicpastawithadoboflakesandpoachedeggs);
		recipe.setFavorite(false);
		//Serves 2 
		//Prep Time 10 minutes
		//Cooking Time 25 minutes
		recipe.getIngredients().add(new ProcIngredient("For the poached eggs: "));
		recipe.getIngredients().add(new ProcIngredient("water, for poaching"));
		recipe.getIngredients().add(new ProcIngredient("2 teaspoons vinegar"));
		recipe.getIngredients().add(new ProcIngredient("pinch of salt"));
		recipe.getIngredients().add(new ProcIngredient("2 pieces eggs, chilled"));
		recipe.getIngredients().add(new ProcIngredient("1/4 cup olive oil"));
		recipe.getIngredients().add(new ProcIngredient("5 cloves garlic, minced"));
		recipe.getIngredients().add(new ProcIngredient("200 grams spaghetti, cooked according to package directions"));
		recipe.getIngredients().add(new ProcIngredient("salt and pepper, to taste"));
		recipe.getIngredients().add(new ProcIngredient("1/2 cup adobo flakes"));
		recipe.getIngredients().add(new ProcIngredient("garlic flakes, thinly sliced"));
		recipe.getIngredients().add(new ProcIngredient("basil leaves, and red pepper flakes for garnish"));
		recipe.getIngredients().add(new ProcIngredient("bread sticks, to serve"));
		recipe.getProcedures().add(new ProcIngredient("Poach the eggs:  Fill a saucepan with water to a depth of 3 inches. Season with vinegar and salt. Bring to a boil over high heat, then reduce heat to a simmer. Crack eggs into heatproof cups and gently add eggs one at a time to the poaching liquid. Poach eggs for 3 to 4 minutes. Remove using a slotted spoon; set aside.  (For a step-by-step visual guide, see How to Poach an Egg)"));
		recipe.getProcedures().add(new ProcIngredient("In a pan, heat olive oil. Sauté garlic until brown and fragrant."));
		recipe.getProcedures().add(new ProcIngredient("Toss in cooked pasta. Season with salt and pepper."));
		recipe.getProcedures().add(new ProcIngredient("Divide pasta among bowls. Sprinkle adobo on top. Garnish with garlic flakes, basil leaves, and red pepper flakes. Top each plate with a poached egg. Serve with bread sticks, if desired."));
		recipe.setDescription("");
		recipeList.add(recipe);

		recipe = new Recipe();
		recipe.setCategory(category);
		recipe.setRecipeName("Linguine with Mushroom, Tomato, and Mozzarella");
		recipe.setRecipeImage(R.drawable.linguinewithmushroomtomatoandmozzarella);
		recipe.setFavorite(false);
		//Serves 4  
		//Prep Time 10 minutes  
		//Cooking Time 15 minutes
		recipe.getIngredients().add(new ProcIngredient("200 grams linguine"));
		recipe.getIngredients().add(new ProcIngredient("3 tablespoons olive oil"));
		recipe.getIngredients().add(new ProcIngredient("2 tablespoons garlic"));
		recipe.getIngredients().add(new ProcIngredient("2 cups sliced fresh button mushrooms or  a mix of button, shiitake, and oyster mushrooms"));
		recipe.getIngredients().add(new ProcIngredient("1 can diced tomatoes, drained"));
		recipe.getIngredients().add(new ProcIngredient("1/4 cup white wine"));
		recipe.getIngredients().add(new ProcIngredient("salt and pepper to taste"));
		recipe.getIngredients().add(new ProcIngredient("2 tablespoons chopped fresh basil"));
		recipe.getIngredients().add(new ProcIngredient("buffalo mozzarella, torn for garnish"));
		recipe.getProcedures().add(new ProcIngredient("Cook linguine according to package directions. While cooking, make the sauce. "));
		recipe.getProcedures().add(new ProcIngredient("Sauté garlic and onions in olive oil. Add in mushrooms and diced tomatoes. Pour in wine and simmer until reduced.  "));
		recipe.getProcedures().add(new ProcIngredient("Season with salt and pepper to taste and add in basil. Toss in cooked pasta adding some water if the sauce is too thick. "));
		recipe.getProcedures().add(new ProcIngredient("Transfer to a plate and top with fresh mozzarella."));
		recipe.setDescription("Sharlen Tan on this dish: One of my specialties is pasta, and my friends ask for it often for our get-togethers. I love how I can play around with it, tossing in whatever ingredients I have on hand.");
		recipeList.add(recipe);

		recipe = new Recipe();
		recipe.setCategory(category);
		recipe.setRecipeName("Corned Beef and Spinach Pasta Casserole");
		recipe.setRecipeImage(R.drawable.cornedbeefandspinachpastacasserole);
		recipe.setFavorite(false);
		//Serves 4 to 6  
		//Prep Time 20 minutes  
		//Cooking Time 45 minutes
		recipe.getIngredients().add(new ProcIngredient("225 grams macaroni noodles"));
		recipe.getIngredients().add(new ProcIngredient("4 tablespoons butter, divided"));
		recipe.getIngredients().add(new ProcIngredient("1 cup chopped onions"));
		recipe.getIngredients().add(new ProcIngredient("2 1/2 to 3 cups corned beef"));
		recipe.getIngredients().add(new ProcIngredient("3 cups chopped spinach, blanched and squeezed to remove liquid"));
		recipe.getIngredients().add(new ProcIngredient("3 tablespoons all-purpose flour"));
		recipe.getIngredients().add(new ProcIngredient("1 cup chicken broth"));
		recipe.getIngredients().add(new ProcIngredient("1 cup milk"));
		recipe.getIngredients().add(new ProcIngredient("1/8 teaspoon black pepper"));
		recipe.getIngredients().add(new ProcIngredient("1/4 teaspoon dried thyme"));
		recipe.getIngredients().add(new ProcIngredient("2 cups shredded sharp Cheddar cheese"));
		recipe.getIngredients().add(new ProcIngredient("salt, to taste"));
		recipe.getIngredients().add(new ProcIngredient("3/4 to 1 cup grated Parmesan cheese"));
		recipe.getProcedures().add(new ProcIngredient("Preheat oven to 400°F."));
		recipe.getProcedures().add(new ProcIngredient("Using a large stockpot, cook noodles following package directions; drain well. Put noodles back in the pot and set aside."));
		recipe.getProcedures().add(new ProcIngredient("In a medium saucepan, melt 2 tablespoons butter over medium-low heat; add onions and cook for 2 minutes. Add corned beef and cook until lightly browned. With a slotted spoon, transfer beef mixture to the pot with noodles. Add spinach to the same pot and set aside."));
		recipe.getProcedures().add(new ProcIngredient("In the same medium saucepan, melt remaining 2 tablespoons butter over medium-low heat; stir in flour until well blended and bubbly. Stir in chicken broth and milk.Cook, stirring constantly, until thickened and bubbly. Add pepper and thyme."));
		recipe.getProcedures().add(new ProcIngredient("Stir in Cheddar cheese, cooking until melted. Add salt to taste. Pour sauce over the noodle-corned beef mixture; stir until well blended. Spoon the noodle mixture into a baking dish. Sprinkle with Parmesan cheese and bake for 30 minutes or until hot and lightly browned."));
		recipe.setDescription("If your little ones fancy corned beef, they will absolutely adore this casserole! Think chunky noodles blanketed in a creamy sauce and studded with corned beef and spinach. Dee-lish!");
		recipeList.add(recipe);
	}
	
	public void populateSalad(ArrayList<Recipe> recipeList){
		category = 7;
		recipe = new Recipe();
		recipe.setCategory(category);
		recipe.setRecipeName("Insalata Verde");
		recipe.setRecipeImage(R.drawable.insalataverde);
		recipe.setFavorite(false);
		//Serves 6 to 8  
		//Prep Time 5 minutes
		recipe.getIngredients().add(new ProcIngredient("800 grams mixed greens like oakleaf, romaine, rucola, lola rossa, watercress "));
		recipe.getIngredients().add(new ProcIngredient("3 tablespoons lemon juice or balsamico or white wine vinegar"));
		recipe.getIngredients().add(new ProcIngredient("3 tablespoons lemon juice or coarse salt to taste"));
		recipe.getIngredients().add(new ProcIngredient("6 tablespoons very good quality extra-virgin olive oil"));
		recipe.getIngredients().add(new ProcIngredient("black pepper, freshly ground"));
		recipe.getProcedures().add(new ProcIngredient("Dissolve the coarse salt in the acid-lemon juice or vinegar, and stir."));
		recipe.getProcedures().add(new ProcIngredient("Add extra virgin olive oil  and stir quickly, then add the dressing to the greens in a salad bowl. Toss gently till dressing is spread evenly"));
		recipe.getProcedures().add(new ProcIngredient("Season with freshly ground black pepper and serve."));
		recipe.setDescription("This is the way Italians enjoy their salad: Tossing the greens in good quality, fruity extra virgin olive oil, some lemon juice, and just salt to season, right at the table.  You may replace the lemon with some balsamico or wine vinegar for a more acidic taste, but take note that nothing to sweeten the dressing is ever added. ");
		recipeList.add(recipe);

		recipe = new Recipe();
		recipe.setCategory(category);
		recipe.setRecipeName("Alaska Potato-Crab Salad with Honey Mustard Dressing");
		recipe.setRecipeImage(R.drawable.alaskapotatocrabsaladwithhoneymustard);
		recipe.setFavorite(false);
		recipe.getIngredients().add(new ProcIngredient("500  grams potatoes, boiled and diced"));
		recipe.getIngredients().add(new ProcIngredient("1  cup fresh asparagus, cut into 1-inch sticks and blanched"));
		recipe.getIngredients().add(new ProcIngredient("1  cup button mushrooms, quartered"));
		recipe.getIngredients().add(new ProcIngredient("1  cup young corn, cut into ½ inch pieces"));
		recipe.getIngredients().add(new ProcIngredient("300 grams crabsticks, quartered"));
		recipe.getIngredients().add(new ProcIngredient("1  pack Alaska Crema All-Purpose Cream"));
		recipe.getIngredients().add(new ProcIngredient("2 tablespoons lemon juice or vinegar"));
		recipe.getIngredients().add(new ProcIngredient("3 tablespoons honey"));
		recipe.getIngredients().add(new ProcIngredient("3 tablespoons mustard"));
		recipe.getIngredients().add(new ProcIngredient("salt and pepper"));
		recipe.getProcedures().add(new ProcIngredient("To make the dressing, mix ALASKA CREMA, lemon juice, honey and mustard in a bowl or blender until smooth and creamy. Season with salt and pepper."));
		recipe.getProcedures().add(new ProcIngredient("Place all vegetables and the crabsticks in a separate bowl and toss in dressing and chill before serving."));
		recipe.setDescription("Add zest to your usual potato salad!");
		recipeList.add(recipe);

		recipe = new Recipe();
		recipe.setCategory(category);
		recipe.setRecipeName("Sardine and Orange Salad with Basil-Pesto Dressing");
		recipe.setRecipeImage(R.drawable.sardineandorangesaladwithbasilpestodressing);
		recipe.setFavorite(false);
		//Serves 8 to 10  
		//Prep Time 20 minutes
		recipe.getIngredients().add(new ProcIngredient("For the basil-pesto dressing: "));
		recipe.getIngredients().add(new ProcIngredient("3 cloves garlic "));
		recipe.getIngredients().add(new ProcIngredient("1 1/2 tablespoons honey "));
		recipe.getIngredients().add(new ProcIngredient("1/3 cup apple cider vinegar"));
		recipe.getIngredients().add(new ProcIngredient("1/4 cup extra virgin olive oil "));
		recipe.getIngredients().add(new ProcIngredient("1 cup fresh basil leaves, blanched "));
		recipe.getIngredients().add(new ProcIngredient("For the salad: "));
		recipe.getIngredients().add(new ProcIngredient("1 head Baguio pechay, chopped finely"));
		recipe.getIngredients().add(new ProcIngredient("2 red onions, sliced into rounds "));
		recipe.getIngredients().add(new ProcIngredient("3 oranges, skinned and sliced"));
		recipe.getIngredients().add(new ProcIngredient("1 (230-gram) bottle sardines, scales and bones removed then flaked"));
		recipe.getProcedures().add(new ProcIngredient("Make the dressing: In a blender, blend all ingredients together for 3 minutes. ")); 
		recipe.getProcedures().add(new ProcIngredient("Assemble the salad: Make a bed of Baguio pechay on a serving platter. Layer onions, oranges, and sardines on top. Drizzle with basil-pesto dressing.  "));
		recipe.setDescription("Awaken the palate with this fresh and vibrant salad. This light and simple dish provides balance to an otherwise robust spread.");
		recipeList.add(recipe);

		recipe = new Recipe();
		recipe.setCategory(category);
		recipe.setRecipeName("Couscous Salad");
		recipe.setRecipeImage(R.drawable.couscoussalad);
		recipe.setFavorite(false);
		//Serves 4  
		//Prep Time 10 minutes  
		//Cooking Time 20 minutes
		recipe.getIngredients().add(new ProcIngredient("1 cup water"));
		recipe.getIngredients().add(new ProcIngredient("250 grams couscous"));
		recipe.getIngredients().add(new ProcIngredient("1 yellow bell pepper, deseeded  and diced"));
		recipe.getIngredients().add(new ProcIngredient("1 red bell pepper, deseeded and diced"));
		recipe.getIngredients().add(new ProcIngredient("1 green bell pepper, deseeded and diced"));
		recipe.getIngredients().add(new ProcIngredient("1 cucumber, deseeded and diced"));
		recipe.getIngredients().add(new ProcIngredient("1/2 cup cilantro, chopped"));
		recipe.getIngredients().add(new ProcIngredient("1/4 cup mint, chopped"));
		recipe.getIngredients().add(new ProcIngredient("1 head romaine lettuce"));
		recipe.getIngredients().add(new ProcIngredient("olive oil, as needed"));
		recipe.getIngredients().add(new ProcIngredient("lemon juice, to taste"));
		recipe.getIngredients().add(new ProcIngredient("balsamic vinegar, to taste"));
		recipe.getIngredients().add(new ProcIngredient("1 1/2 teaspoons curry powder"));
		recipe.getIngredients().add(new ProcIngredient("1 1/2 teaspoons ground cumin"));
		recipe.getIngredients().add(new ProcIngredient("salt and pepper, to taste"));
		recipe.getIngredients().add(new ProcIngredient("bell peppers, sliced into rounds for garnish (optional)"));
		recipe.getIngredients().add(new ProcIngredient("toasted pine nuts, for garnish (optional)"));
		recipe.getProcedures().add(new ProcIngredient("Make the couscous: Bring salted water to a boil. Remove from heat and add couscous. Cook over low heat, stirring constantly, until the liquid is fully absorbed."));
		recipe.getProcedures().add(new ProcIngredient("Prepare the vegetables: Combine peppers, cucumbers, herbs, and lettuce in a mixing bowl. Drizzle with olive oil; season with lemon juice and balsamic vinegar. Set aside."));
		recipe.getProcedures().add(new ProcIngredient("Transfer couscous to a flat tray. Drizzle with olive oil and fluff gently with a fork."));
		recipe.getProcedures().add(new ProcIngredient("Season with cumin, curry, salt, and pepper. Let cool to room temperature. Once cool, add vegetables and pine nuts; mix well."));
		recipe.setDescription("Made of semolina, couscous is a great alternative to rice and pasta. Here, we made it perfect for summer by mixing it with a colorful bounty of vegetables for a fresh, savory salad.");
		recipeList.add(recipe);

		recipe = new Recipe();
		recipe.setCategory(category);
		recipe.setRecipeName("Pandesal Veggie Salad");
		recipe.setRecipeImage(R.drawable.pandesalveggiesalad);
		recipe.setFavorite(false);
		//Serves 2 to 3
		recipe.getIngredients().add(new ProcIngredient("For the vinaigrette : "));
		recipe.getIngredients().add(new ProcIngredient("2 tablespoons coconut vinegar"));
		recipe.getIngredients().add(new ProcIngredient("2 cloves garlic, chopped"));
		recipe.getIngredients().add(new ProcIngredient("2 tablespoons vegetable oil"));
		recipe.getIngredients().add(new ProcIngredient("2 tablespoons olive oil"));
		recipe.getIngredients().add(new ProcIngredient("1 piece pandesal, chopped into 1-inch cubes"));
		recipe.getIngredients().add(new ProcIngredient("1 tablespoon olive oil "));
		recipe.getIngredients().add(new ProcIngredient("salt to taste "));
		recipe.getIngredients().add(new ProcIngredient("4 small native onions, peeled and sliced"));
		recipe.getIngredients().add(new ProcIngredient("4 to 5 tomatoes, sliced into quarters"));
		recipe.getIngredients().add(new ProcIngredient("1 deseeded cucumber, sliced into half-moons"));
		recipe.getIngredients().add(new ProcIngredient("a sprig or two of cilantro  for garnish (optional)"));
		recipe.getProcedures().add(new ProcIngredient("Prepare the vinaigrette:  Combine coconut vinegar, garlic, vegetable oil, and  olive oil. Mix well and season with salt and pepper; set aside. "));
		recipe.getProcedures().add(new ProcIngredient("Heat olive oil in a pan and toast pandesal cubes. Season with salt, then set aside and allow to cool. "));
		recipe.getProcedures().add(new ProcIngredient("In a mixing bowl, combine pandesal cubes with onions, tomatoes, and cucumber. Pour dressing over, toss well, and serve immediately. Add cilantro, if desired."));
		recipe.setDescription("Give your greens some extra crunch with toasted pandesal cubes. ");
		recipeList.add(recipe);	
	}
	
	public void populateVegetarian(ArrayList<Recipe> recipeList){
		category = 8;
		recipe = new Recipe();
		recipe.setCategory(category);
		recipe.setRecipeName("Sinampalukang Langka");
		recipe.setRecipeImage(R.drawable.sinampalukanglangka);
		recipe.setFavorite(false);
		//Serves 6  
		//Prep Time 10 minutes  
		//Cooking Time 15 minutes
		recipe.getIngredients().add(new ProcIngredient("4 cups unripe jackfruit (langka), peeled, cored, and cut into"));
		recipe.getIngredients().add(new ProcIngredient("1 1/2-inch strips2 cups water "));
		recipe.getIngredients().add(new ProcIngredient("4 unripe tamarind pods or sinigang sa sampalok powder, to taste "));
		recipe.getIngredients().add(new ProcIngredient("1 onion, sliced "));
		recipe.getIngredients().add(new ProcIngredient("1 (1-inch) piece ginger, sliced into 4 pieces "));
		recipe.getIngredients().add(new ProcIngredient("1 finger chili "));
		recipe.getIngredients().add(new ProcIngredient("1/2 cup small shrimp fish sauce (patis), to taste "));
		recipe.getIngredients().add(new ProcIngredient("fresh tamarind leaves for garnish"));
		recipe.getProcedures().add(new ProcIngredient("Put jackfruit and water in a stockpot and bring to a boil; cook for 5 minutes or until jackfruit is almost tender.")); 
		recipe.getProcedures().add(new ProcIngredient("Add tamarind pods, onions, ginger, and chili; cook until tender. ")); 
		recipe.getProcedures().add(new ProcIngredient("Add shrimp and cook until they turn pink. Season with fish sauce. Discard tamarind pods. 4  Remove from heat and add tamarind leaves. Serve hot."));
		recipe.setDescription("This tasty side dish is the perfect complement to grilled or fried Filipino viands. The wonderful tanginess refreshes the palate and cuts through the richness of meats. ");
		recipeList.add(recipe);

		recipe = new Recipe();
		recipe.setCategory(category);
		recipe.setRecipeName("Creamy Broccoli and Bean Soup");
		recipe.setRecipeImage(R.drawable.creamybroccoliandbeansoup);
		recipe.setFavorite(false);
		//Serves 4  
		//Prep Time 20 minutes  
		//Cooking Time 15 minutes
		recipe.getIngredients().add(new ProcIngredient("2 tablespoons olive oil"));
		recipe.getIngredients().add(new ProcIngredient("1/3 cup chopped white onions"));
		recipe.getIngredients().add(new ProcIngredient("2 teaspoons minced garlic"));
		recipe.getIngredients().add(new ProcIngredient("250 grams broccoli florets"));
		recipe.getIngredients().add(new ProcIngredient("1 (400-gram) can natural white beans, drained"));
		recipe.getIngredients().add(new ProcIngredient("2 1/2 cups vegetable or chicken stock"));
		recipe.getIngredients().add(new ProcIngredient("salt and freshly ground black pepper, to taste"));
		recipe.getIngredients().add(new ProcIngredient("croutons, for garnish (optional)"));
		recipe.getProcedures().add(new ProcIngredient("In a medium pot, heat oil and sauté onions until translucent. Add garlic and sauté until fragrant. Add broccoli florets and sauté for a few seconds."));
		recipe.getProcedures().add(new ProcIngredient("Add beans and stock; bring mixture to a simmer. Season with salt and pepper. Cook for 10 minutes until broccoli is tender."));
		recipe.getProcedures().add(new ProcIngredient("Turn off heat. using a blender or food processor, purée soup in batches until creamy."));
		recipe.getProcedures().add(new ProcIngredient("Return soup to a clean pot and bring to a boil. Taste and adjust seasoning. Before serving, garnish with bread croutons, if desired."));
		recipe.setDescription("The starch in legumes gives the soup a thick, creamy texture, making it the ideal thickener for vegetarians and those who are lactose-intolerant.");
		recipeList.add(recipe);

		recipe = new Recipe();
		recipe.setCategory(category);
		recipe.setRecipeName("Vegetable Slaw with Miso Dressing");
		recipe.setRecipeImage(R.drawable.vegetableslawwithmisodressing);
		recipe.setFavorite(false);
		//Serves 4
		recipe.getIngredients().add(new ProcIngredient("For the miso dressing: "));
		recipe.getIngredients().add(new ProcIngredient("1/4 cup red miso"));
		recipe.getIngredients().add(new ProcIngredient("2 tablespoons water"));
		recipe.getIngredients().add(new ProcIngredient("2 tablespoons mirin"));
		recipe.getIngredients().add(new ProcIngredient("2 tablespoons lemon juice"));
		recipe.getIngredients().add(new ProcIngredient("1 tablespoon honey"));
		recipe.getIngredients().add(new ProcIngredient("3/4 tablespoon sesame oil"));
		recipe.getIngredients().add(new ProcIngredient("1 teaspoon soy sauce"));
		recipe.getIngredients().add(new ProcIngredient("1/2 teaspoon grated ginger"));
		recipe.getIngredients().add(new ProcIngredient("1/4 cup vegetable oil"));
		recipe.getIngredients().add(new ProcIngredient("For the salad: "));
		recipe.getIngredients().add(new ProcIngredient("300 grams jicama (singkamas), peeled and julienned"));
		recipe.getIngredients().add(new ProcIngredient("1 (6-inch) piece cucumber, peeled, seeded, and julienned"));
		recipe.getIngredients().add(new ProcIngredient("1 large carrot, peeled and julienned"));
		recipe.getIngredients().add(new ProcIngredient("8 pieces kani sticks, shredded"));
		recipe.getIngredients().add(new ProcIngredient("1 1/2 tablespoons chopped cilantro (optional)"));
		recipe.getIngredients().add(new ProcIngredient("toasted sesame seeds for garnish "));
		recipe.getProcedures().add(new ProcIngredient("Make the miso dressing: In a bowl, combine all the ingredients except for the vegetable oil. Mix well using a whisk. Add vegetable oil in a slow, steady stream and continue whisking vigorously until emulsified."));
		recipe.getProcedures().add(new ProcIngredient("In a large bowl, combine jicama, cucumber, carrot, and shredded kani. Toss vegetables with enough miso dressing to coat, or serve dressing on the side if preferred. Sprinkle with chopped cilantro and toasted sesame seeds."));
		recipe.setDescription("Planning an Asian-inspired dinner? This light, refreshing salad makes for a delicious match to tempura, grilled chicken, or steak teppanyaki.");
		recipeList.add(recipe);
	}
	
	

}
