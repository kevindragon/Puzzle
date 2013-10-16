package kevin.jiang.puzzle;


/**
 * 小图块位置及其计算
 * @author Kevin Jiang kittymiky@gmail.com
 *
 */
public class PuzzlePosition {
	public final static int UP = 1;
	public final static int RIGHT = 2;
	public final static int DOWN = 3;
	public final static int LEFT = 4;

	public static int[][] imbtns = {
			{R.id.imageButton1, R.id.imageButton2, R.id.imageButton3}, 
    		{R.id.imageButton4, R.id.imageButton5, R.id.imageButton6}, 
    		{R.id.imageButton7, R.id.imageButton8, R.id.imageButton9}, 
    		{R.id.imageButton10, R.id.imageButton11, R.id.imageButton12}
    };
	
	/**
	 * 默认的小图块的位置
	 */
	public static int[][] position = {
			{1, 2, 3}, 
			{4, 5, 6}, 
			{7, 8, 9}, 
			{-1, -1, 0}};

	public static int[][] positionDefault = {
			{1, 2, 3}, 
			{4, 5, 6}, 
			{7, 8, 9}, 
			{-1, -1, 0}};

	public static int[][] draw = {
		{R.drawable.part_01, R.drawable.part_02, R.drawable.part_03},
		{R.drawable.part_04, R.drawable.part_05, R.drawable.part_06}, 
		{R.drawable.part_07, R.drawable.part_06, R.drawable.part_09}, 
		{android.R.drawable.screen_background_light_transparent, 
			android.R.drawable.screen_background_light_transparent, 
			android.R.drawable.screen_background_dark_transparent}, 
	};
	public static int[][] drawDefalut = {
		{R.drawable.part_01, R.drawable.part_02, R.drawable.part_03},
		{R.drawable.part_04, R.drawable.part_05, R.drawable.part_06}, 
		{R.drawable.part_07, R.drawable.part_08, R.drawable.part_09}, 
		{android.R.drawable.screen_background_light_transparent, 
			android.R.drawable.screen_background_light_transparent, 
			android.R.drawable.screen_background_dark_transparent}, 
	};
	
	/**
	 * 判断一个小图块是否可以朝指定方向移动
	 * @param pos
	 * @param o
	 * @return boolean
	 */
	public static boolean canMoveDirection(int[] pos, int o) {
		boolean ret = false;
		if (o == UP) {
			if (pos[0] - 1 < 0) ret = false;
			else ret = (position[pos[0] - 1][pos[1]]==0) ? true : false;
		} else if (o == RIGHT) {
			if (pos[1] + 1 > 2) ret = false;
			else ret = (position[pos[0]][pos[1]+1]==0) ? true : false;
		} else if (o == DOWN) {
			int tmp = (pos[0] + 1)*3 + pos[1] + 1;
			if (tmp > 9) {
				if (tmp == 10 || tmp == 11) ret = false;
				else if (tmp == 12) ret = (position[pos[0]+1][pos[1]] == 0) ? true : false;
			} else {
				ret = (position[pos[0]+1][pos[1]] == 0) ? true : false;
			}
		} else if (o == LEFT) {
			if (pos[1] - 1 < 0) ret = false;
			else ret = (position[pos[0]][pos[1]-1]==0) ? true : false;
		}

		return ret;
	}
	
	/**
	 * 要移动到的目标位置的索引
	 * @param pos
	 * @param o
	 * @return
	 */
	public static int getTargetIndex(int[] pos, int o) {
		int index = 0;
		if (o == UP) {
			index = (pos[0]-1)*3 + pos[1] + 1;
		} else if (o == RIGHT) {
			index = pos[0]*3 + pos[1] + 2;
		} else if (o == DOWN) {
			index = (pos[0]+1)*3 + pos[1] + 1;
		} else if (o == LEFT) {
			index = pos[0]*3 + pos[1];
		}
		
		return index;
	}
	
	public static int[] getXY(int index) {
		int x = index / 3;
		int y = index % 3;
		
		return new int[]{x, y};
	}
}
