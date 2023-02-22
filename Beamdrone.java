import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Beamdrone {

	public static void main(String[] args) {
		try (BufferedReader br = new BufferedReader(new FileReader("beamdrone.in"));
				FileWriter fw = new FileWriter("beamdrone.out")) {

			String line = br.readLine();
			String[] tokens = line.split("\\s");
			int n = Integer.parseInt(tokens[0]);
			int m = Integer.parseInt(tokens[1]);

			line = br.readLine();
			tokens = line.split("\\s");

			int xi = Integer.parseInt(tokens[0]);
			int yi = Integer.parseInt(tokens[1]);
			int xf = Integer.parseInt(tokens[2]);
			int yf = Integer.parseInt(tokens[3]);
			char[][] matrix = new char[n][m];
			int[][] distanceMatrix = new int[n][m];

			for (int i = 0; i < n; i++) {
				line = br.readLine();

				for (int j = 0; j < m; j++) {
					matrix[i][j] = line.charAt(j);
					distanceMatrix[i][j] = Integer.MAX_VALUE;
				}
			}

			PriorityQueue<Celula> pq = new PriorityQueue<>(
					Comparator.comparingInt(x -> x.distance));

			pq.offer(new Celula(xi, yi, 0, Celula.START));
			int distance = 0;
			distanceMatrix[xi][yi] = 0;

			while (!pq.isEmpty()) {
				Celula celula = pq.poll();

				if (celula.distance > distanceMatrix[celula.x][celula.y]) {
					continue;
				}

				if (celula.x == xf && celula.y == yf) {
					distance = celula.distance;
					break;
				}

				// Verificam daca putem sa mergem in sus.
				if (celula.x > 0) {
					if (matrix[celula.x - 1][celula.y] != 'W') {

						if (celula.directieIntrare == Celula.UP
								|| celula.directieIntrare == Celula.START) {

							distance = celula.distance;
						} else {
							distance = celula.distance + 1;
						}

						if (distance <= distanceMatrix[celula.x - 1][celula.y]) {
							pq.offer(new Celula(celula.x - 1, celula.y,
									distance, Celula.UP));
							distanceMatrix[celula.x - 1][celula.y] = distance;
						}
					}
				}

				// Verificam daca putem merge in jos.
				if (celula.x < n - 1) {
					if (matrix[celula.x + 1][celula.y] != 'W') {

						if (celula.directieIntrare == Celula.DOWN
								|| celula.directieIntrare == Celula.START) {

							distance = celula.distance;
						} else {
							distance = celula.distance + 1;
						}

						if (distance <= distanceMatrix[celula.x + 1][celula.y]) {
							pq.offer(new Celula(celula.x + 1, celula.y,
									distance, Celula.DOWN));
							distanceMatrix[celula.x + 1][celula.y] = distance;
						}
					}
				}

				// Verificam daca putem merge la stanga.
				if (celula.y > 0) {
					if (matrix[celula.x][celula.y - 1] != 'W') {

						if (celula.directieIntrare == Celula.LEFT
								|| celula.directieIntrare == Celula.START) {

							distance = celula.distance;
						} else {
							distance = celula.distance + 1;
						}

						if (distance <= distanceMatrix[celula.x][celula.y - 1]) {
							pq.offer(new Celula(celula.x, celula.y - 1,
									distance, Celula.LEFT));
							distanceMatrix[celula.x][celula.y - 1] = distance;
						}
					}
				}

				// Verificam daca putem merge la dreapta.
				if (celula.y < m - 1) {
					if (matrix[celula.x][celula.y + 1] != 'W') {

						if (celula.directieIntrare == Celula.RIGHT
								|| celula.directieIntrare == Celula.START) {

							distance = celula.distance;
						} else {
							distance = celula.distance + 1;
						}

						if (distance <= distanceMatrix[celula.x][celula.y + 1]) {
							pq.offer(new Celula(celula.x, celula.y + 1,
									distance, Celula.RIGHT));
							distanceMatrix[celula.x][celula.y + 1] = distance;
						}
					}
				}
			}

			System.out.println(distance);
			fw.write(distance + "\n");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
