<?php

/*
Our "config.inc.php" file connects to database every time we include or require
it within a php script.  Since we want this script to add a new user to our db,
we will be talking with our database, and therefore,
let's require the connection to happen:
*/
require("config.inc.php");
if (!empty ($_POST)) {
		$username = $_POST['username'];

	$query_params = array(
			':username' =>'jack' 
		);
	//initial query
	$query = "Select * FROM property where username = '$username'";

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

	// Finally, we can retrieve all of the found rows into an array using fetchAll 
	$rows = $stmt->fetchAll();


	if ($rows) {
		$response["success"] = 1;
		$response["message"] = "Post Available!";
		$response["posts"]   = array();
		
		foreach ($rows as $row) {
			$post             = array();
			$post["username"] = $row["userName"];
			$post["title"]    = $row["name"];
			$post["message"]  = $row["description"];
			$post["imgurl"]   = $row["Image"];
			$post["price"] 	  = $row["price"];
			$post["status"]   = $row["status"];
			$post["location"] = $row["location"];
			$post["city"] 	  = $row["city"];
			$post["phone"]    = $row["contact"];
			$post["post_id"]  = $row["pid"];
			
			//update our repsonse JSON data
			array_push($response["posts"], $post);
		}
		
		// echoing JSON response
		echo json_encode($response);
		
		
	} else {
		$response["success"] = 0;
		$response["message"] = "No Post Available!";
		die(json_encode($response));
	}
} else {
?>
	<h1>Login</h1> 
		<form action="getmydata.php" method="post"> 
		
		    Username:<br /> 
		    <input type="text" name="username" placeholder="username" /> 
		    <br /><br /> 
		    
		    <input type="submit" value="Login" /> 
		</form> 
<?php
}
?>
