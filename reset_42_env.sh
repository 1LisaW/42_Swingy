#!/bin/sh

USER="tklimova"

docker system prune -a


# Move Docker's data root
systemctl --user stop docker
mkdir -p /goinfre/$USER/docker-data
mkdir -p ~/.config/docker
touch ~/.config/docker/daemon.json
echo '{
  "data-root": "/goinfre/$USER/docker-data"
}' > ~/.config/docker/daemon.json
systemctl --user start docker
docker info | grep "Docker Root Dir"

docker build -t swing-builder .