<?php

	define('HOST','localhost');
	define('USER','root');
	define('PASS','');
	define('DB','andestate');
	
	//this is our upload folder 
 $upload_path = 'upload/';
 
 //Getting the server ip 
 $server_ip = gethostbyname(gethostname());
 
 //creating the upload url 
 $upload_url = 'http://'.$server_ip.'/Estate_Conny/upload/'; 
 
 //response array 
 $response = array(); 
 
 
 if($_SERVER['REQUEST_METHOD']=='POST'){
 
 //checking the required parameters from the request 
 if(isset($_POST['name']) and isset($_FILES['image']['name'])){
 
 //connecting to the database 
 $con = mysqli_connect(HOST,USER,PASS,DB) or die('Unable to Connect...');
 
 //getting name from the request 
 $name = $_POST['name'];
 
 //getting file info from the request 
 $fileinfo = pathinfo($_FILES['image']['name']);
 
 //getting the file extension 
 $extension = $fileinfo['extension'];
 
 //file url to store in the database 
 $file_url = $upload_url . $name . '.' . $extension;
 
 //file path to upload in the server 
 $file_path = $upload_path . $name . '.'. $extension; 
 
 //trying to save the file in the directory 
 try{
 //saving the file 
 move_uploaded_file($_FILES['image']['tmp_name'],$file_path);
 
 $sql = "INSERT INTO `andestate`.`image` (`id`, `url`, `name`) VALUES (NULL, '$file_url', '$name');";
 
 
 //adding the path and name to database 
 if(mysqli_query($con,$sql)){
 
 //filling response array with values 
 $response['error'] = false; 
 $response['url'] = $file_url; 
 $response['name'] = $name;
 }
 //if some error occurred 
 }catch(Exception $e){
 $response['error']=true;
 $response['message']=$e->getMessage();
 } 
 //displaying the response 
 echo json_encode($response);
 
 //closing the connection 
 mysqli_close($con);
 }else{
 $response['error']=true;
 $response['message']='Please choose a file';
 }
 }
 
 /*
 We are generating the file name 
 so this method will return a file name for the image to be upload 
 */
 function getFileName(){
 $con = mysqli_connect(HOST,USER,PASS,DB) or die('Unable to Connect...');
 $sql = "SELECT max(id) as id FROM images";
 $result = mysqli_fetch_array(mysqli_query($con,$sql));
 
 mysqli_close($con);
 if($result['id']==null)
 return 1; 
 else 
 return ++$result['id']; 
 }
?>