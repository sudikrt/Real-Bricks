<?php

/*
Our "config.inc.php" file connects to database every time we include or require
it within a php script.  Since we want this script to add a new user to our db,
we will be talking with our database, and therefore,
let's require the connection to happen:
*/
require("config.inc.php");
	
	$login_ok = false;

	$response["success"] = 0;
	
	echo $username;
	$response["message"] = "Database Error!";
	die(json_encode($response));
if (!empty($_POST)) {
	
	
	// Finally, we can retrieve all of the found rows into an array using fetchAll 
}

?>
		<h1>Login</h1> 
		<form action="getUser.php" method="post"> 
		
		    Username:<br /> 
		    <input type="text" name="username" placeholder="username" /> 
		    <br /><br /> 
		   
		    <br /><br /> 
		    <input type="submit" value="Login" /> 
		</form> 
		
<?php


?> 