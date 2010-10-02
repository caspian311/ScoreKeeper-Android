package net.todd.scorekeeper;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class MainActivity extends Activity {
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			
			RelativeLayout mainLayout = new RelativeLayout(this);
			mainLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			mainLayout.setBackgroundColor(0xFF3399CC);
			
			TextView title = new TextView(this);
			title.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			title.setText("Score Keeper");
			title.setTextSize(30);
			title.setTextColor(0xFF000000);
			title.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
			mainLayout.addView(title);
			
			TableLayout tableView = new TableLayout(this);
			tableView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			tableView.setStretchAllColumns(true);
			tableView.setGravity(Gravity.CENTER);
			mainLayout.addView(tableView);
			
			TableRow firstRow = new TableRow(this);
			tableView.addView(firstRow);
			
			Button addPlayersButton = new Button(this);
			addPlayersButton.setText("Add Players");
			addPlayersButton.setTextSize(20);
			firstRow.addView(addPlayersButton);
			Button historyButton = new Button(this);
			historyButton.setText("See History");
			historyButton.setTextSize(20);
			firstRow.addView(historyButton);

			TableRow secondRow = new TableRow(this);
			tableView.addView(secondRow);

			Button startGameButton = new Button(this);
			startGameButton.setText("Start Game");
			startGameButton.setTextSize(20);
			secondRow.addView(startGameButton);
			Button quitButton = new Button(this);
			quitButton.setText("Quit");
			quitButton.setTextSize(20);
			secondRow.addView(quitButton);
			
			setContentView(mainLayout);
		}
}

//public class MainActivity extends Activity {
//    TextView tv;
//    EditText et;
//    LinearLayout ll;
//
//    /** Called when the activity is first created. */
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        //LinearLayout ll = new LinearLayout(this);
//        ll = new LinearLayout(this);
//        ll.setOrientation(android.widget.LinearLayout.VERTICAL);
//        ll.setLayoutParams(new ViewGroup.LayoutParams(-1,-1));
//        // ARGB: Opaque Red
//        ll.setBackgroundColor(0x88ff0000);
//
////        tv = new TextView(this);
////        tv.setLayoutParams(new ViewGroup.LayoutParams(-1,-2));
////        tv.setText("sample text goes here");
////        // ARGB: Opaque Green
////        tv.setBackgroundColor(0x5500ff00);
////        ll.addView(tv);
////
////        et = new EditText(this);
////        et.setLayoutParams(new ViewGroup.LayoutParams(-1,-2));
////        et.setText("edit me please");
////        // ARGB: Solid Blue
////        et.setBackgroundColor(0xff0000ff);
////        ll.addView(et);
////
////        Button btn = new Button(this);
////        btn.setText("Go!");
////        btn.setOnClickListener(new Button.OnClickListener() {
////            @Override
////			public void onClick(View v) {
////                tv.setText(et.getText().toString());
////            }
////        });
////
////        ll.addView(btn);
//        setContentView(ll);
//
//        //setContentView(R.layout.main);
//    }
//}
