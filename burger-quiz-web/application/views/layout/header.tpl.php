<!DOCTYPE html>
<html lang="fr">
<head>
  <title>Burger Quiz <?= strlen($title) > 0 ? " - $title" : "" ?></title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta name="description" content="Jeu du Burger Quiz">
  <meta name="author" content="Nathan Le Ray, Jason Hecamps">

  <!-- Le Styles -->
  <link rel="stylesheet" type="text/css" 
        href="<?= base_url(); ?>assets/stylesheets/vendor/bootstrap.min.css">
  <link rel="stylesheet" type="text/css" 
        href="<?= base_url(); ?>assets/stylesheets/vendor/bootstrap-responsive.min.css">
  <link rel="stylesheet" type="text/css" 
        href="<?= base_url(); ?>assets/stylesheets/style.css">
  <link rel="stylesheet" type="text/css" 
        href="<?= base_url(); ?>assets/stylesheets/mobile.css">
  
  <!-- Le Javascripts -->
  <script type="text/javascript" 
          src="<?= base_url(); ?>assets/javascripts/vendor/jquery.min.js">
  </script>
  <script type="text/javascript" 
          src="<?= base_url(); ?>assets/javascripts/vendor/underscore.min.js">
  </script>
  <script type="text/javascript" 
          src="<?= base_url(); ?>assets/javascripts/vendor/backbone.min.js">
  </script>
  <script type="text/javascript" 
          src="<?= base_url(); ?>assets/javascripts/vendor/bootstrap.min.js">
  </script>
  <script type="text/javascript" 
          src="<?= base_url(); ?>assets/javascripts/vendor/jquery.cookie.js">
  </script>
  <script type="text/javascript" 
          src="<?= base_url(); ?>assets/javascripts/app.js">
  </script>
  
  <!-- Le Favicon and stuff -->
  <link rel="shortcut icon" href="<?= base_url(); ?>assets/ico/favicon.ico">
  <link rel="apple-touch-icon-precomposed" 
        sizes="144x144" 
        href="<?= base_url(); ?>assets/ico/apple-touch-icon-144-precomposed.png">
  <link rel="apple-touch-icon-precomposed" 
        sizes="114x114" 
        href="<?= base_url(); ?>assets/ico/apple-touch-icon-114-precomposed.png">
  <link rel="apple-touch-icon-precomposed" 
        sizes="72x72" 
        href="<?= base_url(); ?>assets/ico/apple-touch-icon-72-precomposed.png">
  <link rel="apple-touch-icon-precomposed" 
        href="<?= base_url(); ?>assets/ico/apple-touch-icon-57-precomposed.png">
  
</head>
<body>
  <div class="navbar navbar-fixed-top">
    <div class="navbar-inner">
      <div class="container">
        <a class="btn btn-navbar" 
           data-toggle="collapse" 
           data-target=".nav-collapse">
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
        </a>

        <a class="brand" href="<?= base_url(); ?>">Burger Quiz</a>

        <div class="nav-collapse">
          <ul class="nav">
            <li><a href="<?= base_url(); ?>" id="nav-home">Accueil</a></li>
            <li>
              <a href="<?= base_url(); ?>app/scores" id="nav-scores">Tableau des scores</a>
            </li>
            <li class="divider-vertical"></li>
            <li><a href="#" id="nav-user"></a></li>
            <li>
              <a href="<?= base_url(); ?>users/logout" 
                 id="nav-logout" style="display:none">Déconnexion</a>
            </li>
          </ul>
        </div>
      </div>
    </div>
  </div>
  <div id="app" class="container">
    <div id="loader" style="display: none;"><img src="<?= base_url(); ?>assets/images/icons/loader.gif" alt="Chargement..." /><br /></div>
    <script type="text/javascript">
    $(document).ready(function() {
      // Application Base URL (Used in Order to Fetch Data)
      App.baseURI = '<?= base_url(); ?>';
      
      // Starting App
      new App.start();
      // Starting History with PushState Support Enabled (Oh Yeah!)
      Backbone.history.start({pushState: true, root: "/burger-quiz-web/"});
    });
    </script>
    <section id="stage">