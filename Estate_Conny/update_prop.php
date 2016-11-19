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
	$status = $_POST['status'];
	$price = $_POST['price'];
	$description = $_POST['description'];
	$city = $_POST['city'];
	$pid = $_POST['pid'];
	
	
	
	
	if ($name != "" && $contact != "" && $location != "" && $price != "" && $description != "" && $city != "") 
	{
			$query = " 
				SELECT 
					pid
				FROM property 
				WHERE 
					pid = :pid and userName = :user
			";
		
			$query_params = array(
				':pid' => $_POST['pid'], ':user' => $_POST['userName']
				
			);
			
			try {
				$stmt   = $db->prepare($query);
				$result = $stmt->execute($query_params);
			}
			catch (PDOException $ex) {
				// For testing, you could use a die and message. 
				//die("Failed to run query: " . $ex->getMessage());
				
				//or just use this use this one to product JSON data:
				$response["success"] = 0;
				$response["message"] = "Database Error1. Please Try Again!";
				die(json_encode($response));
				
			}
			
			
			//fetching all the rows from the query
			$row = $stmt->fetch();
			if ($row) {
				//if we encrypted the password, we would unencrypt it here, but in our case we just
				//compare the two passwords
				
					$login_ok = true;
			}
			
			if ($login_ok) {
			
				//gets user's info based off of a username.
				$query = "UPDATE property SET
								name='$name', 
								location='$location', 
								contact='$contact', 
								city='$city', 
								status='$status', 
								price='$price', 
								description='$description' 
								WHERE pid=$pid";
				//$query = "INSERT INTO user(firstname, lastname, username, password, location, contact) VALUES('$fname', '$lname', '$username','$password','$location','$contact')";
				
				$query_params = array(
					':username' => $_POST['userName']
				);
				
				try {
					$stmt   = $db->prepare($query);
					$result = $stmt->execute($query_params);
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
					$response["message"] = "Property Updated!";
					die(json_encode($response));
				}
			} else {
				$response["success"] = 0;
				$response["message"] = "Property id mismatch";
				die(json_encode($response));
			}
			
			
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
		<form action="update_prop.php" method="post"> 
			UserName:<br /> 
		    <input type="text" name="userName" placeholder="username" /> 
		    <br /><br /> 
			Contact:<br /> 
		    <input type="text" name="contact" placeholder="contact" /> 
		    <br /><br /> 
		    Name:<br /> 
		    <input type="text" name="name" placeholder="name" /> 
		    <br /><br /> 
			Location:<br /> 
		    <input type="text" name="location" placeholder="Location" /> 
		    <br /><br /> 
			Status:<br /> 
		    <input type="text" name="status" placeholder="status" /> 
		    <br /><br /> 
			Price:<br /> 
		    <input type="text" name="price" placeholder="price" /> 
			<br /><br /> 
			Desc:<br /> 
		    <input type="text" name="description" placeholder="description" /> 
			<br /><br /> 
			City:<br /> 
		    <input type="text" name="city" placeholder="City" /> 
			<br /><br /> 
			Pid:<br /> 
		    <input type="text" name="pid" placeholder="pid" /> 
			
		    <br /><br /> 
		    <br /><br /> 
		    <input type="submit" value="Login" /> 
		</form>
<?php
}
?>		