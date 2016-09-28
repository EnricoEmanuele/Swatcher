<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
    <head>
        <meta charset="UTF-8">
        <title></title>
    </head>
    <body>
        <?php
        
        class Media{
            var $name;
            var $extension;
            var $size;
            var $date;
            
            function __construct($name, $extension, $size, $date) {
                $this->name = $name;
                $this->extension = $extension;
                $this->size = $size;
                $this->date = $date;
            }
            
            function getName() {
                return $this->name;
            }

            function getExtension() {
                return $this->extension;
            }

            function getSize() {
                return $this->size;
            }

            function getDate() {
                return $this->date;
            }

            function setName($name) {
                $this->name = $name;
            }

            function setExtension($extension) {
                $this->extension = $extension;
            }

            function setSize($size) {
                $this->size = $size;
            }

            function setDate($date) {
                $this->date = $date;
            }
            
            function toString(){
                return "FILENAME: ".$this->name."<br>"."SIZE: ".$this->size."<br>"."TYPE: ".$this->extension."<br>"."DATE: ".$this->date."<br>";
            }
        
        }
        
        $dir = "/mnt/picam";
        $mediaArray = [];
        
        // Open a directory, and read its contents
        if (is_dir($dir)) {
            if ($dh = opendir($dir)) {
                while (($file = readdir($dh)) !== false) {
                    if ($file !== ".." && $file !== ".") {
                        $name = $file;
                        $extension = pathinfo($dir . '/' . $file, PATHINFO_EXTENSION);
                        $date = date("F d Y H:i:s.",filemtime($dir . '/' . $file));
                        $size = filesize($dir . '/' . $file);
                        $media = new Media($name, $extension, $size, $date);
                        array_push($mediaArray, $media);
                        //echo $media->toString();
                    }
                }
                closedir($dh);
            }
        }
        
        echo json_encode($mediaArray);
        
        ?>
    </body>
</html>
