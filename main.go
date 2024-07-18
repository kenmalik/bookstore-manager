package main

import (
	"database/sql"
	"encoding/json"
	"fmt"
	"html/template"
	"log"
	"net/http"
	"strconv"

	_ "modernc.org/sqlite"
)

var db *sql.DB
var templates = template.Must(template.ParseFiles("tmpl/all.html"))

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

	if err := openDb(); err != nil {
		log.Fatal(err)
	}
	log.Println("Connected to database")

  http.Handle("/css/", http.StripPrefix("/css/", http.FileServer(http.Dir("./css"))))
  http.HandleFunc("/", func (w http.ResponseWriter, r *http.Request) {
    http.ServeFile(w, r, "index.html")
  })
	http.HandleFunc("/all", handleAll)
	http.HandleFunc("/search", handleSearch)

	log.Println("Listening on port 8080")
  log.Fatal(http.ListenAndServe(":8080", nil))
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

func getBookById(id int64) (Book, error) {
	var book Book

	row := db.QueryRow("SELECT * FROM books WHERE id = ?", id)
	if err := row.Scan(&book.ID, &book.Title, &book.Author, &book.Genre, &book.Price, &book.Stock); err != nil {
		return book, err
	}
	return book, nil
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

type Books = []Book

type Data struct {
  Books Books
}

func handleAll(w http.ResponseWriter, r *http.Request) {
		if r.Method != "GET" {
			return
		}

		books, err := getAllBooks()
		if err != nil {
			log.Fatal(err)
		}
    page := Data{Books: books}

    templates.ExecuteTemplate(w, "all.html", page)
}

func handleSearch(w http.ResponseWriter, r *http.Request) {
		if r.Method != "GET" {
			return
		}

		query := r.URL.Query()
		var idParam string
		if idParam = query.Get("id"); idParam == "" {
			return
		}

		id, err := strconv.ParseInt(idParam, 10, 64)
		if err != nil {
			w.WriteHeader(400)
			fmt.Fprintln(w, "Invalid search parameters")
			return
		}

		book, err := getBookById(id)
		if err != nil {
			if err == sql.ErrNoRows {
				w.WriteHeader(404)
				fmt.Fprintln(w, "No book with matching id")
				return
			}
			log.Fatal("/search?id="+idParam+":", err)
		}

		jsonEncoded, err := json.Marshal(book)
		if err != nil {
			log.Fatal(err)
		}
		fmt.Fprint(w, string(jsonEncoded))
}
