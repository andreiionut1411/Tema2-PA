import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;
import java.util.PriorityQueue;

public class Fortificatii {

	public static int k;

	public static void main(String[] args) {
		try (BufferedReader br = new BufferedReader(new FileReader("fortificatii.in"));
				FileWriter fw = new FileWriter("fortificatii.out", false)) {
			String line = br.readLine();
			String[] tokens = line.split("\\s");
			int n = Integer.parseInt(tokens[0]);
			final int m = Integer.parseInt(tokens[1]);
			k = Integer.parseInt(tokens[2]);
			int[] barbari = new int[n + 1];
			long initialValue = Long.MAX_VALUE - Integer.MAX_VALUE;

			line = br.readLine();
			tokens = line.split("\\s");

			int b = Integer.parseInt(tokens[0]);
			ArrayList<ArrayList<Edge>> listaAdiacenta = new ArrayList<>();
			ArrayList<ArrayList<Edge>> listaAdiacentaBarbari = new ArrayList<>();

			// Initializam listele de adiacenta. Indexarea acestora va incepe de la 1.
			for (int i = 0; i <= n; i++) {
				listaAdiacenta.add(new ArrayList<>());
				listaAdiacentaBarbari.add(new ArrayList<>());
			}

			for (int i = 0; i < b; i++) {
				int barbar = Integer.parseInt(tokens[i + 1]);
				barbari[barbar] = 1;
			}

			for (int i = 0; i < m; i++) {
				int isBarbar = 0;
				line = br.readLine();
				tokens = line.split("\\s");
				int x = Integer.parseInt(tokens[0]);
				int y = Integer.parseInt(tokens[1]);
				int t = Integer.parseInt(tokens[2]);

				if (barbari[x] == 1 && barbari[y] == 0) {
					listaAdiacentaBarbari.get(x).add(new Edge(y, t));
					isBarbar = 1;
				} else if (barbari[x] == 0 && barbari[y] == 1) {
					listaAdiacentaBarbari.get(y).add(new Edge(x, t));
					isBarbar = 1;
				} else if (barbari[x] == 1 && barbari[y] == 1) {
					isBarbar = 1;
				}

				if (isBarbar == 0) {
					listaAdiacenta.get(x).add(new Edge(y, t));
					listaAdiacenta.get(y).add(new Edge(x, t));
				}
			}

			ArrayList<Long> d = new ArrayList<>();
			PriorityQueue<Integer> queue = new PriorityQueue<>(Comparator.comparingLong(d::get));
			int[] vizitat = new int[n + 1];
			queue.offer(1);

			// Initializam distantele
			for (int i = 0; i <= n; i++) {
				d.add(initialValue);
			}

			d.set(1, 0L);

			while (!queue.isEmpty()) {
				int node = queue.poll();
				if (vizitat[node] == 1) {
					continue;
				}

				vizitat[node] = 1;

				for (Edge neigh : listaAdiacenta.get(node)) {
					if (vizitat[neigh.node] == 0) {
						if (d.get(neigh.node) > d.get(node) + neigh.cost) {
							d.set(neigh.node, d.get(node) + neigh.cost);
						}

						queue.offer(neigh.node);
					}
				}
			}

			ArrayList<Long> drumuriBarbare = new ArrayList<>();
			int numarDrumuriBarbare = 0;

			for (int i = 1; i <= n; i++) {
				if (barbari[i] == 1) {
					for (Edge neigh : listaAdiacentaBarbari.get(i)) {
						if (d.get(neigh.node) != initialValue) {
							drumuriBarbare.add(d.get(neigh.node) + neigh.cost);
							numarDrumuriBarbare++;
						}
					}
				}
			}

			drumuriBarbare.sort(Comparator.comparingLong(x -> x));
			int indexCurent = 0;

			while (k > 0) {
				if (indexCurent < numarDrumuriBarbare - 1) {
					if (Objects.equals(drumuriBarbare.get(indexCurent),
							drumuriBarbare.get(indexCurent + 1))) {
						indexCurent++;
					} else {
						long diferenta = drumuriBarbare.get(indexCurent + 1)
								- drumuriBarbare.get(indexCurent);
						if (k >= diferenta * (indexCurent + 1)) {
							k -= diferenta * (indexCurent + 1);
							indexCurent++;
						} else {
							k = k / (indexCurent + 1);
							drumuriBarbare.set(indexCurent, drumuriBarbare.get(indexCurent) + k);
							k = 0;
						}
					}
				} else {
					k = k / numarDrumuriBarbare;
					drumuriBarbare.set(indexCurent, drumuriBarbare.get(indexCurent) + k);
					k = 0;
				}
			}

			fw.write(drumuriBarbare.get(indexCurent) + "\n");
		} catch (IOException e) {
			System.out.println("Nu exista fisierul");
			e.printStackTrace();
		}
	}
}
