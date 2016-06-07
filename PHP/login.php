<?php 
session_start();
$pdo = new PDO('mysql:host=localhost;dbname=test', 'username', 'pw');
 
if(isset($_GET['login'])) {
	$email = $_POST['email'];
	$passwort = $_POST['passwort'];
	
	$statement = $pdo->prepare("SELECT * FROM users WHERE email = :email");
	$result = $statement->execute(array('email' => $email));
	$user = $statement->fetch();
		
	//Überprüfung des Passworts
	if ($user !== false && password_verify($passwort, $user['passwort'])) {
		$_SESSION['userid'] = $user['id'];
		header("Location: weiter.php");
		exit; 
		die('Login erfolgreich. Weiter zu <a href="geheim.php">internen Bereich</a>');

	} else {
		echo "<div class='alert alert-danger'>
		<strong>Fehler</strong> Email oder Passwort falsch.
		</div>";
		
	}
	
}
?>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="../../favicon.ico">

    <title>Login</title>

    <!-- Bootstrap core CSS -->
    <link href="boot/css/bootstrap.min.css" rel="stylesheet">

    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <link href="boot/assets/css/ie10-viewport-bug-workaround.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="signin.css" rel="stylesheet">

    <!-- Just for debugging purposes. Don't actually copy these 2 lines! -->
    <!--[if lt IE 9]><script src="../../assets/js/ie8-responsive-file-warning.js"></script><![endif]-->
    <script src="boot/assets/js/ie-emulation-modes-warning.js"></script>

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>

  <body>
<?php 
if(isset($errorMessage)) {
	echo $errorMessage;
}
?>
    <div class="container">

      <form action="?login=1" method="post" class="form-signin">
        <h2 class="form-signin-heading">Bitte einloggen</h2>
        <label for="inputEmail" class="sr-only">Email adresse</label>
        <input type="email" name="email" id="inputEmail" class="form-control" placeholder="Email Adresse" required autofocus>
        <label for="inputPassword" class="sr-only">Passwort</label>
        <input type="password" name="passwort" id="inputPassword" class="form-control" placeholder="Password" required>
        <button class="btn btn-lg btn-primary btn-block" type="submit">anmelden</button>
      </form>

    </div> <!-- /container -->


    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <script src="boot/assets/js/ie10-viewport-bug-workaround.js"></script>
  </body>
</html>