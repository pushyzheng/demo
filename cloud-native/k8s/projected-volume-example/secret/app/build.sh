sudo env GOOS=linux GOARCH=amd64 go build main.go

docker build --platform linux/x86_64 -t pushyzheng/projected-volume-example .