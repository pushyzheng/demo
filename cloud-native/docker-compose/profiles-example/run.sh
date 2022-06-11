if [ "$1" = "debug" ]; then
  echo "Run with debug"
  docker compose --profile debug up

elif [ "$1" = "frontend" ]; then
  echo "Run with frontend"
  docker compose --profile frontend up

else
  echo "unsupported args"
fi
