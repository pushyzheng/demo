sudo env GOOS=linux GOARCH=amd64 go build main.go

docker build --platform linux/x86_64 -t registry.cn-hangzhou.aliyuncs.com/pushyzheng/golang-quickstart .