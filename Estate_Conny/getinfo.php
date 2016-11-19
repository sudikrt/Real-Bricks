<?php

//load and connect to MySQL database stuff
require("config.inc.php");
$login_ok = false;
if (!empty($_POST)) {
	$username = $_POST['username'];
	
	$query_params = array(
			':username' => $_POST['username']
		);
	//initial query
	$query = "Select * FROM user WHERE username = '$username'";

	//execute query
	try {
		$stmt   = $db->prepare($query);
		$result = $stmt->execute($query_params);
	}
	catch (PDOException $ex) {
		$response["success"] = 0;
		$response["message"] = "Database Error!";
		die(json_encode($response));
	}
	
	$row = $stmt->fetch();
	if ($row) {
		//if we encrypted the password, we would unencrypt it here, but in our case we just
		//compare the two passwords
	
		$login_ok = true;
		$response["success"] = 1;
		$response["message"] = "Getting user Info!";
		$response["posts"]   = array();
		
		$post             = array();
		$post["username"] = $row["username"];
		$post["fname"]    = $row["firstname"];
		$post["lname"]  = $row["lastname"];
		$post["location"]   = $row["location"];
		$post["contact"] 	  = $row["contact"];
				
		//update our repsonse JSON data
		array_push($response["posts"], $post);
			
		// echoing JSON response
		echo json_encode($response);
			
	}
	else {
		$response["success"] = 0;
		$response["message"] = "Not a valid user!";
		die(json_encode($response));
	}
	
} else {
?>
		<h1>Login</h1> 
		<form action="getinfo.php" method="post"> 
		
		    Username:<br /> 
		    <input type="text" name="username" placeholder="username" /> 
		    <br /><br /> 
		    
		    <input type="submit" value="Login" /> 
		</form> 
<?php
}

?> 