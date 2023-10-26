## docker ps --format "table {{.ID}}\t{{.Status}}\t{{.Names}}"
## docker network ls
## docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' 8e3fa1c64497