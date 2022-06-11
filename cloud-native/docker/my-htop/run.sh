# build image
docker build -t myhtop .

# Run container
docker run -it --rm --pid=host myhtop