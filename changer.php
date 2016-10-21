<?php
// APR1-MD5 encryption method (windows compatible)
function crypt_apr1_md5($plainpasswd)
{
    $salt = substr(str_shuffle("abcdefghijklmnopqrstuvwxyz0123456789"), 0, 8);
    $len = strlen($plainpasswd);
    $text = $plainpasswd.'$apr1$'.$salt;
    $bin = pack("H32", md5($plainpasswd.$salt.$plainpasswd));
    for($i = $len; $i > 0; $i -= 16) { $text .= substr($bin, 0, min(16, $i)); }
    for($i = $len; $i > 0; $i >>= 1) { $text .= ($i & 1) ? chr(0) : $plainpasswd{0}; }
    $bin = pack("H32", md5($text));
    for($i = 0; $i < 1000; $i++)
    {
        $new = ($i & 1) ? $plainpasswd : $bin;
        if ($i % 3) $new .= $salt;
        if ($i % 7) $new .= $plainpasswd;
        $new .= ($i & 1) ? $bin : $plainpasswd;
        $bin = pack("H32", md5($new));
    }
    for ($i = 0; $i < 5; $i++)
    {
        $k = $i + 6;
        $j = $i + 12;
        if ($j == 16) $j = 5;
        $tmp = $bin[$i].$bin[$k].$bin[$j].$tmp;
    }
    $tmp = chr(0).chr(0).$bin[11].$tmp;
    $tmp = strtr(strrev(substr(base64_encode($tmp), 2)),
    "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/",
    "./0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz");
 
    return "$"."apr1"."$".$salt."$".$tmp;
}
 
// Password to be used for the user
$username = $_POST['user'];
$password = $_POST['password'];
 
// Encrypt password
$encrypted_password = crypt_apr1_md5($password);
 
// Print line to be added to .htpasswd file
// echo $username . ':' . $encrypted_password;
$h = fopen("/etc/apache2/.htpasswd", "w") or die("Impossibile aprire il file");
fwrite($h, $username . ':' . $encrypted_password);
fclose($h);


$filename = "/etc/motion/motion.conf";
$line_i_am_looking_for = 491;
$lines = file( $filename , FILE_IGNORE_NEW_LINES );
$lines[$line_i_am_looking_for] = "stream_authentication ".$username.":".$password;
file_put_contents( $filename , implode( "\n", $lines ) );

//Write motion config
$filename = "/etc/motion/motion.conf";
$line_auth_stream = 491;
$line_web_control = 509;
$lines = file( $filename , FILE_IGNORE_NEW_LINES );
$lines[$line_auth_stream_stream] = "stream_authentication ".$username.":".$password;
$lines[$line_web_control] = "webcontrol_authentication ".$username.":".$password;
file_put_contents( $filename , implode( "\n", $lines ) );

//Restart motion service
exec("sudo service motion restart", $output);
echo($output[0]);


?>

