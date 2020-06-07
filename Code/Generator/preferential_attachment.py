# Extension of preferential attachment to hypergraph
# Extension idea: when a new node is introduced, it forms simplex rather than pair-wise links as in the original BA (preferential attachment) model
# Hyper-graph "flavor": the new node chooses other (simplex_size - 1) nodes by choosing a group of size (simplex_size-1) based on the "degrees" of groups
# Some randomness is allowed: when forming each new simplex with a new node: with some probability p, other nodes are chosen randomly; with chance (1-p), other nodes are chosen according to what stated above.

### Hyper-graph generator based on preferential attachment:

import numpy as np
import random
import math
#from scipy.stats import pareto, lognorm, levy, weibull_max, weibull_min, burr, loggamma, gamma
from itertools import combinations

### IMPORTANT
# Nodes are numbers from 1,2...
# Indices are from 0,1,....
class preferential_attachment:

    # a simple graph generator object of preferential attachment law
    def __init__(self, name, k = 1, randomness = 0, version = 'non-correlated', num_nodes = 1000, type = 'non-deterministic'):
        self.name = name                                        # a string, name of our hyper-graph, for now it must be name of 1 of 16 available hypergraph datasets
        self.k = k
        self.randomness = randomness                            # level of randomness, between 0 and 1
        self.current_num_nodes = 0                              # current number of nodes present in the hypergraph
        self.num_nodes = num_nodes                              # total number of nodes
        self.file_name = ""                                     # name of the text file to store the hyper-graph
        self.size = 0                                           # current number of nodes in our hyper-graph
        self.degrees = [0] * num_nodes                         # store the degrees of the nodes in our hyper-graph
                                                                # "degree" of a node is defined to be the number of simplicies involving that node
        self.deg_list = [dict() for x in range(24)]             # store degrees of simplices of size 2-25, deg_list[i] stores "degrees" of simplices of size (i+2)
                                                                # store degrees of 2-simplex (edge) in deg_list[0], key is "a,b" for an edge connecting node a and b
                                                                # store degrees of 3-simplex (triangle) in deg_list[1]
                                                                # ...
                                                                # after forming a simplex of size(n), need to update deg_list[i] for i = 0,1,...(n-2)
        self.version = version                                  # either 'correlated' or 'preferential'
                                                                # 'correlated': if 1 new node forms 2 new simplices, they should be correlated (having intersection at least 1 element)
                                                                # 'preferential': only care about degree of the groups, not account for correlation

        self.type = type

    # Compute the probability of being chosen based on the degree distribution
    # Sometimes, we use self.degrees, sometimes we use another one
    def compute_probability(self, degrees):
        # If all entries are zeros (from the beginning)
        if np.count_nonzero(degrees) == 0:
            chosen_probability = [1/len(degrees)] * len(degrees)
        # If some entries are NOT zeros
        else:
            chosen_probability = degrees / np.sum(degrees)          # normalize "degrees" so that sum of all entries is equal to 1
        return chosen_probability

    # Pick a node based on the probability distribution of being chosen
    def pick_node(self, chosen_probability):
        a = np.random.choice(self.num_nodes, p = chosen_probability)
        return (a+1)

    # Pick several nodes based on the probability distribution of being chosen
    def pick_nodes(self, chosen_probability, number):
        chosen_indices = np.random.choice(a=range(self.size), size=number, replace=False, p = chosen_probability)
        chosen_nodes = chosen_indices + 1
        return chosen_nodes

    # learn the distribution of size of simplices
    # bear resemblance to the size distribution of an existing hypergraph dataset, which is specified by self.name
    # note that index of output 'size_distribution' starts from 0, but size of simplices must start from 1
    def learn_size_distribution(self):
        # Learn the size distribution
        ## For now, we only learn the simplex size distribution based on the size distribution of a real-world hypergraph dataset
        g = open("size distribution/no repetitions/" + self.name + " size distribution.txt")
        gl = g.readlines()
        size_distribution = []
        for line in gl:
            string = line.splitlines()
            string = string[0].split()
            size_distribution.append(int(string[0]))
        size_distribution = size_distribution / np.sum(size_distribution)
        return size_distribution

    # learn the distribution of number of simplices that each new node will bring

    def learn_number_simplices_per_node(self):
        g = open("simplex per node/" + self.name + "-simplices-per-node-distribution.txt", "r")
        count = -1

        for line in g:
            count += 1
        distribution = [0] * (count + 1)
        g.close()

        g = open("simplex per node/" + self.name + "-simplices-per-node-distribution.txt", "r")

        index = 0
        for line in g:
            distribution[index] = int(line)
            index += 1

        g.close()

        distribution = distribution/np.sum(distribution)

        return count, distribution



    ### generate the hypergraph
    def generate(self, file_name):
        ### SIMPLE Graph generator, having several constraints, you can add more if necessary

        # The number of nodes must be at least 200 (this is the smallest number of nodes in a real-world hyper-graph dataset)
        if self.num_nodes < 200:
            print("Error: number of nodes is too small")
            return (-1)

        # output file to write the generated hypergraph
        self.file_name = file_name
        #f = open("hyper_PA " + str(self.randomness) + " " + file_name + " " + str(self.k) + ".txt", "w")
        f = open("PA/" + file_name + ".txt", "w")

        # learn size distribution
        size_distribution = self.learn_size_distribution()

        # learn distirbution of number of simplicer per new node
        maximum_number, distribution = self.learn_number_simplices_per_node()

        # Initialize a sub-hypergraph, as 12 pair-wise edges of the first 24 nodes, each node has degree 1
        self.size = 24
        self.current_num_nodes = 24

        # update the degree of the nodes
        for i in range(24):
            self.degrees[i] = 1
        # write into file and update "degree" of edge
        for i in range(12):
            f.write(str(i + 1) + " " + str(24-i) + "\n")
            self.deg_list[0][str(i+1) + "," + str(24-i)] = 1
        # update degree in deg_list
        # From node 26-th onward, we will generate simplices based on preferential attachment
        ### Iterate through node 26,27,...



        for node in range(25, self.num_nodes+1):
            hyper_edges = []

            if self.type == 'deterministic':
                number_simplices = self.k

            else:
                sampled_number = np.random.choice(a = maximum_number + 1, size = 1, replace = False, p = distribution)
                number_simplices = sampled_number[0]

            for simplex in range(number_simplices):
                chosen_size = np.random.choice(a = 25, size = 1, replace = False, p = size_distribution)
                simplex_size = chosen_size[0] + 1

                degree = self.degrees[:self.size]
                probability = self.compute_probability(degree)
                #probability = probability[:self.size]

                chosen_nodes = np.random.choice(a=self.size, size=simplex_size, replace=False, p=probability)
                # the hyper_edge is our newly formed hyperedge, consisting of the new node and another node chosen randomly based on its degree
                hyper_edge = chosen_nodes
                hyper_edge = np.append(hyper_edge, node - 1)
                hyper_edge.sort()
                # Increment the degree of each node in our new simplex by 1 unit
                for t in hyper_edge:  # in here, t is the corresponding index of the node 't+1'
                    # self.degrees[t] += 1  # so when using index, use 't'
                    # write this newly formed hyper-edge into our file
                    f.write(str(t + 1) + " ")  # but when writing into file, must write 't+1'
                f.write("\n")
                hyper_edge = np.array(hyper_edge) + 1

                for node in hyper_edge:
                    self.degrees[node-1] += 1

            self.size += 1

        f.close()

def main():
    # for now, this log-normal distribution mean 0.5, variance 1 seems fine
    # Generating hyper-graphs with the same number of nodes and edges with the 16 realworld hypergraph datasets

    directory = ["coauth-DBLP", "coauth-MAG-Geology", "coauth-MAG-history", "congress-bills", "contact-high-school", "contact-primary-school", "DAWN", "email-Enron", "email-Eu", "NDC-classes", "NDC-substances", "tags-ask-ubuntu", "tags-math", "tags-stack-overflow", "threads-math", "threads-stack-overflow"]
    number_nodes = [1924991, 1256385, 1014734, 1718, 327, 242, 2558, 148, 1005,  1161, 5311, 3029, 1629, 49998, 176445, 2675955]
    number_simplices = [3700067, 1590335, 1812511, 260851, 172035, 106879, 2272433, 10883, 234760, 49724, 112405, 271233, 822059, 14458875, 719792, 11305343]

    randomness = [0.1, 0.5, 0.9]


    #k_range = [50, 105, 111, 3, 4]
    k_range = [4]
    considered_range = [8, 11, 12]

    index = 0
    for i in considered_range:
        generator = preferential_attachment(directory[i],  randomness = 0, version = 'not correlated',  num_nodes = number_nodes[i])
        generator.generate(directory[i])
        print("done with " + directory[i])
        #index += 1




if __name__ == '__main__':
    main()