package Fragments.RecipeFragments;

import java.util.ArrayList;

import com.example.chefie.MainActivity;
import com.example.chefie.R;
import com.example.chefie.adapters.ProcIngAdapter;
import com.example.chefie.classes.ProcIngredient;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

public class RecipeProcedure extends ListFragment{
private ArrayList<ProcIngredient> procedures;
	
	public RecipeProcedure(ArrayList<ProcIngredient> procedures) {
		// TODO Auto-generated constructor stub
		Log.v(MainActivity.SECOND_KEY, this.getClass().getName() + " cosntructor invoked");
		this.procedures = procedures;
		Log.v(MainActivity.SECOND_KEY, this.getClass().getName() + " cosntructor end");
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		Log.v(MainActivity.SECOND_KEY, this.getClass().getName() + " onActivity invoked");
		ArrayList<String> procs = new ArrayList<String>();
		for(ProcIngredient p:procedures){
			procs.add(p.getProcIngred());
		}
		setListAdapter(new ProcIngAdapter(getActivity(), procs.toArray(new String[procs.size()]), procedures));
		Log.v(MainActivity.SECOND_KEY, this.getClass().getName() + " onActivity end");
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		ImageButton ib = (ImageButton) v.findViewById(R.id.check);
		if(procedures.get(position).isCheck()){
			ib.setImageResource(R.drawable.check_empty);
			procedures.get(position).setCheck(false);

		}else{
			ib.setImageResource(R.drawable.check);
			procedures.get(position).setCheck(true);
		}
	}
}
