# ATD-TA
## Order Table
The Order Table is structured using a linked list of linked lists. This is to make adding orders at new prices easy, and for easily retrieving particular orders at particular prices (existing orders are retrieved for modification and deletion via their ID). 

## Matching Engine
The Matching Engine makes use of the delete and modify methods present in the orderbook, in order to fulfil orders where buy and sell prices overlap
