package com.example.zero1;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;

public class TrainsInfoActivity extends Activity {
	
	ActionBar actionbar;
	Bundle bundle;
	TrainInfoListAdapter listviewadapter;
	ArrayList<TrainInfoHoder> arraylist=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_trains_info);
		actionbar=getActionBar();
		
		//设置adapter
		ListView listview=(ListView) findViewById(R.id.trainlist);
		View view = getLayoutInflater().inflate(R.layout.activity_trains_info_control, null);

		mPopupWindow = new PopupWindow(view, LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		//注意button要从绑定的布局里拿
		Button button = (Button) view.findViewById(R.id.daybefor);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub									
			}});
		listview.setOnTouchListener(new OnTouchListener(){
			
			
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				// TODO Auto-generated method stub
				// TODO Auto-generated method stub
				Handler mHideHandler = new Handler();
				Runnable mHideRunnable = new Runnable() {
					@Override
					public void run() {
						if(mPopupWindow!=null)
							mPopupWindow.dismiss();
						hasdelaytask=false;
					}
				};
				switch (arg1.getAction()) {
					case MotionEvent.ACTION_MOVE:	//按下监听
						View view = getLayoutInflater().inflate(R.layout.activity_trains_info_control, null);
						//mPopupWindow不显示时
//						if (mPopupWindow == null) {
							//设置mPopupWindow
							mPopupWindow = new PopupWindow(view, LayoutParams.MATCH_PARENT,
									LayoutParams.WRAP_CONTENT);
							//注意button要从绑定的布局里拿
							Button button = (Button) view.findViewById(R.id.daybefor);
							button.setOnClickListener(new OnClickListener() {
								@Override
								public void onClick(View arg0) {
									// TODO Auto-generated method stub									
								}});
//						}
						//设置mPopupWindow为显示，并设置位置
						mPopupWindow.showAtLocation(findViewById(R.id.trainlist),
									Gravity.RIGHT | Gravity.BOTTOM, 0, 0);		
						break;
					case MotionEvent.ACTION_UP: //抬起监听
						if(!hasdelaytask){
							mHideHandler.postDelayed(mHideRunnable, 3000);
							hasdelaytask=true;
						}
							
						break;
					default:
						break;
				}

				//注意：这里要返回true
				return false;
			}});
		listviewadapter=new TrainInfoListAdapter(this,arraylist); 
		listview.setAdapter(listviewadapter);
		listview.setOnItemClickListener(listviewadapter);
		//初始化bundle asynchronous要用
		bundle=getIntent().getBundleExtra("traininfo");
		new TicketClientQueryTrainTask().execute(AppActivity.tc);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.trains_info, menu);
		return true;
	}


	private class TicketClientQueryTrainTask extends AsyncTask<TicketClient, Void, ArrayList<TrainInfoHoder>> {

		@Override
		protected ArrayList<TrainInfoHoder> doInBackground(TicketClient... params) {
			// TODO Auto-generated method stub

//			String s=params[0].queryTrainInfo(null,TicketClient.queryurlformat,bundle.getString("date"),bundle.getString("from_station"),bundle.getString("to_station"), "ADULT");
			String s=params[0].queryTrainInfoAgentAndroid(null, TicketClient.queryurlAgAndroidformat, bundle.getString("date"), bundle.getString("from_station"), bundle.getString("to_station"), "ADULT");
			
//			return Utility.ParseJsonToArray(s);
			return Utility.ParseAgAndrJsonToArray(s);
		}
	     protected void onPostExecute(ArrayList<TrainInfoHoder> result) {
	    	 Log.i("fuck", "notifydatasetchanged");
	    	 listviewadapter.setTrainarray(result);
	         listviewadapter.notifyDataSetChanged();
	     }
	 
	 
	}
	private PopupWindow mPopupWindow;
	boolean hasdelaytask=false;
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		if(hasdelaytask==true){
			mPopupWindow.dismiss();
			hasdelaytask=false;
		}
		super.onPause();
	}
}
