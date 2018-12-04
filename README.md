[![Build Status](https://www.travis-ci.org/Arci/sales-taxes.svg?branch=master)](https://www.travis-ci.org/Arci/sales-taxes)

# Sales Taxes
Basic sales tax is applicable at a rate of 10% on all goods, except books, food, and medical products that are exempt. Import duty is an additional sales tax applicable on all imported goods at a rate of 5%, with no exemptions.

When I purchase items I receive a receipt which lists the name of all the items and their price (including tax), finishing with the total cost of the items, and the total amounts of sales taxes paid. The rounding rules for sales tax are that for a tax rate of n%, a shelf price of p contains (np/100 rounded up to the nearest 0.05) amount of sales tax.

# Assumptions

## Products categories
A file containing the categories of the various products is supplied at startup, the file is such that:
- is a CSV with two columns: 
    - *product* that is the product name
    - *category* that is its the category to which it belongs
- If a product cannot be found within categories it's supposed to be eligible for the basic sales tax
- categories can be saved in memory, an `HashSet` has been used to store them, otherwise they should be saved persistently on a DB or the file read by chunk


# Build the application
The application can be build with Maven:

```
mvn clean instal
```

# Run the application
The application is written in Java using Oracle's JDK11:

```
java -jar  SalesTaxes -c "/path/to/categories.csv" -b "/path/to/basket.txt" -o "/path/to/result.txt"
```

Givin no arguments the help is printed:

```
usage: SalesTaxes
 -b,--basket <arg>       the shopping basket file path
 -c,--categories <arg>   the categories file path
 -o,--output <arg>       the file to which output the receipt
```