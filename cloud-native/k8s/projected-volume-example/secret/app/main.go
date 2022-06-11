package main

import (
	"fmt"
	"os"
	"time"
)

func main() {
	username := readFile("/projected-volume/username.txt")
	password := readFile("/projected-volume/password.txt")

	fmt.Printf("connection mysql server, username: %s, password: %s",
		username, password)

	time.Sleep(30 * time.Minute)
}

func readFile(filename string) string {
	b, err := os.ReadFile(filename)
	if err != nil {
		panic(err)
	}
	return string(b)
}
