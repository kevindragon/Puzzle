package kevin.jiang.puzzle;

import android.os.Bundle;
import android.app.Activity;
import android.content.res.Resources;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
//import android.view.Window;

public class MainActivity extends Activity implements OnTouchListener {
	private int[] imbtns = {R.id.imageButton1, R.id.imageButton2, R.id.imageButton3, 
    		R.id.imageButton4, R.id.imageButton5, R.id.imageButton6, 
    		R.id.imageButton7, R.id.imageButton8, R.id.imageButton9, 
    		R.id.imageButton10, R.id.imageButton11, R.id.imageButton12};
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 隐藏title
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        // 设置事件监听
        setEventListene();
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    public void setEventListene() {
        for (int i=0; i<9; i++) {
        	ImageButton btn = (ImageButton) findViewById(imbtns[i]);
        	btn.setId(i+1);
        	btn.setOnTouchListener(this);
        }
    	ImageButton btn = (ImageButton) findViewById(R.id.imageButton12);
    	btn.setId(12);
    	btn.setOnTouchListener(this);
    }

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		float inX = 0, inY = 0, x = 0, y = 0;
    	int btnWidth = v.getWidth();
    	int btnHeight = v.getHeight();
		int eventaction = event.getAction();
		
		switch (eventaction) {
		// 接触屏幕
		case MotionEvent.ACTION_DOWN:
			inX = x = event.getX();
			inY = y = event.getY();
			Log.i("Puzzle", "ACTION_DOWN: " + x + " - " + y);
			break;
		// 离开屏幕
		case MotionEvent.ACTION_UP:
			float horizontalMovement = event.getX() - inX;
			float verticalMovement = event.getY() - inY;
			int direction = 0;
			if (horizontalMovement > 0 && horizontalMovement > btnWidth/3.0) {
				direction = PuzzlePosition.RIGHT;
				Log.i("Puzzle", "ACTION_UP: 向右移动");
			} else if (horizontalMovement < 0 && Math.abs(horizontalMovement) > btnWidth/2.0) {
				direction = PuzzlePosition.LEFT;
				Log.i("Puzzle", "ACTION_UP: 向左移动");
			}
			if (verticalMovement > 0 && verticalMovement > btnHeight/3.0) {
				direction = PuzzlePosition.DOWN;
				Log.i("Puzzle", "ACTION_UP: 向下移动");
			} else if (verticalMovement < 0 && Math.abs(verticalMovement) > btnHeight/2.0) {
				direction = PuzzlePosition.UP;
				Log.i("Puzzle", "ACTION_UP: 向上移动");
			}
			
			int[] xy = PuzzlePosition.getXY(v.getId()-1);
			
			Log.i("Puzzle", "id: " + v.getId() + ", xy: " + xy[0] + ", " + xy[1]);
			Log.i("Puzzle", x + " | " + y + " | " + 
					horizontalMovement + " | " + verticalMovement + " | " +
					btnWidth + " | " + btnHeight);
			Log.i("Puzzle", "canMoveDirection: " + 
					PuzzlePosition.canMoveDirection(xy, direction) + ", " +
					PuzzlePosition.getIndex(xy, direction));
			
			int [] states = v.getDrawableState();
			for (int i=0; i<states.length; i++) {
				Log.i("Puzzle", "drable state: " + String.valueOf(states[i]));
			}
			
			if (PuzzlePosition.canMoveDirection(xy, direction)) {
				// 交换两个小图块的图片
				Resources res=getResources();
				int img = res.getIdentifier("part_0" + v.getId(), "drawable", 
						getPackageName());
				int sbtnId = res.getIdentifier(
						"imageButton"+PuzzlePosition.getIndex(xy, direction), 
						"id", getPackageName());
				ImageButton sbtn = (ImageButton) findViewById(
						imbtns[PuzzlePosition.getIndex(xy, direction)-1]);
				
				Log.i("Puzzle", "imageButton"+PuzzlePosition.getIndex(xy, direction) + 
						", sbtn: " + sbtnId + ", ib12: " + R.id.imageButton12 + ", " + 
						sbtn);
//				if (sbtn != null) {
//				}
//				sbtn.setImageResource(img);
				// 交换位置信息
			}
			
			break;
		}
		
		return false;
	}
    
}
