<?php

  require "vendor/autoload.php";

  if (!empty($_SERVER['HTTPS']) && ('on' == $_SERVER['HTTPS'])) {
		$uri = 'https://';
	} else {
		$uri = 'http://';
	}
  define("HOME", $uri . $_SERVER['HTTP_HOST']);

?>
