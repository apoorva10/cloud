echo $1
ssh -vvv -i "/Users/apoorvavenkatesh/Downloads/cloudpi.pem" ubuntu@52.32.98.46 -X 'cd /var/www/html; sh try.sh'
