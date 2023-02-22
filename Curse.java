import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class Curse {

	public static void main(String[] args) {
		try (BufferedReader br = new BufferedReader(new FileReader("curse.in"));
				FileWriter fw = new FileWriter("curse.out", false)) {

			String line = br.readLine();
			String[] tokens = line.split("\\s");
			int n = Integer.parseInt(tokens[0]);
			int m = Integer.parseInt(tokens[1]);
			int a = Integer.parseInt(tokens[2]);
			ArrayList<ArrayList<Integer>> adj = new ArrayList<>();
			ArrayList<ArrayList<Integer>> rezultate = new ArrayList<>();
			ArrayList<Integer> nrParinti = new ArrayList<>();
			int[] isNotCarSource = new int[m + 1];

			for (int i = 0; i <= m; i++) {
				adj.add(new ArrayList<>());
				nrParinti.add(0);
			}

			for (int i = 0; i < a; i++) {
				line = br.readLine();
				tokens = line.split("\\s");
				rezultate.add(new ArrayList<>());
				for (int j = 0; j < n; j++) {
					int x = Integer.parseInt(tokens[j]);
					rezultate.get(i).add(x);
				}
			}

			// Adaugam o muchie astfel incat sursa acesteia sa fie nodul care are prioritate mai
			// mare, iar destinatia cel cu prioritate mai mica. Pentru fiecare nod tinem
			// numarul de muchii incidente.
			for (int i = 1; i < a; i++) {
				int index = 0;
				while (Objects.equals(rezultate.get(i - 1).get(index), rezultate.get(i).get(index))
						&& index < n) {
					index++;
				}

				int u = rezultate.get(i - 1).get(index);
				int v = rezultate.get(i).get(index);
				isNotCarSource[v] = 1;
				adj.get(u).add(v);
				int parinti = nrParinti.get(v) + 1;
				nrParinti.set(v, parinti);
			}

			// Cautam sursa din care sa incepem dfs-ul.
			int source = -1;
			for (int i = 1; i <= m; i++) {
				if (isNotCarSource[i] == 0) {
					source = i;
					break;
				}
			}

			int[] distances = new int[m + 1];
			distances[0] = source;
			int node = source;
			int depth = 1;

			// Pana nu ajungem la nodul cu prioritatea cea mai mica, ne plimbam
			// prin toti vecinii nodului si le scadem cu 1 numarul de muchii incidente,
			// iar nodul care a ramas cu 0 inseamna ca el este urmatorul nod ca prioritate.
			while (node != -1) {
				int nextNode = -1;
				for (Integer neigh : adj.get(node)) {
					int parinti = nrParinti.get(neigh) - 1;
					nrParinti.set(neigh, parinti);

					if (parinti == 0) {
						distances[depth++] = neigh;
						nextNode = neigh;
					}
				}

				node = nextNode;
			}

			for (int i = 0; i < m; i++) {
				fw.write(distances[i] + " ");
			}
			fw.write("\n");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
