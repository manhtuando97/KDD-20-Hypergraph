package com.company;

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


    public static String[] simplexToTetragonlevel(int [] simplex){
        int simplex_size = simplex.length;
        String[] TetragonLevelNodes = new String[simplex_size * (simplex_size - 1) * (simplex_size - 2) * (simplex_size - 3)/ 24];
        int i,j,k,t, count;
        count = 0;
        for (i= 0; i <= simplex_size - 4; i++){
            for (j = i+1; j <= simplex_size - 3; j ++){
                for (k = j + 1; k <= simplex_size - 2; k++){
                    for ( t = k + 1; t <= simplex_size - 1; t++){
                        String tetragonLevelNode = String.valueOf(simplex[i]) + "," + String.valueOf(simplex[j]) + "," + String.valueOf(simplex[k] + "," + String.valueOf(simplex[t]));
                        TetragonLevelNodes[count] = tetragonLevelNode;
                        count++;
                    }

                }
            }
        }
        return TetragonLevelNodes;
    }

    public static String[] simplexStringToTetragonlevel(String[] simplex){
        int simplex_size = simplex.length;
        String[] TetragonLevelNodes = new String[simplex_size * (simplex_size - 1) * (simplex_size - 2) * (simplex_size - 3)/ 24];
        int i,j,k,t, count;
        count = 0;
        for (i= 0; i <= simplex_size - 4; i++){
            for (j = i+1; j <= simplex_size - 3; j ++){
                for (k = j + 1; k <= simplex_size - 2; k++){
                    for ( t = k + 1; t <= simplex_size - 1; t++){
                        String tetragonLevelNode = String.valueOf(simplex[i]) + "," + String.valueOf(simplex[j]) + "," + String.valueOf(simplex[k] + "," + String.valueOf(simplex[t]));
                        TetragonLevelNodes[count] = tetragonLevelNode;
                        count++;
                    }

                }
            }
        }
        return TetragonLevelNodes;
    }

    public static void main(String[] args) throws IOException {
	// write your code here
        // read the Generated datasets and project them onto edge-level projected and triangle-level projected graphs
        //String[] names = {"coauth-DBLP", "coauth-MAG-Geology", "coauth-MAG-History", "congress-bills", "contact-high-school", "contact-primary-school", "DAWN", "email-Enron", "email-Eu", "NDC-classes", "NDC-substances", "tags-ask-ubuntu", "tags-math", "tags-stack-overflow", "threads-math", "threads-stack-overflow"};

        //String[] names = {"hyper_PA 0 DAWN 55", "hyper_PA 0 NDC-classes 1", "hyper_PA 0 NDC-substances 2", "hyper_PA 0 tags-ask-ubuntu 50","hyper_PA 0 tags-math 105", "hyper_PA 0 threads-math 4", "hyper_PA 0 tags-stack-overflow 111"};
        //String[] names = {"DAWN", "email-Eu", "NDC-classes", "NDC-substances", "tags-ask-ubuntu", "tags-math", "threads-ask-ubuntu", "threads-math"};

        String[] names = {"DAWN", "email-Eu", "tags-ask-ubuntu", "tags-math"};

        String directory = "hyper PA non-deterministic/Iteration 2/";

        String[] probability = new String[3];
        probability[0] = "0.1";
        probability[1] = "0.5";
        probability[2] = "0.9";


        String[] types = new String[3];
        types[0] = "0.1";
        types[1] = "0.5";
        types[2] = "0.9";
        // Edge-level and Triangle-level projections
        for (int i = 1; i <= 1; i ++) {
            for (int j = 0; j <= 0; j++) {
                for (int k = 0; k <= 0; k++){
                    String file_name = names[i];
                    //String prob = probability[j];
                    //String type = types[k];

                    Hashtable<String, Integer> edgeLevelLabel = new Hashtable();
                    Hashtable<String, Integer> triangleLevelLabel = new Hashtable();
                    Hashtable<String, Integer> tetragonLevelLabel = new Hashtable();

                    // Open the files
                    //File file_simplex = new File("Generated data/seq of sets/CRU " + file_name + " 3 " + prob + " " + type +   " .txt");
                    //File file_simplex = new File("CRU " + file_name + " 3 " + prob + " " + type +   ".txt");
                    //File file_simplex = new File("hyper_PA " + probability[j] + " " +  file_name + " 3.txt");
                    //File file_simplex = new File(  "hyper PA non-deterministic/" + file_name + ".txt");
                    File file_simplex = new File(  directory + file_name + ".txt");


                    //File edge_level_simplices = new File( "Generated data/seq of sets/edge/CRU " + file_name + " " + prob + " " + type +" -edge_level-dictated-simplices (2-7).txt");
                    //File edge_level_labels = new File (  "Generated data/seq of sets/edge/CRU " + file_name + " " + prob + " " + type +" -edge_level-node-labels (2-7).txt");

                    //File edge_level_simplices = new File( "CRU " + file_name + " 3 " + prob + " " + type +" -edge_level-dictated-simplices (2-7).txt");
                    //File edge_level_labels = new File (  "hyper PA non-deterministic/" + file_name + "-edge_level-node-labels (2-7).txt");
                    //File edge_level_simplices = new File(  "hyper PA non-deterministic/" + file_name + "-edge_level-dictated-simplices (2-7).txt");
                    File edge_level_labels = new File (  directory + file_name + "-edge_level-node-labels (2-7).txt");
                    File edge_level_simplices = new File(  directory + file_name + "-edge_level-dictated-simplices (2-7).txt");

                    //File edge_level_simplices = new File("hyper_PA " + probability[j] + " " +  file_name + " -edge_level-dictated-simplices (2-7).txt");
                    //File edge_level_labels = new File ("hyper_PA " + probability[j] + " " + file_name + " -edge_level-node-labels (2-7).txt" );
                    //File triangle_level_simplices = new File(  "Generated data/seq of sets/triangle/CRU " + file_name + " " + prob + " " + type +" -triangle_level-dictated-simplices (3-7).txt");
                    //File triangle_level_labels = new File(  "hyper PA non-deterministic/" + file_name + "-triangle_level-node-labels (3-7).txt");
                    //File triangle_level_simplices = new File(   "hyper PA non-deterministic/" + file_name +  "-triangle_level-dictated-simplices (3-7).txt" );
                    File triangle_level_labels = new File(  directory + file_name + "-triangle_level-node-labels (3-7).txt");
                    File triangle_level_simplices = new File(   directory + file_name +  "-triangle_level-dictated-simplices (3-7).txt" );

                    //File tetragon_level_labels = new File(  "hyper PA non-deterministic/" + file_name + "-tetragon_level-node-labels (4-7).txt");
                    //File tetragon_level_simplices = new File(   "hyper PA non-deterministic/" + file_name +  "-tetragon_level-dictated-simplices (4-7).txt" );
                    File tetragon_level_labels = new File(  directory + file_name + "-tetragon_level-node-labels (4-7).txt");
                    File tetragon_level_simplices = new File(   directory + file_name +  "-tetragon_level-dictated-simplices (4-7).txt" );


                    //File triangle_level_simplices = new File(  "CRU " + file_name + " 3 " + prob + " " + type +" -triangle_level-dictated-simplices (3-7).txt");
                    //File triangle_level_labels = new File( "CRU " + file_name + " 3 " + prob + " " + type +" -triangle_level-node-labels (3-7).txt");

                    //File triangle_level_simplices = new File("hyper_PA " + probability[j] + " " +  file_name + " -triangle_level-dictated-simplices (3-7).txt");
                    //File triangle_level_labels = new File("hyper_PA " + probability[j] + " " +  file_name + " -triangle_level-node-labels (2-7).txt");

                    //File simplices = new File("Generated data/" + type + "/Simplices/" + file_name + ".txt");
                    //File simplices_7 = new File("Generated data/" + type + "/Simplices/" + file_name + "(7).txt");
                    // Define buffers
                    BufferedReader br = new BufferedReader(new FileReader(file_simplex));

                    BufferedWriter sim = new BufferedWriter(new FileWriter(edge_level_simplices));
                    BufferedWriter label = new BufferedWriter(new FileWriter(edge_level_labels));

                    BufferedWriter sim_t = new BufferedWriter(new FileWriter(triangle_level_simplices));
                    BufferedWriter label_t = new BufferedWriter(new FileWriter(triangle_level_labels));

                    BufferedWriter sim_tt = new BufferedWriter(new FileWriter(tetragon_level_simplices));
                    BufferedWriter label_tt = new BufferedWriter(new FileWriter(tetragon_level_labels));

                    String br_line;

                    while ((br_line = br.readLine()) != null) {
                    /*

                    if (hyper_edge.length <= 7) {
                        for (int i = 0; i <= hyper_edge.length - 1; i++) {
                            sim.write(hyper_edge[i]);
                            sim.write(" ");
                        }
                        sim.write("\n");
                    }
                    */

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
                            String[] tetragonLevelNodes = simplexStringToTetragonlevel(hyper_edge);
                            for (int it = 0; it <= tetragonLevelNodes.length - 1; it++ ){
                                int write_value;
                                if (tetragonLevelLabel.get(tetragonLevelNodes[it]) == null) {
                                    write_value = tetragonLevelLabel.size() + 1;
                                    tetragonLevelLabel.put(tetragonLevelNodes[it], tetragonLevelLabel.size() + 1);
                                    label_tt.write(String.valueOf(write_value));
                                    label_tt.write(" " + tetragonLevelNodes[it] + "\n");
                                }
                                else{
                                    write_value = tetragonLevelLabel.get(tetragonLevelNodes[it]);
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


                    String complete_statement = "done with " + file_name;
                    System.out.println(complete_statement);
                }


                }

            }
        }
    }

