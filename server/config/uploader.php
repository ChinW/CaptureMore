<?php

$relative_path = "upload/lecture/";
if(isset($_SERVER["SERVER_NAME"]) && in_array(substr($_SERVER["SERVER_NAME"], 0, 5), ["lectu"] )){
    $php_path =  "F:\Tools\Program Handle\WAMP\bin\php\php5.5.12\php.exe";
}else{
    $php_path =  "C:\wamp\bin\php\php5.5.12\php.exe";   
}

return [

    'relative_path' => "upload/lecture/",

    'absolute_path' => public_path()."/".$relative_path,

    "php_path" => $php_path
];
