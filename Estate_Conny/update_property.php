<?php

//load and connect to MySQL database stuff
require("config.inc.php");
$login_ok = false;
if (!empty($_POST)) {
	$response = array();
		
		$user = $_POST ['userName'];
		//$contact = $_POST['contact'];
		//$name = $_POST['name'];
		//$location = $_POST['location'];
		//$status = $_POST['status'];
		//$price = $_POST['price'];
		//$description = $_POST['description'];
		//$city = $_POST['city'];
		//$pid = $_POST['pid'];
		
		
		if ($user != "" )//&& $contact != "" )//&& $location != "" && $price != "" && $description != "" && $city != "") 
		{
				$response ["message"] = '$user';
		}else {
				// required field is missing
				$response["success"] = 0;
				$response["message"] = "Required field(s) is missing";
				// echoing JSON response
				echo json_encode($response);
		}
} else {
?>
		<h1>Login</h1> 
		<form action="new_prop.php" method="post"> 
			Contact:<br /> 
		    <input type="text" name="contact" placeholder="username" /> 
		    <br /><br /> 
			Name:<br /> 
		    <input type="text" name="userName" placeholder="userName" /> 
		    <br /><br /> 
		    Location:<br /> 
		    <input type="text" name="location" placeholder="username" /> 
		    <br /><br /> 
			Price:<br /> 
		    <input type="text" name="price" placeholder="username" /> 
		    <br /><br /> 
			Description:<br /> 
		    <input type="text" name="description" placeholder="username" /> 
		    <br /><br /> 
		    <br /><br /> 
		    <input type="submit" value="Login" /> 
		</form>
<?php
}
?>		