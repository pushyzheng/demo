package main

import (
	"context"
	"fmt"
	"github.com/go-redis/redis/v8"
	"net/http"
	"os"
)

var ctx = context.Background()

var client *redis.Client

func set(resp http.ResponseWriter, req *http.Request) {
	err := client.Set(ctx, "key", "value", 0).Err()
	if err != nil {
		panic(err)
	}
	resp.Write([]byte("ok"))
}

func get(resp http.ResponseWriter, req *http.Request) {
	value, err := client.Get(ctx, "key").Result()
	if err != nil {
		resp.Write([]byte("Error:" + err.Error()))
		return
	}
	fmt.Println("value:", value)
	resp.Write([]byte(value))
}

func main() {
	env := os.Getenv("env")
	if len(env) == 0 {
		env = "dev"
	}
	fmt.Println("env:", env)

	client = redis.NewClient(&redis.Options{
		Addr:     getServerHost(env),
		Password: "", // no password set
		DB:       0,  // use default DB
	})
	client.Ping(ctx)

	http.HandleFunc("/", set)
	http.HandleFunc("/get", get)

	addr := "0.0.0.0:9000"
	fmt.Println("Running on http://" + addr)
	err := http.ListenAndServe(addr, nil)
	if err != nil {
		panic(err)
	}
}

func getServerHost(env string) string {
	var serverHost string
	if env == "dev" {
		serverHost = "localhost:6379"
	} else {
		// 由于在用一个 docker-compose 下， 所有可以直接通过 service-name 访问到
		serverHost = "redis-server:6379"
	}
	fmt.Println("server host:", serverHost)
	return serverHost
}
