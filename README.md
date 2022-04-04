# online-bookstore
online bookstore master branch

STESP to RUN
===========================
1.Create  database using Db script.
create database bookstoreApp;
use bookstoreApp;
Execute the postgresql script 
/online-bookstore/src/main/resources/schema.sql

2.Execute the main method in /online-bookstore/src/main/java/com/org/demo/bookstore/OnlineBookstoreApplication.java

3.Add book to the book store API.

URL:http://localhost:8083/online-bookstore/v1/books/add-book

Request Body:{
    "isbn":"121323",
    "title":"abc",
    "author":"pqr",
    "quantity":2,
    "price":"10"
}

4.Search book API.
http://localhost:8083/online-bookstore/v1/books/search?author=xyz

5.Search media coverage of book API.
http://localhost:8083/online-bookstore/v1/books/search-media-coverage/title/provident

6.Buy book API.
http://localhost:8083/online-bookstore/v1/books/buy-book

    [
        {
            "title": "abc",
            "quantity": 1
        },
        {
            "title": "and",
            "quantity": 1
        }
    ]


