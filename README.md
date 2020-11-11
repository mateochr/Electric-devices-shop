# Electric devices shop
An electric shop with simple GUI in Java.

## Contributor
Alex Antoniou [ @antonal01 ]

## Classes

### Item

* Common variables and functions for derived classes (TV, Console, Camera etc.).
* Getters and setters.
* Print method.
* Maximum reuse of code.
* Polymorphism when calling common functions.

<img src="https://user-images.githubusercontent.com/58492424/93671106-e9ab7880-faa8-11ea-94b8-43ee3af50a67.png">




### Business

* Common variables and functions for derived classes (Order, Sale). 
* Contains a report for the device ordered/sold.
* Same properties apply as the class Item.

<img src="https://user-images.githubusercontent.com/58492424/93671122-0942a100-faa9-11ea-972e-a48a6623dc42.png">




### AvailableCatalogue || OrderedCatalogue || SoldCatalogue

* Three classes that implement the list of available items, ordered items and sold items respectivly. All of them use the ArrayList structure.
* Getters and setters.
* Print method.




### StoreFileR

In this class the reading and writing of ITEM_LIST.txt, ORDER_LIST.txt, SALE_LIST.txt is implemented. This works like a database, the format of the txt files is specific.
So after the reading, all the catalogues (AvailableCatalogue, OrderedCatalogue, SoldCatalogue) are initialized. When the app is closed the txt files are updated (e.g. new sales made).




### mainApp

In the main class the GUI is implemented along with all the functions that enable the user's interaction with the app.




## Extra files

* A folder with images that are used in the app.
* ITEM_LIST.txt: list with the items of the store.
* ORDER_LIST.txt: list with orders.
* SALE_LIST.txt: list with sales.

*the txt files have a specific format*


## HowTo
Open cmd on the same folder as the .java files, first run
`javac mainApp.java` and then `java mainApp`
