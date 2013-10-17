package kevin.jiang.puzzle;

import java.util.ArrayList;
import java.util.Random;

import android.os.Bundle;
import android.app.Activity;
//import android.content.res.Resources;
import android.util.Log;
//import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
//import android.widget.LinearLayout;
import android.view.Window;

public class MainActivity extends Activity implements OnTouchListener {
	
	private float xDown, yDown;
	private boolean isShuffle = false;
	private boolean gameOver = false;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 隐藏title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        // 设置事件监听
        setEventListene();
        shuffle();
    }
    
    /**
     * 生成8个随机数
     * @return
     */
    private int[] generateRandomNumber() {
    	// 随机打乱
    	ArrayList<Integer> def = new ArrayList<Integer>();
    	for (int i=1; i<9; i++) {
    		def.add(new Integer(i));
    	}
    	int[] shu = new int[def.size()];
    	Random ran = new Random();
    	for (int j=0; j<8; j++) {
        	for (;;) {
        		if (def.size() == 1) {
        			shu[j] = def.get(0);
        			def.remove(0);
        			break;
        		}
        		int index = ran.nextInt(def.size());
        		if (def.get(index) == (j+1)) {
        			continue;
        		}
        		shu[j] = def.get(index);
        		def.remove(index);
        		break;
        	}
    	}
    	return shu;
    }
    
    public void shuffle() {
    	if (isShuffle) {
    		return;
    	}
    	int[] shu = generateRandomNumber();

    	// 显示序列数
    	String p = "";
    	for (int i=0; i<shu.length; i++) {
    		if (i == shu.length-1) {
    			p += shu[i];
    		} else {
    			p += shu[i] + ", ";
    		}
    	}
    	int inverseOrdinal;
    	for (;;) {
    		inverseOrdinal = 0;
        	// 计算逆序数
        	for (int i=0; i<shu.length; i++) {
        		for (int j=i+1; j<shu.length; j++) {
        			if (shu[i] > shu[j]) {
        				inverseOrdinal += 1;
        			}
        		}
        	}
        	if (inverseOrdinal % 2 == 0) {
        		break;
        	} else {
        		shu = generateRandomNumber();
        	}
    	}
    	TextView tv = (TextView) findViewById(R.id.textView2);
    	tv.setText(p + ", in: " + inverseOrdinal);

Log.i("Puzzle", "shuffle PuzzlePosition.position [before]: \n" + xxDebug(PuzzlePosition.position));
    	for (int i=0; i<8; i++) {
    		// 打乱 position
    		int[] ori = PuzzlePosition.getXY(i);

    		PuzzlePosition.position[ori[0]][ori[1]] = shu[i];
    		int[] xy = PuzzlePosition.getXY(shu[i] - 1);
    		PuzzlePosition.draw[ori[0]][ori[1]] = PuzzlePosition.drawDefalut[xy[0]][xy[1]];
    	}
    	for (int i=0; i<8; i++) {
    		int[] ori = PuzzlePosition.getXY(i);
    		ImageButton btn = (ImageButton) findViewById(PuzzlePosition.imbtns[ori[0]][ori[1]]);
    		btn.setImageResource(PuzzlePosition.draw[ori[0]][ori[1]]);
    	}
Log.i("Puzzle", "shuffle PuzzlePosition.position [after]: \n" + xxDebug(PuzzlePosition.position));
    	isShuffle = true;
    	
    }
    
    public void setEventListene() {
    	for (int i=0; i<3; i++) {
        	for (int j=0; j<3; j++) {
            	ImageButton btn = (ImageButton) findViewById(
            			PuzzlePosition.imbtns[i][j]);
            	btn.setTag(i*3 + j + 1);
            	btn.setOnTouchListener(this);
        	}
    	}
    	ImageButton btn12 = (ImageButton) findViewById(R.id.imageButton12);
    	btn12.setTag(12);
    	btn12.setOnTouchListener(this);
    	// 再来一局
		Button newGameBtn = (Button) findViewById(R.id.button1);
		newGameBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (gameOver) {
					shuffle();
					gameOver = false;
				}
			}
		});
    }

	@Override
	public boolean onTouch(View v, MotionEvent event) {
    	int btnWidth = v.getWidth();
    	int btnHeight = v.getHeight();
		int eventaction = event.getAction();
		
		switch (eventaction) {
		// 接触屏幕
		case MotionEvent.ACTION_DOWN:
			xDown = event.getRawX();
			yDown = event.getRawY();
			break;
		// 离开屏幕
		case MotionEvent.ACTION_UP:
			float horizontalMovement = event.getRawX() - xDown;
			float verticalMovement = event.getRawY() - yDown;
			int direction = 0;
//Log.i("Puzzle", "finger up, xDown: " + xDown + ", yDown: " + yDown
//		+ ", eventX: " + event.getX() + ", eventY: " + event.getY());
			if (horizontalMovement > 0 && horizontalMovement > btnWidth/3.5) {
				direction = PuzzlePosition.RIGHT;
				Log.i("Puzzle", "ACTION_UP: 向右移动");
			} else if (horizontalMovement < 0 && Math.abs(horizontalMovement) > btnWidth/2.0) {
				direction = PuzzlePosition.LEFT;
				Log.i("Puzzle", "ACTION_UP: 向左移动");
			} else if (verticalMovement > 0 && verticalMovement > btnHeight/3.5) {
				direction = PuzzlePosition.DOWN;
				Log.i("Puzzle", "ACTION_UP: 向下移动");
			} else if (verticalMovement < 0 && Math.abs(verticalMovement) > btnHeight/2.0) {
				direction = PuzzlePosition.UP;
				Log.i("Puzzle", "ACTION_UP: 向上移动");
			}
Log.i("Puzzle", "tag: " + ((Integer)v.getTag()).intValue());
//Log.i("Puzzle", "direction: " + direction);
			// 把index转换成x, y坐标
			int[] xy = PuzzlePosition.getXY(((Integer)v.getTag()).intValue() - 1);
			int targetIndex = PuzzlePosition.getTargetIndex(xy, direction);
			int[] targetXY = PuzzlePosition.getXY(targetIndex - 1);
			
Log.i("Puzzle", "canMove: "+PuzzlePosition.canMoveDirection(xy, direction));
Log.i("Puzzle", "targetIndex: "+targetIndex);
Log.i("Puzzle", "xy[0]: " + xy[0] + ", xy[1]: " + xy[1] 
		+ ", imbtns[x][y]: " + PuzzlePosition.imbtns[xy[0]][xy[1]]);
			
			if (PuzzlePosition.canMoveDirection(xy, direction)) {
Log.i("Puzzle", "targetXY[0]: " + targetXY[0] + ", targetXY[1]: " + targetXY[1] 
		+ ", imbtns[targetXY[0]][targetXY[1]]: " + PuzzlePosition.imbtns[targetXY[0]][targetXY[1]]);
				// 交换两个小图块的图片
				ImageButton targetBtn = (ImageButton) findViewById(
						PuzzlePosition.imbtns[targetXY[0]][targetXY[1]]);
				targetBtn.setImageResource(
						PuzzlePosition.draw[xy[0]][xy[1]]);
				
				ImageButton btn = (ImageButton) findViewById(
						PuzzlePosition.imbtns[xy[0]][xy[1]]);
				btn.setImageResource(
						PuzzlePosition.draw[targetXY[0]][targetXY[1]]);
				
Log.i("Puzzle", targetBtn + ", " + btn);
				
				// 交换位置信息
				int swap;
//Log.i("Puzzle", "btn ids [before]: \n" + xxDebug(PuzzlePosition.imbtns));
//				swap = PuzzlePosition.imbtns[xy[0]][xy[1]];
//				PuzzlePosition.imbtns[xy[0]][xy[1]] = PuzzlePosition.imbtns[targetXY[0]][targetXY[1]];
//				PuzzlePosition.imbtns[targetXY[0]][targetXY[1]] = swap;
//Log.i("Puzzle", "btn ids [after]: \n" + xxDebug(PuzzlePosition.imbtns));

Log.i("Puzzle", "draw [before]: \n" + xxDebug(PuzzlePosition.draw));
				swap = PuzzlePosition.draw[xy[0]][xy[1]];
				PuzzlePosition.draw[xy[0]][xy[1]] = 
						PuzzlePosition.draw[targetXY[0]][targetXY[1]];
				PuzzlePosition.draw[targetXY[0]][targetXY[1]] = swap;
Log.i("Puzzle", "draw [after]: \n" + xxDebug(PuzzlePosition.draw));

Log.i("Puzzle", "PuzzlePosition.position [before]: \n" + xxDebug(PuzzlePosition.position));
				swap = PuzzlePosition.position[xy[0]][xy[1]];
				PuzzlePosition.position[xy[0]][xy[1]] = PuzzlePosition.position[targetXY[0]][targetXY[1]];
				PuzzlePosition.position[targetXY[0]][targetXY[1]] = swap;
Log.i("Puzzle", "PuzzlePosition.position [after]: \n" + xxDebug(PuzzlePosition.position));
			}
			if (isWin()) {
				TextView tv = (TextView) findViewById(R.id.textView2);
				tv.setText("我回来啦，亲一个。");
				gameOver = true;
				isShuffle = false;
			}
			break;
		}
		
		return false;
	}
	
	public boolean isWin() {
		for (int i=0; i<3; i++) {
			for (int j=0; j<3; j++) {
				int pos = i*3 + j +1;
				if (PuzzlePosition.position[i][j] != pos) {
					return false;
				}
			}
		}
		return true;
	}

public String xxDebug(int[][] c) {
	String out = "";
	for (int i=0; i<4; i++) {
		for (int j=0; j<3; j++) {
			out += c[i][j] + ", ";
		}
		out += "\n";
	}
	return out;
}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		android.os.Process.killProcess(android.os.Process.myPid());
	}
}
