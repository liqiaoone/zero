package com.example.zero1;

//import android.app.ActionBar;
import com.example.zero1.account.AccountManager;
import com.example.zero1.account.User;

import android.accounts.Account;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
//import android.support.v7.*;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.view.LayoutInflater;
//import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

public class AppActivity extends android.support.v7.app.ActionBarActivity implements ActionBar.TabListener{
	FragmentPagerAdapter ticketPageAdapter;
	ViewPager viewpager;
    private LayoutInflater inflater;
    static android.support.v7.app.ActionBar actionbar;
    static TicketClient tc;
    static AccountManager am=new AccountManager();
	@Override 
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		actionbar = getSupportActionBar();
		actionbar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		viewpager=(ViewPager) findViewById(R.id.pager);
		FragmentPagerAdapter fpa=new MyPagerAdater(getSupportFragmentManager());
		viewpager.setAdapter(fpa);
		tc=new TicketClient(getApplicationContext());
		Tab tab=actionbar.newTab().setText(fpa.getPageTitle(0)).setTabListener(
				this);
	    actionbar.addTab(tab);
		Tab tab2=actionbar.newTab().setText(fpa.getPageTitle(1)).setTabListener(
				this);
	    actionbar.addTab(tab2);
		Tab tab3=actionbar.newTab().setText(fpa.getPageTitle(2)).setTabListener(
				this);
	    actionbar.addTab(tab3);
		viewpager
		.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				actionbar.setSelectedNavigationItem(position);
			}
		});
		new TicketClientInitTask().execute(tc);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.appactivity_main, menu);
		return true;
	}
	public void onRadioButtonClicked(View view) {
	    // Is the button now checked?
	    boolean checked = ((RadioButton) view).isChecked();
	    
	    // Check which radio button was clicked
	    switch(view.getId()) {
	        case R.id.radio_left_btn:
	            if (checked)
	            	 
	            break;
	        case R.id.radio_right_btn:
	            if (checked)
	                // Ninjas rule
	            break;
	    }
	}
	public void onStationSelectCilcked(View view){
		startActivity(new Intent(this,CityChoose.class));
	}
	public void onDateSelectClicked(View view){
		startActivity(new Intent(this,DateChoose.class));
	}
	public void onTimeIntervalClicked(View view){
//		startActivity(new Intent(this,DateChoose.class));
		Builder builder = new AlertDialog.Builder(this);
		inflater = LayoutInflater.from(this);
		View layout = inflater.inflate(R.layout.timeintervalselect, null);
        builder.setView(layout);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
	}
	private class TicketClientInitTask extends AsyncTask<TicketClient, Void, String> {

		@Override
		protected String doInBackground(TicketClient... params) {
			// TODO Auto-generated method stub

			params[0].updateStationInfo();
			
			return null;
		}		
	}
	//Handling clicks on action items
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.inf_user:
	        	openUserInfo();
	            return true;
	        case R.id.action_add:
	   
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	void openUserInfo(){
		if(!am.hasLoginedUser()){
			startActivity(new Intent(this,LoginActivity.class));
		}
		
	}

	@Override
	public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		viewpager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}
	

}
