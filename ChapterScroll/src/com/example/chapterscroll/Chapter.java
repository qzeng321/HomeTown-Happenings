package com.example.chapterscroll;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;




import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;
import android.widget.AdapterView.OnItemClickListener;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.View;
import android.view.MotionEvent;

public class Chapter extends Activity {


	    private static final int SWIPE_MIN_DISTANCE = 120;
	    private static final int SWIPE_MAX_OFF_PATH = 250;
	    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
	    private GestureDetector gestureDetector;
	    
		private static final String TAG = "Transition";
		ArrayList<String> arrlst ;
	    List<HashMap<String, Object>> fillMaps = new ArrayList<HashMap<String, Object>>();
	    SimpleAdapter adapter;
	    HashMap<String, Object> map = new HashMap<String, Object>();
		ListView listView ;
		int addDate=0;			
	
	    /** Called when the activity is first created. */
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.main);
			
			listView= (ListView) findViewById(R.id.listView1);
			DateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy");
			if(this.getIntent().hasExtra("date")){
			addDate=this.getIntent().getExtras().getInt("date");
			}
			//get current date time with Date()
			Date date = new Date();
			Calendar c = Calendar.getInstance();
			c.setTime((date));
			c.add(Calendar.DATE, addDate);  // number of days to add
			((TextView) findViewById(R.id.textView1)).setText(dateFormat.format(c.getTime()));
			String[] from = new String[] { "title", "description" ,"distance","time"};
		    int[] to = new int[] { R.id.title, R.id.description, R.id.distance ,R.id.time};
			

		    adapter = new SimpleAdapter(this, fillMaps, R.layout.listlayout, from, to);
		    listView.setAdapter(adapter);
		    
			TextView text=(TextView) findViewById(R.id.textView1);
			
			// when the add button is clicked the text in the Edit Text is added to the list
			text.setOnLongClickListener(new OnLongClickListener() {
				@Override
				public boolean onLongClick(View v) {
					// TODO Auto-generated method stub
					Log.d("main", "qqq fl");
					map = new HashMap<String, Object>();
				    map.put("title", "Second title");
				    map.put("description", "description 2");
				    map.put("distance", "2 mi"); 
				    map.put("time", "7:00 PM"); 
				    fillMaps.add(map);
				    adapter.notifyDataSetChanged();
					return true;
				}
			});
			
			// when an item is clicked, displays a toast notification
			listView.setOnItemClickListener(new OnItemClickListener() {
	            @Override
	            public void onItemClick(AdapterView<?> parent, View view, int position,
	                    long id) {                
	            	// can call any function to act on item list
	                String item = "TOAST!!!!";  
	                
	                // toast that displays list string
	                Toast.makeText(getBaseContext(), item, Toast.LENGTH_LONG).show();
	                
	            }
	        });
	        gestureDetector = new GestureDetector(new MyGestureDetector());
	        View mainview = (View) findViewById(R.id.mainView);
	 
	        // Set the touch listener for the main view to be our custom gesture listener
	        mainview.setOnTouchListener(new View.OnTouchListener() {
	            public boolean onTouch(View v, MotionEvent event) {
	                if (gestureDetector.onTouchEvent(event)) {
	                    return true;
	                }
	                return false;
	            }
	        });
	   	 
	        // Set the touch listener for the main view to be our custom gesture listener
	        listView.setOnTouchListener(new View.OnTouchListener() {
	            public boolean onTouch(View v, MotionEvent event) {
	                if (gestureDetector.onTouchEvent(event)) {
	                    return true;
	                }
	                return false;
	            }
	        });
	    }
	 
	    class MyGestureDetector extends SimpleOnGestureListener {
	        @Override
	        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
		    Intent intent = new Intent(Chapter.this.getBaseContext(), Chapter.class);
		    
	            if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH) {
	                return false;
	            }
	 
	            // right to left swipe
	            if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
	            	intent.putExtra("date", addDate+1);
	            	startActivity(intent);
	    		Chapter.this.overridePendingTransition(
				R.anim.slide_in_right,
				R.anim.slide_out_left
	    		);
	    	    // right to left swipe
	            }  else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
	            	intent.putExtra("date", addDate-1);
	            	startActivity(intent);
	    		Chapter.this.overridePendingTransition(
				R.anim.slide_in_left, 
				R.anim.slide_out_right
	    		);
	            }
	 
	            return false;
	        }
	 
	        // It is necessary to return true from onDown for the onFling event to register
	        @Override
	        public boolean onDown(MotionEvent e) {
		        	return true;
	        }
	    }
	}