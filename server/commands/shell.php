<?php
// set_time_limit(0); 
// $data = shell_exec("echo %errorLevel%");
// echo $data;
// $data = shell_exec("whoami");
// echo $data;
exec('"F:\Tools\Program Handle\WAMP\bin\php\php5.5.12\php.exe" "./word2pdf.php"', $output, $error);
echo ($error);