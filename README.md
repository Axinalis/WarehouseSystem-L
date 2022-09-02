# Store_name
This project represents store chain with high load of information.
The **store** container represents some number of stores, that send information about quantity of goods in them to the warehouse and request new batches of goods. To reduce amount of time that needed to emit data, this service is not using any database.
The **warehouse** container represents warehouse. It has database **database** container. 
The **zookeeper** and **kafka** containers provide kafka for systems to communicate.

The warehouse consumes data and decide whether it need to refill stock in different stores or send request to refill self stock. As warehouse deals with database, it spends more time on every request and is needed to be written with reactive programming. 

To make system more loaded, we assume, that one day ends in 2 minutes, one store sends statistics about their goods every second (and if there is 2 stores, data will be emitted every half second and so on).
To refill store stock, we need 20 seconds. To refill stock in warehouse, we need 40 seconds.
All those values could be changed via config.