# Burger Quiz - Partie web en PHP  

Partie web du projet permettant de jouer au jeu en répondant aux multiples questions.
Les meilleures scores dans les différentes catégories seront également affichés dans une section dédiée.

## Interface

* [Twitter Bootstrap](http://twitter.github.com/bootstrap/)
* [jQuery](http://jquery.com/)
* [Backbone.js](http://documentcloud.github.com/backbone/)

## Backend

* [CodeIgniter](http://codeigniter.com/)

## Configuration

### Globale

Modifiez le fichier _./application/config/config.php_.  
Le fichier contient une variable **$config['base_url']** que vous devez modifier pour pointer vers la racine de l'application.  

Votre serveur Apache doit avoir le **mod_rewrite** activé et la configuration du _VirtualHost_ doit avoir la directive **AllowOverride** configurée à **All** afin que le fichier _.htaccess_ soit pris en compte par Apache.  

Si d'aventure ce n'est pas le cas et que vous êtes dans l'incapacité de modifier la configuration du serveur, mettez la variable **$config['index_page']** à _"index.php"_.

### Base de données

La configuration de la base de données se situe dans le fichier _./application/config/database.php_.  
Modifiez les variables de configuration pour qu'elles correspondent à votre base de données.

## Bootstrap

Le script ne crée pas automatiquement la base de données, vous devez le faire par vous même avec l'interclassement **utf8_general_ci**.  

Pour charger la structure et les données initiales de la base de données, importez le fichier _./scripts/burgerquiz.sql_ dans votre base de données.
