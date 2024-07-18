package main

import (
	"database/sql"
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

type FormData struct {
	Values map[string]string
	Errors map[string]string
}

func newFormData() FormData {
	return FormData{
		Values: make(map[string]string),
		Errors: make(map[string]string),
	}
}

type Books = []Book

type Data struct {
	Books Books
	Form  FormData
}

func main() {
	log.SetPrefix("[Server] ")
	log.SetFlags(0)

	if err := openDb(); err != nil {
		log.Fatal(err)
	}
	log.Println("Connected to database")

	http.Handle("/css/", http.StripPrefix("/css/", http.FileServer(http.Dir("./css"))))
	http.HandleFunc("/", func(w http.ResponseWriter, r *http.Request) {
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

func handleAll(w http.ResponseWriter, r *http.Request) {
	if r.Method != "GET" {
		return
	}

	books, err := getAllBooks()
	if err != nil {
		log.Fatal(err)
	}
	page := Data{Books: books, Form: newFormData()}

	templates.ExecuteTemplate(w, "all", page)
}

func handleSearch(w http.ResponseWriter, r *http.Request) {
	if r.Method != "GET" {
		return
	}

	query := r.FormValue("id")

	id, err := strconv.ParseInt(query, 10, 64)
	if err != nil {
		formData := newFormData()
		formData.Values["search"] = query
		formData.Errors["search"] = "Invalid search term"
		w.Header().Add("HX-Push-Url", "false")
    w.WriteHeader(400)
		templates.ExecuteTemplate(w, "form", formData)
		return
	}

	book, err := getBookById(id)
	if err != nil {
		if err == sql.ErrNoRows {
			formData := newFormData()
			formData.Values["search"] = query
			formData.Errors["search"] = "No book with matching id"
      w.Header().Add("HX-Push-Url", "false")
      w.WriteHeader(404)
			templates.ExecuteTemplate(w, "form", formData)
			return
		}
		formData := newFormData()
		formData.Values["search"] = query
		formData.Errors["search"] = "Server error"
    w.WriteHeader(500)
		templates.ExecuteTemplate(w, "form", formData)
		return
	}

	templates.ExecuteTemplate(w, "form", newFormData())
	templates.ExecuteTemplate(w, "oob-book", book)
}
