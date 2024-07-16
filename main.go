package main

import (
	"database/sql"
	"encoding/json"
	"fmt"
	"log"
	"net/http"

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
	log.SetPrefix("[Server] ")
	log.SetFlags(0)

	err := openDb()
	if err != nil {
		log.Fatal(err)
	}
	log.Println("Connected to database")

	http.HandleFunc("/", func(w http.ResponseWriter, r *http.Request) {
		books, err := getAllBooks()
		if err != nil {
			log.Fatal(err)
		}
    jsonEncoded, err := json.Marshal(books)
    if err != nil {
      log.Fatal(err)
    }
		fmt.Fprintf(w, string(jsonEncoded))
	})

	log.Println("Listening on port 8080")
	err = http.ListenAndServe(":8080", nil)
	if err != nil {
		log.Fatal(err)
	}
}

func openDb() error {
	var err error
	db, err = sql.Open("sqlite", "./resources/inventory.db")
	if err != nil {
		return fmt.Errorf("openDb: %v", err)
	}
	err = db.Ping()
	if err != nil {
		return fmt.Errorf("openDb: %v", err)
	}
	return nil
}

func getAllBooks() ([]Book, error) {
	rows, err := db.Query("SELECT * FROM books")
	if err != nil {
		return nil, fmt.Errorf("getBooks: %v", err)
	}
	defer rows.Close()

	return rowsToBooks(rows)
}

func rowsToBooks(rows *sql.Rows) ([]Book, error) {
	var books []Book
	for rows.Next() {
		var book Book
		if err := rows.Scan(&book.ID, &book.Title, &book.Author, &book.Genre, &book.Price, &book.Stock); err != nil {
			return nil, fmt.Errorf("rowsToBooks: %v", err)
		}
		books = append(books, book)
	}

	if err := rows.Err(); err != nil {
		return nil, fmt.Errorf("rowsToBooks: %v", err)
	}
  return books, nil
}
