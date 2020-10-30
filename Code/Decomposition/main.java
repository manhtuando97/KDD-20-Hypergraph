import java.io.*;

import java.util.*;
import java.lang.*;

public class Main {

    public static String[] simplexToEdgeLevel(int[] simplex){
        int simplex_size = simplex.length;
        String[] EdgelevelNodes = new String[simplex_size * (simplex_size-1) / 2];
        int i, j, count;
        count = 0;
        for (i = 0; i <= simplex_size-2; i++){
            for (j = i + 1; j <= simplex_size-1; j++){
                String edgelevel_node = String.valueOf(simplex[i]) + "," + String.valueOf(simplex[j]);
                EdgelevelNodes[count] = edgelevel_node;
                count++;
            }
        }
        return EdgelevelNodes;
    }

    public static String[] simplexStringToEdgeLevel(String[] simplex){
        int simplex_size = simplex.length;
        String[] EdgelevelNodes = new String[simplex_size * (simplex_size-1) / 2];
        int i, j, count;
        count = 0;
        for (i = 0; i <= simplex_size-2; i++){
            for (j = i + 1; j <= simplex_size-1; j++){
                String edgelevel_node = String.valueOf(simplex[i]) + "," + String.valueOf(simplex[j]);
                EdgelevelNodes[count] = edgelevel_node;
                count++;
            }
        }
        return EdgelevelNodes;
    }

    public static String[] simplexToTriangleLevel(int[] simplex){
        int simplex_size = simplex.length;
        String[] TriangleLevelNodes = new String[simplex_size * (simplex_size - 1) * (simplex_size - 2) / 6];
        int i,j,k, count;
        count = 0;
        for (i= 0; i <= simplex_size - 3; i++){
            for (j = i+1; j <= simplex_size - 2; j ++){
                for (k = j + 1; k <= simplex_size - 1; k++){
                    String triangleLevelNode = String.valueOf(simplex[i]) + "," + String.valueOf(simplex[j]) + "," + String.valueOf(simplex[k]);
                    TriangleLevelNodes[count] = triangleLevelNode;
                    count++;
                }
            }
        }
        return TriangleLevelNodes;
    }

    public static String[] simplexStringToTriangleLevel(String[] simplex){
        int simplex_size = simplex.length;
        String[] TriangleLevelNodes = new String[simplex_size * (simplex_size - 1) * (simplex_size - 2) / 6];
        int i,j,k, count;
        count = 0;
        for (i= 0; i <= simplex_size - 3; i++){
            for (j = i+1; j <= simplex_size - 2; j ++){
                for (k = j + 1; k <= simplex_size - 1; k++){
                    String triangleLevelNode = String.valueOf(simplex[i]) + "," + String.valueOf(simplex[j]) + "," + String.valueOf(simplex[k]);
                    TriangleLevelNodes[count] = triangleLevelNode;
                    count++;
                }
            }
        }
        return TriangleLevelNodes;
    }


    public static String[] simplexToFcliquelevel(int [] simplex){
        int simplex_size = simplex.length;
        String[] FcliqueLevelNodes = new String[simplex_size * (simplex_size - 1) * (simplex_size - 2) * (simplex_size - 3)/ 24];
        int i,j,k,t, count;
        count = 0;
        for (i= 0; i <= simplex_size - 4; i++){
            for (j = i+1; j <= simplex_size - 3; j ++){
                for (k = j + 1; k <= simplex_size - 2; k++){
                    for ( t = k + 1; t <= simplex_size - 1; t++){
                        String fcliqueLevelNode = String.valueOf(simplex[i]) + "," + String.valueOf(simplex[j]) + "," + String.valueOf(simplex[k] + "," + String.valueOf(simplex[t]));
                        FcliqueLevelNodes[count] = fcliqueLevelNode;
                        count++;
                    }

                }
            }
        }
        return FcliqueLevelNodes;
    }

    public static String[] simplexStringToFcliquelevel(String[] simplex){
        int simplex_size = simplex.length;
        String[] FcliqueLevelNodes = new String[simplex_size * (simplex_size - 1) * (simplex_size - 2) * (simplex_size - 3)/ 24];
        int i,j,k,t, count;
        count = 0;
        for (i= 0; i <= simplex_size - 4; i++){
            for (j = i+1; j <= simplex_size - 3; j ++){
                for (k = j + 1; k <= simplex_size - 2; k++){
                    for ( t = k + 1; t <= simplex_size - 1; t++){
                        String fcliqueLevelNode = String.valueOf(simplex[i]) + "," + String.valueOf(simplex[j]) + "," + String.valueOf(simplex[k] + "," + String.valueOf(simplex[t]));
                        FcliqueLevelNodes[count] = fcliqueLevelNode;
                        count++;
                    }

                }
            }
        }
        return FcliqueLevelNodes;
    }

    public static void main(String[] args) throws IOException {


      
        // Edge-level and Triangle-level projections
        for (int i = 0; i <= 0; i ++) {

            String input_directory = args[0];
            String output_directory = args[1];

            Hashtable<String, Integer> edgeLevelLabel = new Hashtable();
            Hashtable<String, Integer> triangleLevelLabel = new Hashtable();
            Hashtable<String, Integer> fcliqueLevelLabel = new Hashtable();

            // Open the files
            File file_hyperedges = new File(  input_directory + ".txt");

            File edge_level_labels = new File (  output_directory + "-edge_level-node-labels.txt");
            File edge_level_decomposed = new File(  output_directory + "-edge_level-decomposed.txt");

            File triangle_level_labels = new File(  output_directory + "-triangle_level-node-labels.txt");
            File triangle_level_decomposed = new File(   output_directory +  "-triangle_level-decomposed.txt" );

            File fclique_level_labels = new File(  output_directory + "-4clique_level-node-labels.txt");
            File fclique_level_decomposed = new File(   output_directory +  "-4clique_level-decomposed.txt" );

            // Define buffers
            BufferedReader br = new BufferedReader(new FileReader(file_hyperedges));

            BufferedWriter sim = new BufferedWriter(new FileWriter(edge_level_decomposed));
            BufferedWriter label = new BufferedWriter(new FileWriter(edge_level_labels));

            BufferedWriter sim_t = new BufferedWriter(new FileWriter(triangle_level_decomposed));
            BufferedWriter label_t = new BufferedWriter(new FileWriter(triangle_level_labels));

            BufferedWriter sim_tt = new BufferedWriter(new FileWriter(fclique_level_decomposed));
            BufferedWriter label_tt = new BufferedWriter(new FileWriter(fclique_level_labels));

            String br_line;

            while ((br_line = br.readLine()) != null) {

                String[] hyper_edge = br_line.split(" ");
                // suitable for edge-level projection

                if (hyper_edge.length >= 2 && hyper_edge.length <= 7){
                    String[] edgeLevelNodes = simplexStringToEdgeLevel(hyper_edge);
                    for (int it = 0; it <= edgeLevelNodes.length - 1; it++ ){
                        int write_value;
                        if (edgeLevelLabel.get(edgeLevelNodes[it]) == null) {
                            write_value = edgeLevelLabel.size() + 1;
                            edgeLevelLabel.put(edgeLevelNodes[it], edgeLevelLabel.size() + 1);
                            label.write(String.valueOf(write_value));
                            label.write(" " + edgeLevelNodes[it] + "\n");
                        }
                        else{
                            write_value = edgeLevelLabel.get(edgeLevelNodes[it]);
                        }
                        sim.write(String.valueOf(write_value));
                        sim.write(" ");
                    }
                    sim.write("\n");
                }

                if (hyper_edge.length >= 3 && hyper_edge.length <= 7){
                    String[] triangleLevelNodes = simplexStringToTriangleLevel(hyper_edge);
                    for (int it = 0; it <= triangleLevelNodes.length - 1; it++ ){
                        int write_value;
                        if (triangleLevelLabel.get(triangleLevelNodes[it]) == null) {
                            write_value = triangleLevelLabel.size() + 1;
                            triangleLevelLabel.put(triangleLevelNodes[it], triangleLevelLabel.size() + 1);
                            label_t.write(String.valueOf(write_value));
                            label_t.write(" " + triangleLevelNodes[it] + "\n");
                        }
                        else{
                            write_value = triangleLevelLabel.get(triangleLevelNodes[it]);
                        }
                        sim_t.write(String.valueOf(write_value));
                        sim_t.write(" ");
                    }
                    sim_t.write("\n");
                }


                if (hyper_edge.length >= 4 && hyper_edge.length <= 7){
                    String[] fcliqueLevelNodes = simplexStringToFcliquelevel(hyper_edge);
                    for (int it = 0; it <= fcliqueLevelNodes.length - 1; it++ ){
                        int write_value;
                        if (fcliqueLevelLabel.get(fcliqueLevelNodes[it]) == null) {
                            write_value = fcliqueLevelLabel.size() + 1;
                            fcliqueLevelLabel.put(fcliqueLevelNodes[it], fcliqueLevelLabel.size() + 1);
                            label_tt.write(String.valueOf(write_value));
                            label_tt.write(" " + fcliqueLevelNodes[it] + "\n");
                        }
                        else{
                            write_value = fcliqueLevelLabel.get(fcliqueLevelNodes[it]);
                        }
                        sim_tt.write(String.valueOf(write_value));
                        sim_tt.write(" ");
                    }
                    sim_tt.write("\n");
                }
            }
            sim.close();
            label.close();
            br.close();
            sim_t.close();
            label_t.close();

            sim_tt.close();
            label_tt.close();


            String complete_statement = "done with " + input_directory;
            System.out.println(complete_statement);
        }
    }

}
