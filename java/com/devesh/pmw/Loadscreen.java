package com.devesh.pmw;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class Loadscreen extends Activity {

	@Override
	protected void onCreate(Bundle loader) {
		// TODO Auto-generated method stub
		super.onCreate(loader);
		setContentView(R.layout.mainscreen);
		Thread timer= new Thread(){
		public void run()
		{
			try{
			sleep(2000);
		}
			catch(InterruptedException e)
			{
				
			}
			finally{
		Intent starter=new Intent(getApplicationContext(), MainActivity.class);
		startActivity(starter);
		}
		
		
	};
     
	};
	timer.start();
}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}
	
}
