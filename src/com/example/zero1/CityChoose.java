package com.example.zero1;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;

public class CityChoose extends Activity {
	
	CityAdapter hotadpter;
	CityAdapter rcadpter;
	int[] a={1,2,3,4};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.city_choose_main);
		super.onCreate(savedInstanceState);
		hotadpter =new CityAdapter(a,this);
		rcadpter =new CityAdapter(a,this);
		GridView gdview=(GridView)findViewById(R.id.hotgd);
		GridView rcgdview=(GridView)findViewById(R.id.recentlygd);
		gdview.setAdapter(hotadpter);
		gdview.setNumColumns(3);
		rcgdview.setAdapter(rcadpter);
		rcgdview.setNumColumns(3);
	}

}