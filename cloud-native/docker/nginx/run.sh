docker run \
  -p 8080:80 \
  --name nginx-server \
  --rm \
  --volume "$PWD/conf.d":/etc/nginx/conf.d \
  --volume "$PWD/html":/usr/share/nginx/html \
  --volume "$PWD/logs":/var/log/nginx \
  nginx
