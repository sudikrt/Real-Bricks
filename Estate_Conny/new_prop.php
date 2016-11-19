<?php

//load and connect to MySQL database stuff
require("config.inc.php");
$login_ok = false;
if (!empty($_POST)) {
	$response = array();
		
		$user = $_POST ['userName'];
		$contact = $_POST['contact'];
		$name = $_POST['name'];
		$location = $_POST['location'];
		$status = "For-Sale";
		$price = $_POST['price'];
		$description = $_POST['description'];
		$image = $_POST['path'];
		$city = $_POST['city'];
		if ($image == "") {
			$image = "default.jpg";
		}
		
		if ($name != "" && $contact != "" && $location != "" && $price != "" && $description != "" && $city != "") 
		{
			//gets user's info based off of a username.
			$query = "INSERT INTO property(userName, contact, name, location, city, status, price, description, image) VALUES('$user', '$contact','$name', '$location', '$city', '$status','$price','$description', '$image')";
			
			
			
			try {
				$stmt   = $db->prepare($query);
				$result = $stmt->execute();
			}
			catch (PDOException $ex) {
				// For testing, you could use a die and message. 
				//die("Failed to run query: " . $ex->getMessage());
				
				//or just use this use this one to product JSON data:
				$response["success"] = 0;
				$response["message"] = "Database Error1. Please Try Again!";
				die(json_encode($response));
			}
			if ( $result > 0 ) {
				$response["success"] = 1;
				$response["message"] = "User created successfully!";
				die(json_encode($response));
			}	
		}
		else {
			$response["success"] = 0;
			$response["message"] = "Missing Fields";
			die(json_encode($response));
		}
}else {
		// required field is missing
		$response["success"] = 0;
		$response["message"] = "Required field(s) is missing";

		// echoing JSON response
		echo json_encode($response);
}

?>
		<h1>Login</h1> 
		<form action="new_prop.php" method="post"> 
			Contact:<br /> 
		    <input type="text" name="contact" placeholder="username" /> 
		    <br /><br /> 
			Name:<br /> 
		    <input type="text" name="name" placeholder="username" /> 
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