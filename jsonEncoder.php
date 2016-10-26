 <?php
        
        class Media{
            var $name;
            var $extension;
            var $size;
            var $date;
	    var $path;  
          
            function __construct($name, $extension, $size, $date, $dir) {
                $this->name = $name;
                $this->extension = $extension;
                $this->size = $size;
                $this->date = $date;
		$this->path = $dir."/".$name;
            }
            
            function getName() {
                return $this->name;
            }

	    function getPath() {
                return $this->path;
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
	    
	    function setPath($path) {
                $this->path = $path;
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
        
        $dir = "picam";
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
                        $media = new Media($name, $extension, $size, $date, $dir);
						if($media->getExtension() !== "avi"){
                        	array_push($mediaArray, $media);
						}
                        //echo $media->toString();
                    }
                }
                closedir($dh);
            }
        }
        
        echo json_encode($mediaArray);
        
?>