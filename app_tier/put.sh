var=$(</cloud/input)
echo $var
aws s3api put-object --bucket "3480-project" --key $var --body /cloud/file.txt

