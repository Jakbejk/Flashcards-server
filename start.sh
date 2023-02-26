sudo kill $(sudo lsof -t -i:8080)
sudo systemctl start mariadb
sudo systemctl enable mariadb
sudo systemctl enable jenkins
sudo systemctl start jenkins