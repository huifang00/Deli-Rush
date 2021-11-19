# Deli Rush
The idea of implementing this application came from the long queue of ordering food during lunch time and dinner time. 
This scenario can be seen in The University of Nottingham Malaysia especially during lunch break.
Next, after ordering the food from the stall, most of the customers go to other places such as finding seats while waiting for the food to be prepared by the seller.
Hence, this leads the issue the seller rings the call bell when the food is ready but no one responds to it and leads to more and more food accumulated at the stall.
However, from a different perspective, if the customers are waiting in front of the stall, this leads to increasing of crowd in front of the stall.
Due to current pandemic situation, crowd of people should be avoided.

## Purpose of the Deli Rush
1. Cut down the queue of ordering food
2. Notify the customer when the food is ready for collection
3. Reduce the time of lining up for ordering food

## Installation of the application
Clone the repository to your local by typing below commamd
> $ git clone https://github.com/huifang00/Deli-Rush.git
#### OR
Import the project folder through Android studio
1. Download the Zip folder of this project
2. Open Android studio
3. Select File > New > Import project > Search for the downloaded project folder

### Limitation of current version
The current application are assuming that user only place one order per time and the next order can only be placed after the previous order status is updated to "On My Way". This is because the current version is using timer to update the order status. However, in reality the order status should be updated from database instead of timer.

