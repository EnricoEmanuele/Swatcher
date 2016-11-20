<?php

// APR1-MD5 encryption method (windows compatible)
function crypt_apr1_md5($plainpasswd) {
    $salt = substr(str_shuffle("abcdefghijklmnopqrstuvwxyz0123456789"), 0, 8);
    $len = strlen($plainpasswd);
    $text = $plainpasswd . '$apr1$' . $salt;
    $bin = pack("H32", md5($plainpasswd . $salt . $plainpasswd));
    for ($i = $len; $i > 0; $i -= 16) {
        $text .= substr($bin, 0, min(16, $i));
    }
    for ($i = $len; $i > 0; $i >>= 1) {
        $text .= ($i & 1) ? chr(0) : $plainpasswd{0};
    }
    $bin = pack("H32", md5($text));
    for ($i = 0; $i < 1000; $i++) {
        $new = ($i & 1) ? $plainpasswd : $bin;
        if ($i % 3)
            $new .= $salt;
        if ($i % 7)
            $new .= $plainpasswd;
        $new .= ($i & 1) ? $bin : $plainpasswd;
        $bin = pack("H32", md5($new));
    }
    for ($i = 0; $i < 5; $i++) {
        $k = $i + 6;
        $j = $i + 12;
        if ($j == 16)
            $j = 5;
        $tmp = $bin[$i] . $bin[$k] . $bin[$j] . $tmp;
    }
    $tmp = chr(0) . chr(0) . $bin[11] . $tmp;
    $tmp = strtr(strrev(substr(base64_encode($tmp), 2)), "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/", "./0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz");

    return "$" . "apr1" . "$" . $salt . "$" . $tmp;
}

//Current credentials
$username = $_SERVER['PHP_AUTH_USER'];
$password = $_SERVER['PHP_AUTH_PW'];

$new_username = null;
$new_password = null;

$headers = getallheaders();

foreach ($headers as $key => $val) {
    if ($key == "new_username")
        $new_username = $val;
    if ($key == "new_password")
        $new_password = $val;
}

$newCredentials = $new_username . ":" . $new_password;

if ($new_username !== null and $new_password !== null and $new_username !== '' and $new_password !== '') {

    /*
     * Write apache .htpasswd file
     */
    //encrypt password
    $encrypted_password = crypt_apr1_md5($new_password);

    //write file
    $h = fopen("/etc/apache2/.htpasswd", "w") or die("Impossibile aprire il file");
    fwrite($h, $new_username . ':' . $encrypted_password);
    fclose($h);
    
    /*
     * Write motion config
     */
    
    $currentCredentials = $username . ":" . $password;

    //set-streaming-anth-request
    $setStreaming = curl_init();
    curl_setopt_array($setStreaming, array(
        CURLOPT_RETURNTRANSFER => true,
        CURLOPT_URL => 'http://192.168.1.111:4321/0/config/set?stream_authentication=' . $newCredentials,
    ));
    curl_setopt($setStreaming, CURLOPT_HTTPAUTH, CURLAUTH_BASIC);
    curl_setopt($setStreaming, CURLOPT_USERPWD, $currentCredentials);
    $respStreaming = curl_exec($setStreaming);
    curl_close($setStreaming);

    //set-webcontrol-auth-request
    $setWebcontrol = curl_init();
    curl_setopt_array($setWebcontrol, array(
        CURLOPT_RETURNTRANSFER => true,
        CURLOPT_URL => 'http://192.168.1.111:4321/0/config/set?webcontrol_authentication=' . $newCredentials,
    ));
    curl_setopt($setWebcontrol, CURLOPT_HTTPAUTH, CURLAUTH_BASIC);
    curl_setopt($setWebcontrol, CURLOPT_USERPWD, $currentCredentials);
    $respWebcontrol = curl_exec($setWebcontrol);
    curl_close($setWebcontrol);

    //write-request
    $write = curl_init();
    curl_setopt_array($write, array(
        CURLOPT_RETURNTRANSFER => true,
        CURLOPT_URL => 'http://192.168.1.111:4321/0/config/write',
    ));
    curl_setopt($write, CURLOPT_HTTPAUTH, CURLAUTH_BASIC);
    curl_setopt($write, CURLOPT_USERPWD, $currentCredentials);
    $respWrite = curl_exec($write);
    curl_close($write);

    //Restart motion service
    //exec("sudo service motion restart", $output);
    $restart = curl_init();
    curl_setopt_array($restart, array(
        CURLOPT_RETURNTRANSFER => true,
        CURLOPT_URL => 'http://192.168.1.111:4321/0/action/restart',
    ));
    curl_setopt($restart, CURLOPT_HTTPAUTH, CURLAUTH_BASIC);
    curl_setopt($restart, CURLOPT_USERPWD, $currentCredentials);
    $respRestart = curl_exec($restart);
    curl_close($restart);
    
    //new credentials
    $result = array("new_username" => $new_username, "new_password" => $new_password);
}
else{
    //if new_username and new_password are empty fields return "old username" and "old password"
    $result = array("new_username" => $username, "new_password" => $password);
}

//Send result
echo json_encode($result);

?>
