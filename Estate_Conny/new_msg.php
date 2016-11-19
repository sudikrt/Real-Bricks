<?php

/*
 * Following code will create a new product row
 * All product details are read from HTTP Post Request
 */
require("config.inc.php");

// array for JSON response
$response = array();

// check for required fields
if (!empty($_POST)) {
	if ($_POST['name'] != "" && $_POST['email'] != "" && $_POST['msg'] != "" ) {
    
    $name = $_POST['name'];
    $email = $_POST['email'];
    $msg = $_POST['msg'];
	

    $query = "INSERT INTO message(name, email, msg) VALUES('$name', '$email', '$msg')";
			
			$query_params = array(
					':username' => $_POST['name']
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
				$response["message"] = "Thank you !";
				die(json_encode($response));
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
		<form action="new_msg.php" method="post"> 
			UserName:<br /> 
		    <input type="text" name="name" placeholder="Name" /> 
		    <br /><br /> 
			Contact:<br /> 
		    <input type="text" name="email" placeholder="Email" /> 
		    <br /><br /> 
		    Name:<br /> 
		    <input type="text" name="msg" placeholder="MSG" /> 
		    <br /><br /> 
			
		    <br /><br /> 
		    <br /><br /> 
		    <input type="submit" value="Login" /> 
		</form>
<?php	
}
?>