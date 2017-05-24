cd /home/mapp/wmsLib
nohup java -Dspring.profiles.active=develop -XX:+HeapDumpOnOutOfMemoryError  -XX:HeapDumpPath=/home/mapp
-server -Xms512m -Xmx512M -jar wmsMainServer-1.0.jar &
sleep 5