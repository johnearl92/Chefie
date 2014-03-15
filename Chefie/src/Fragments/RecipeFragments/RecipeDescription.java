package Fragments.RecipeFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chefie.MainActivity;
import com.example.chefie.R;
import com.example.chefie.classes.Recipe;

public class RecipeDescription extends Fragment{
	private Recipe recipe;
	
	public RecipeDescription(Recipe recipe) {
		// TODO Auto-generated constructor stub
		Log.v(MainActivity.SECOND_KEY, this.getClass().getName() + " cosntructor invoked");
		this.recipe = recipe;
		Log.v(MainActivity.SECOND_KEY, this.getClass().getName() + " constructor end");
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Log.v(MainActivity.SECOND_KEY, this.getClass().getName() + " onCreateView  invoked");
		View view = inflater.inflate(R.layout.description, container, false);
		
		TextView tv = (TextView) view.findViewById(R.id.rectitle);
		tv.setText(recipe.getRecipeName());
		
		ImageButton star = (ImageButton) view.findViewById(R.id.star);
		if(recipe.isFavorite()){
			star.setImageResource(R.drawable.fav);
		}
		else{
			star.setImageResource(R.drawable.not);
		}
		
		TextView desc = (TextView) view.findViewById(R.id.recdesc);
		desc.setText("\t \t"+recipe.getDescription());
		
		ImageView recipeImage = (ImageView) view.findViewById(R.id.recimage);
		recipeImage.setImageResource(recipe.getRecipeImage());
		Log.v(MainActivity.SECOND_KEY, this.getClass().getName() + " onCreateView end");
		return view;
	}
	
//	public void favRemover(View v){
//		ImageButton star = (ImageButton)v;
//		if(recipe.isFavorite()){
//			recipe.setFavorite(false);
//			star.setImageResource(R.drawable.check_empty);
//			Toast.makeText(getActivity(), "This recipe is removed from favorites", Toast.LENGTH_SHORT).show();
//	
//		}
//		else{
//			recipe.setFavorite(true);
//			star.setImageResource(R.drawable.check);
//			Toast.makeText(getActivity(), "This Recipe is added to your favorites", Toast.LENGTH_SHORT).show();
//		}
//	}
}
