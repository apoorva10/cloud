<?php

$var=$_GET['input'];
#echo $var;

$output = shell_exec("/usr/bin/python /var/www/html/client.py ".$var);
 $htmlMsg = "
        <html>
            <head>
                <title>CLOUD PI</title>
            </head>
            <body>
                <p>Digits of pi given the input :".$var." is: ".$output.".</p>
            </body>
        </html>
    ";

    echo $htmlMsg;


?>
