public class Celula {
	int x, y;
	int distance;
	int directieIntrare;
	int visited;
	static final int UP = 0;
	static final int DOWN = 1;
	static final int LEFT = 2;
	static final int RIGHT = 3;
	static final int START = 4;

	public Celula(int x, int y, int distance, int directieIntrare) {
		this.x = x;
		this.y = y;
		this.distance = distance;
		this.directieIntrare = directieIntrare;
		visited = 0;
	}
}
