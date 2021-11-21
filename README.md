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
### OR
Import the project folder through Android studio
1. Download the Zip folder of this project
2. Open Android studio
3. Select File > New > Import project > Search for the downloaded project folder

## Limitation of current version
The current application are assuming that user only place one order per time and the next order can only be placed after the previous order status is updated to "On My Way". This is because the current version is using timer to update the order status. However, in reality the order status should be updated from database instead of timer.

## Screenshot of Deli Rush
#### Main Page
<img src="./Deli Rush Images/1.png" width="100" height="180">
### Login Page
If the password entered is wrong, it displayed "Incorrect Password and reduce one attempt
If attempts are used up, the log in button can't be clicked.
Current version allowed to update the attemps remaining by restarting the application.
<img src="./Deli Rush Images/2.png" width="100" height="180">
<img src="./Deli Rush Images/3.png" width="100" height="180">
<img src="./Deli Rush Images/4.png" width="100" height="180">
<img src="./Deli Rush Images/5.png" width="100" height="180">
### Food Stalls Page
<img src="./Deli Rush Images/6.png" width="100" height="180">
<img src="./Deli Rush Images/7.png" width="100" height="180">
### Menu Page for each food stalls
<img src="./Deli Rush Images/8a.png" width="100" height="180">
<img src="./Deli Rush Images/8b.png" width="100" height="180">
<img src="./Deli Rush Images/8c.png" width="100" height="180">
### Initial Cart Page & Order Page
<img src="./Deli Rush Images/9.png" width="100" height="180">
<img src="./Deli Rush Images/10.png" width="100" height="180">
### Side Bar Menu
<img src="./Deli Rush Images/11.png" width="100" height="180">
### Log out Dialog
<img src="./Deli Rush Images/12.png" width="100" height="180">
### Quantity Dialog
If user select any one of the food from the menu, Quantity Dialog displayed and prompt quantity from the user.
<img src="./Deli Rush Images/13.png" width="100" height="180">
<img src="./Deli Rush Images/14.png" width="100" height="180">
<img src="./Deli Rush Images/15.png" width="100" height="180">
### Cart Page
Click "ADD TO CART" on the Quantity Dialog by providing a quantiy > 0
<img src="./Deli Rush Images/16.png" width="100" height="180">
### Order Page from another dialog but the cart is not empty
The application pops up dialog to proceed with new order from new food stall.
<img src="./Deli Rush Images/17.png" width="100" height="180">
<img src="./Deli Rush Images/18.png" width="100" height="180">
### Cart Page
Click "Proceed" on dialog which informs on "Adding from different food stall"
<img src="./Deli Rush Images/19.png" width="100" height="180">
### Payment Dialog
This is just to inform that current version does not integrate with third-party payment system.
*This dialog will be removed in future when integration of third-party payment system is successful.
### Order Page
Click "Place Order" on Cart Page and the cart is non-empty
Display the record of order(s) included the history.
<img src="./Deli Rush Images/20.png" width="100" height="180">
<img src="./Deli Rush Images/21.png" width="100" height="180">
### Notification Bar
* Straight after order placed
<img src="./Deli Rush Images/22.png" width="100" height="180">
### Notification Bar
* When the alarm is ringing
<img src="./Deli Rush Images/23.png" width="100" height="180">
### Order Ready Dialog
<img src="./Deli Rush Images/24.png" width="100" height="180">
### Update the status of order
<img src="./Deli Rush Images/25.png" width="100" height="180">