<?php

$deleted = null;
$fileName = null;

$headers = getallheaders();

foreach ($headers as $key => $val) {
    if ($key == "fileToDelete")
        $fileName = $val;
}

//delete media file
if(is_null($fileName)){
	$deleted = false;
}
else{
	chdir("picam");
	unlink($fileName);
        $deleted = true;
}

$result = array("response" => $deleted);

//Send result
echo json_encode($result);

?>
