<?php

//load and connect to MySQL database stuff
require("config.inc.php");
$login_ok = false;
if (!empty($_POST)) {

	$fname = $_POST['fname'];
    $lname = $_POST['lname'];
    $username = $_POST['username'];
	$password = $_POST['password'];
    $location = $_POST['location'];
    $contact = $_POST['contact'];
	if ($fname != "" && $lname != "" && $username != "" && $password != "" && $location != "" && $contact != "") {
		$query = " 
            SELECT 
                uid,
				firstname,
				lastname, 
                username, 
                password
            FROM user 
            WHERE 
                username = :username 
        ";
    
		$query_params = array(
			':username' => $_POST['username']
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
		
		//This will be the variable to determine whether or not the user's information is correct.
		//we initialize it as false.
		$validated_info = false;
		
		//fetching all the rows from the query
		$row = $stmt->fetch();
		if ($row) {
			//if we encrypted the password, we would unencrypt it here, but in our case we just
			//compare the two passwords
			
				$login_ok = true;
		}
		
		if ($login_ok) {
			$response["success"] = 0;
			$response["message"] = "User-name already exist";
			die(json_encode($response));
		} else {
			//gets user's info based off of a username.
			$query = "INSERT INTO user(firstname, lastname, username, password, location, contact) VALUES('$fname', '$lname', '$username','$password','$location','$contact')";
			
			$query_params = array(
				':username' => $_POST['username']
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
				$response["message"] = "User created successfully!";
				die(json_encode($response));
			}
		}
		
	} else {
		// required field is missing
		$response["success"] = 0;
		$response["message"] = "Required field(s) is missing";

		// echoing JSON response
		echo json_encode($response);
	}
	
} else {
?>
		<h1>Login</h1> 
		<form action="create.php" method="post"> 
			Fname:<br /> 
		    <input type="text" name="fname" placeholder="username" /> 
		    <br /><br /> 
			Lname:<br /> 
		    <input type="text" name="lname" placeholder="username" /> 
		    <br /><br /> 
		    Username:<br /> 
		    <input type="text" name="username" placeholder="username" /> 
		    <br /><br /> 
		    Password:<br /> 
		    <input type="password" name="password" placeholder="password" value="" /> 
			Location:<br /> 
		    <input type="text" name="location" placeholder="username" /> 
		    <br /><br /> 
			Contact:<br /> 
		    <input type="text" name="contact" placeholder="username" /> 
		    <br /><br /> 
		    <br /><br /> 
		    <input type="submit" value="Login" /> 
		</form> 
<?php
}

?> 