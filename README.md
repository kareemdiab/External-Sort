# External-Sort

This project reads bytes from disk, heapifies them and sorts them using heapsort. Each piece of data contains a key and value and they are sorted by key. 
Since it would take too long to read from disk for every memory request I used a buffer pool with the Least Recently Used replacement 
policy and a dirty bit to specify that the buffer has changed and must be written back to disk. The max amount buffers in the buffer pool to be used 
is specified in the command line. A linked list with a max amount of nodes equal to the number of max buffers is used to represent which buffers are 
currently contained in the buffer pool. The buffer pool handles all memory requests and determines whether to read from disk or cache. The .bin file is 
specified on the command line as well. The number of caches hits/misses and runtime is outputted to a specified output file also given on the 
command line. 
Invocation: 
  %> java HeapSort {data-file-name} {num-buffers} {stat-file-name}
