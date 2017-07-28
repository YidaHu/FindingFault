package com.huyd.findingfault;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.huyd.views.MyBean;

public class MainActivity extends AppCompatActivity {

	private Button btnStart, btnQuit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		btnStart = (Button) findViewById(R.id.btnStart);
		btnQuit = (Button) findViewById(R.id.btnQuit);

		btnStart.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(MainActivity.this, GameActivity.class);
				startActivity(intent);
				MyBean bean = new MyBean();

			}
		});

		btnQuit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

			}
		});
	}
}
