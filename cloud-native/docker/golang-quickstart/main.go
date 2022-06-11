package main

import (
	"fmt"
	"net/http"
)

func main() {
	http.HandleFunc("/", func(resp http.ResponseWriter, req *http.Request) {
		resp.Write([]byte("Hello World"))
	})

	addr := "0.0.0.0:9000"
	fmt.Println("Running on http://" + addr)
	err := http.ListenAndServe(addr, nil)
	if err != nil {
		panic(err)
	}
}
