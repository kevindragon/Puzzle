package kevin.jiang.puzzle;


/**
 * Сͼ��λ�ü������
 * @author Kevin Jiang kittymiky@gmail.com
 *
 */
public class PuzzlePosition {
	public final static int UP = 1;
	public final static int RIGHT = 2;
	public final static int DOWN = 3;
	public final static int LEFT = 4;
	
	/**
	 * Ĭ�ϵ�Сͼ���λ��
	 */
	public static int[][] position = {
			{1, 2, 3}, 
			{4, 5, 6}, 
			{7, 8, 9}, 
			{-1, -1, 0}};
	
	/**
	 * �ж�һ��Сͼ���Ƿ���Գ�ָ�������ƶ�
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
	 * Ҫ�ƶ�����Ŀ��λ�õ�����
	 * @param pos
	 * @param o
	 * @return
	 */
	public static int getIndex(int[] pos, int o) {
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
