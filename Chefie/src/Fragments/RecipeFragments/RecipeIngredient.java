package Fragments.RecipeFragments;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.chefie.MainActivity;
import com.example.chefie.R;
import com.example.chefie.adapters.ProcIngAdapter;
import com.example.chefie.classes.ProcIngredient;

public class RecipeIngredient extends ListFragment{
	private ArrayList<ProcIngredient> ingredients;
	
	public RecipeIngredient(ArrayList<ProcIngredient> ingredients) {
		// TODO Auto-generated constructor stub
		Log.v(MainActivity.SECOND_KEY, this.getClass().getName() + " cosntructor invoked");
		this.ingredients = ingredients;
		Log.v(MainActivity.SECOND_KEY, this.getClass().getName() + " constructor end");
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		Log.v(MainActivity.SECOND_KEY, this.getClass().getName() + " onActivity invoked");
		ArrayList<String> ings = new ArrayList<String>();
		for(ProcIngredient p:ingredients){
			ings.add(p.getProcIngred());
		}

//		View header = getLayoutInflater(savedInstanceState).inflate(R.layout.proc_ing_header, null);
//		header.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
//				ImageButton ib;
////				for(int i=0;i<ingredients.size();i++){
////					ingredients.get(i).setCheck(false);
////					ib = getListAdapter().getItemViewType(R.layout.);
////					ib.setImageResource(R.drawable.check_empty);
////				}
//				Log.v("success", "helloo");
//				for(ProcIngredient p:ingredients){
//					p.setCheck(false);
//					Log.v("success", "yes");
//					
//				}
//
//			}
//		});
//		getListView().addHeaderView(header);
		setListAdapter(new ProcIngAdapter(getActivity(), ings.toArray(new String[ings.size()]), ingredients));
		Log.v(MainActivity.SECOND_KEY, this.getClass().getName() + " onActivity end");
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		ImageButton ib = (ImageButton) v.findViewById(R.id.check);
		if(ingredients.get(position).isCheck()){
			ib.setImageResource(R.drawable.check_empty);
			ingredients.get(position).setCheck(false);

		}else{
			ib.setImageResource(R.drawable.check);
			ingredients.get(position).setCheck(true);
			
		}
	}

	public ArrayList<ProcIngredient> getIngredients() {
		return ingredients;
	}

	public void setIngredients(ArrayList<ProcIngredient> ingredients) {
		this.ingredients = ingredients;
	}
	
	
	
//	public void clearer(View v){
//		ImageButton ib;
//		for(int i=0;i<ingredients.size();i++){
//			ingredients.get(i).setCheck(false);
////			ib = (ImageButton) getListAdapter().getItem(i);
////			ib.setImageResource(R.drawable.check_empty);
//		}
//	}
}
