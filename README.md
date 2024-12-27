This program allows users to plan travel routes that meet their needs. Specifically, users can load a travel graph, then request routes according to one of 3 priorities:
1. price, the route that costs the least money
2. Time, the route with the lowest travel time
3. Directness, the route with the fewest connections

Users can load graphs and request routes through text prompts in the terminal. The program computes routes, then prints them.

TO USE

1. Load data files using the following command:
load <path to cities file/file_name.csv> <path to transport file/file_name.csv>
2. Generate route using the prefeerred search method (fast, cheap, or direct):
<method> <origin city> <destination city>

Example use (Note that we have to put “New York City” in quotes since its name is more than one word):

>>> load data/cities1.csv data/transport1.csv
Successfully loaded cities and transportation files.
>>> fast "New York City" Providence
================================================================================
Origin: New York City
Destination: Providence
--------------------------------------------------------------------------------
 -- New York City -> Boston, Type: plane, Cost: $267.0, Duration: 50.0 minutes
 -- Boston -> Providence, Type: train, Cost: $13.0, Duration: 80.0 minutes
================================================================================
