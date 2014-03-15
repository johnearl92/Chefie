package com.example.chefie.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.chefie.MainActivity;
import com.example.chefie.R;
import com.example.chefie.classes.ProcIngredient;

public class ProcIngAdapter extends ArrayAdapter<String>{
	private final Context context;
	private final String[] ingProc;
	private final ArrayList<ProcIngredient> val;
	
	public ProcIngAdapter(Context context,String[] ing,ArrayList<ProcIngredient> val ) {
		super(context, R.layout.ingredient,ing);
		// TODO Auto-generated constructor stub
		Log.v(MainActivity.SECOND_KEY, this.getClass().getName() + " cosntructor invoked");
		this.context = context;
		this.ingProc = ing;
		this.val = val;
		Log.v(MainActivity.SECOND_KEY, this.getClass().getName() + " cosntructor end");
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Log.v(MainActivity.SECOND_KEY, this.getClass().getName() + " getVIew invoked");
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		Log.v(MainActivity.SECOND_KEY, this.getClass().getName() + " Creating View");
		View rowView;
		if(convertView==null){
			rowView = inflater.inflate(R.layout.ingredient, parent, false);
			
		}
		else{
			rowView = convertView;
		}
		Log.v(MainActivity.SECOND_KEY, this.getClass().getName() + " view created");
		
		Log.v(MainActivity.SECOND_KEY, this.getClass().getName() + " creating text");
		TextView tv = (TextView) rowView.findViewById(R.id.text);
		tv.setText(ingProc[position]);
		Log.v(MainActivity.SECOND_KEY, this.getClass().getName() + " text created");
		
		Log.v(MainActivity.SECOND_KEY, this.getClass().getName() + " creating check");
		ImageButton ib = (ImageButton) rowView.findViewById(R.id.check);
		ib.setFocusable(false);
		if(val.get(position).isCheck()){
			ib.setImageResource(R.drawable.check);
		}
		else{
			ib.setImageResource(R.drawable.check_empty);
		}
		final int pos = position;
		ib.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ImageButton ib = (ImageButton) v.findViewById(R.id.check);
				if(val.get(pos).isCheck()){
					ib.setImageResource(R.drawable.check_empty);
					val.get(pos).setCheck(false);

				}else{
					ib.setImageResource(R.drawable.check);
					val.get(pos).setCheck(true);
				}
			}
		});
		Log.v(MainActivity.SECOND_KEY, this.getClass().getName() + " check created");
		Log.v(MainActivity.SECOND_KEY, this.getClass().getName() + " getView end");
		return rowView;
	}

}
