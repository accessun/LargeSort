Some circumstances may arise where one needs to sort a huge amount of data. Due to the limited resources of the machine that is assigned to carry out the sorting operation, sometimes, it is impractical to load the data all at once into the memory for sorting. Apparently, other solutions are required.

This project implements an algorithm named external sorting by which one can successfully cope with such situation. First, the program computes the hash value for each line of data and splits the data into multiple files whose size can well fit into the memory. Then, each file is loaded into the memory for sorting. Eventually, a merging operation is performed to put all the sorted data in each splited files together into one final sorted data set.
