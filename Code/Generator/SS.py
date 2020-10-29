import numpy as np
import random
import argparse
import math
# from scipy.stats import pareto, lognorm, levy, weibull_max, weibull_min, burr, loggamma, gamma
from itertools import combinations


class SS():

    def __init__(self, args):
        self.name = args.name  # name of the hypergraph
        self.type = args.type  # type of choosing recency sets
        # 'all' : choose among all sets, weight based on recency
        # 'random': choose among all sets, randomly
        # 'specified': user chooses the number of the most recent sets to choose from, among them 1 set is chosen based on recency
        # 'specified random': user chooses the number of the most recent sets to choose from, among them 1 set is chosen randomly
        self.num_recency = args.num_recency  # number of the most recent sets to choose from, only applicable when type = 'specified' or 'specified random'
        self.p = args.p  # correlation probability
        self.k = args.k  # number of simplices formed by 1 new node
        self.num_nodes = args.num_nodes  # number of nodes
        self.size = 0  # number of simplices in the "self.sequence"
        self.sequence = []  # sequence of sets

        self.size_distribution_directory = args.size_distribution_directory  # directory containing the size distribution file

        self.simplex_per_node_directory = args.simplex_per_node_directory  # directory containing the distribution of hyperedges per new node

        self.file_name = args.file_name  # name of the output file
        self.output_directory = args.output_directory  # directory containing the output file


    def recency_weights(self, k):  # compute recency weights of the sets
        set_index = range(1, k + 1)
        return set_index / np.sum(set_index)

    def learn_size_distribution(self):  # learn the distribution of simplex sizes
        g = open(self.size_distribution_directory + "/" + self.name + " size distribution.txt")
        gl = g.readlines()
        size_distribution = []
        for line in gl:
            string = line.splitlines()
            string = string[0].split()
            size_distribution.append(int(string[0]))
        size_distribution = size_distribution / np.sum(size_distribution)
        return size_distribution

    def learn_number_simplices_per_node(self):
        g = open(self.simplex_per_node_directory + "/" + self.name + "-simplices-per-node-distribution.txt", "r")
        count = -1

        for line in g:
            count += 1
        distribution = [0] * (count + 1)
        g.close()

        g = open(self.simplex_per_node_directory + "/" + self.name + "-simplices-per-node-distribution.txt", "r")

        index = 0
        for line in g:
            distribution[index] = int(line)
            index += 1

        g.close()

        distribution = distribution / np.sum(distribution)

        return count, distribution

    def generate(self):
        CRU_file = self.output_directory + '/' + self.file_name + ".txt"
        f = open(CRU_file, "w")

        # learn the simplex size distribution
        size_distribution = self.learn_size_distribution()

        # learn distirbution of number of simplicer per new node
        maximum_number, distribution = self.learn_number_simplices_per_node()

        # initialize with 48 nodes
        self.sequence.append(range(1, 25))
        self.size += 1
        self.sequence.append(range(25, 49))
        self.size += 1

        for i in range(1, 25):
            f.write(str(i))
            f.write(" ")
        f.write("\n")

        for i in range(25, 49):
            f.write(str(i))
            f.write(" ")
        f.write("\n")

        # keep introducing new nodes
        for node in range(49, self.num_nodes + 1):

            # sample a number of new simplices formed by this new node
            sampled_number = np.random.choice(a=maximum_number + 1, size=1, replace=False, p=distribution)
            number_simplices = sampled_number[0]

            if self.type == 'all':  # sample a set, based on recency weights
                for simplex in range(number_simplices):

                    # determine the size of the simplex
                    simplex_size = np.random.choice(a=25, size=1, replace=False, p=size_distribution)
                    size = simplex_size[0] + 1

                    # recency weights, more weight for recent sets
                    recency = self.recency_weights(self.size)

                    R = []
                    R.append(node)
                    while (len(R) < size):
                        T = []
                        chosen_set = np.random.choice(a=self.size, size=1, replace=False, p=recency)
                        sampled_set = self.sequence[chosen_set[0]]

                        for i in range(len(sampled_set)):
                            chance = random.random()
                            if chance < self.p:
                                T.append(sampled_set[i])

                        if len(T) < size - len(R):
                            R = np.union1d(R, T)
                        else:
                            T_ = np.random.choice(a=T, size=size - len(R), replace=False)
                            R = np.union1d(R, T_)

                    self.sequence.append(R)
                    for i in range(len(R)):
                        f.write(str(int(R[i])))
                        f.write(" ")
                    f.write("\n")
                    self.size += 1

            elif self.type == 'random':  # sample the set randomly
                for simplex in range(number_simplices):

                    # determine the size of the simplex
                    simplex_size = np.random.choice(a=25, size=1, replace=False, p=size_distribution)
                    size = simplex_size[0] + 1

                    R = []
                    R.append(node)
                    while (len(R) < size):
                        T = []
                        chosen_set = np.random.choice(a=self.size, size=1, replace=False)
                        sampled_set = self.sequence[chosen_set[0]]

                        for i in range(len(sampled_set)):
                            chance = random.random()
                            if chance < self.p:
                                T.append(sampled_set[i])

                        if len(T) < size - len(R):
                            R = np.union1d(R, T)
                        else:
                            T_ = np.random.choice(a=T, size=size - len(R), replace=False)
                            R = np.union1d(R, T_)

                    self.sequence.append(R)
                    for i in range(len(R)):
                        f.write(str(int(R[i])))
                        f.write(" ")
                    f.write("\n")
                    self.size += 1

            elif self.type == 'specified':  # sample from the most self.num_recency sets, based on weighted probability
                for simplex in range(number_simplices):

                    # determine the size of the simplex
                    simplex_size = np.random.choice(a=25, size=1, replace=False, p=size_distribution)
                    size = simplex_size[0] + 1

                    possible_range = min(self.size, self.num_recency)
                    recency = self.recency_weights(possible_range)

                    R = []
                    R.append(node)
                    while (len(R) < size):
                        T = []

                        chosen_set = np.random.choice(a=possible_range, size=1, replace=False,
                                                      p=recency)  # choose a number between 1 and self.num_recency, weighted probability
                        sampled_set = self.sequence[self.size - chosen_set[0] - 1]

                        for i in range(len(sampled_set)):
                            chance = random.random()
                            if chance < self.p:
                                T.append(sampled_set[i])

                        if len(T) < size - len(R):
                            R = np.union1d(R, T)
                        else:
                            T_ = np.random.choice(a=T, size=size - len(R), replace=False)
                            R = np.union1d(R, T_)

                    self.sequence.append(R)
                    for i in range(len(R)):
                        f.write(str(int(R[i])))
                        f.write(" ")
                    f.write("\n")
                    self.size += 1

            else:  # sample from the most self.num_recency recent sets, randomly
                for simplex in range(self.k):

                    # determine the size of the simplex
                    simplex_size = np.random.choice(a=25, size=1, replace=False, p=size_distribution)
                    size = simplex_size[0] + 1

                    # recency weights, more weight for recent sets
                    recency = self.recency_weights(self.size)

                    possible_range = min(self.size, self.num_recency)

                    R = []
                    R.append(node)
                    while (len(R) < size):
                        T = []

                        chosen_set = np.random.choice(a=possible_range, size=1,
                                                      replace=False)  # choose a number between 1 and self.num_recency, randomly
                        sampled_set = self.sequence[self.size - chosen_set[0] - 1]

                        for i in range(len(sampled_set)):
                            chance = random.random()
                            if chance < self.p:
                                T.append(sampled_set[i])

                        if len(T) < size - len(R):
                            R = np.union1d(R, T)
                        else:
                            T_ = np.random.choice(a=T, size=size - len(R), replace=False)
                            R = np.union1d(R, T_)

                    self.sequence.append(R)
                    for i in range(len(R)):
                        f.write(str(int(R[i])))
                        f.write(" ")
                    f.write("\n")
                    self.size += 1

def arg_parse():
    parser = argparse.ArgumentParser(description="SS arguments.")

    # file name
    parser.add_argument('--name', dest='name', help='name of the dataset')
    parser.add_argument('--file_name', dest='file_name', help='name of the output file')


    parser.add_argument('--num_nodes', dest='num_nodes', type=int, help='number of nodes in the hypergraphs')

    parser.add_argument('--type', dest='type', help='type of choosing recency sets')
    parser.add_argument('--num_recency', dest='num_recency', type=int, help="number of the most recent sets to choose from, only applicable when type = 'specified' or 'specified random'")


    parser.add_argument('--p', dest='p', type=float, help='correlation probability')
    parser.add_argument('--k', dest='k', type=int, help='k in the deterministic version')

    # regarding input, output files
    parser.add_argument('--size_distribution_directory', dest='size_distribution_directory', help='directory containing the size distribution file')
    parser.add_argument('--simplex_per_node_directory', dest='simplex_per_node_directory', help='directory containing the distribution of hyperedges per new node')
    parser.add_argument('--output_directory', dest='output_directory', help='directory containing the output file')

    parser.set_defaults(
                        type='random',
                        num_recency=0,
                        p=0.8,
                        k=3,
                        size_distribution_directory='size distribution',
                        simplex_per_node_directory='simplex per node',
                        output_directory='output'
                        )
    return parser.parse_args()

def main():
    # Generating hyper-graphs with the same number of nodes and edges with the 16 realworld hypergraph datasets

    directory = ["DAWN", "email-Eu", "tags-ask-ubuntu", "tags-math"]
    num_nodes = [2558, 1005, 3029, 1629]


    prog_args = arg_parse()

    # To run by command line
    generator = SS(prog_args)
    generator.generate()
    print("done with " + str(prog_args.name))


if __name__ == '__main__':
    main()

