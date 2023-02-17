docker stop app
docker rm app
docker rmi webapp
cd /usr/local/src || exit
docker build -t webapp .
docker run -p 8080:8080 -d --name app --link mysql:emysql --link redis:eredis webapp
fc-list :lang=zh
