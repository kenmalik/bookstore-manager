package main

import (
	"database/sql"
	"encoding/csv"
	"fmt"
	"io"
	"log"
	"os"
	"strconv"
	"strings"

	_ "modernc.org/sqlite"
)

var db *sql.DB

type Book struct {
	ID     int64
	Title  string
	Author string
	Genre  string
	Price  float64
	Stock  int64
}

func main() {
	var openErr error
	db, openErr = sql.Open("sqlite", "./inventory.db")
	if openErr != nil {
		log.Fatal(openErr)
	}
	pingErr := db.Ping()
	if pingErr != nil {
		log.Fatal(pingErr)
	}

	dat, err := os.ReadFile("./inventory.dat")
	if err != nil {
		log.Fatal(err)
	}

	r := csv.NewReader(strings.NewReader(string(dat)))

	for {
		record, readErr := r.Read()
		if readErr == io.EOF {
			break
		}
		if readErr != nil {
			log.Fatal(err)
		}

    price, conversionErr := strconv.ParseFloat(record[3], 64)
    if conversionErr != nil {
      log.Fatal(conversionErr)
    }
    stock, conversionErr := strconv.ParseInt(record[4], 10, 64)
    if conversionErr != nil {
      log.Fatal(conversionErr)
    }

    book := Book{
      Title: record[0],
      Author: record[1],
      Genre: record[2],
      Price: price,
      Stock: stock,
    }
    id, err := addBook(book)
    if err != nil {
      log.Fatal(err)
    }
    fmt.Printf("Inserted book with ID %v\n", id)
	}
}

func addBook(book Book) (int64, error) {
	result, err := db.Exec(
		"INSERT INTO books (title, author, genre, price, stock) VALUES (?, ?, ?, ?, ?)",
		book.Title, book.Author, book.Genre, book.Price, book.Stock)
  if err != nil {
    return 0, fmt.Errorf("addBook: %v", err)
  }
  id, err := result.LastInsertId()
  if err != nil {
    return 0, fmt.Errorf("addBook: %v", err)
  }
  return id, nil
}
