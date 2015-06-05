# Burger Quiz

## Partie applicative en Java

Bla bla bla...

## Lancement par défaut

Vous pouvez lancer l'application avec la configuration d'accès à la base de données avec la commande suivante : ```java -jar BurgerQuizAdmin.jar```.  

## Compilation

Pour compiler le projet, placez vous dans le répertoire _./src_.  
Avant de compiler, modifiez votre variable d'environnement **$CLASSPATH** à l'aide de la commande suivante : ```export CLASSPATH=/home/oyokoon/Dropbox/Projects/burger-quiz-app/lib/mysql-connector-java-5.1.20-bin.jar:.```.  
Tapez ensuite ```javac BurgerQuizAdmin.java``` pour compiler puis ```java BurgerQuizAdmin``` pour lancer le logiciel.

## Configuration de la base de données

Toute la configuration de la base de données se situe dans le fichier _./src/business/dao/DatabaseConnection.java_.

La configuration par défaut étant :

* __url__ : jdbc:mysql://localhost:3306/burgerquiz
* __user__ : burgerquiz
* __password__ : burgerquiz

## Bootstrap

Le script ne crée pas automatiquement la base de données, vous devez le faire par vous même avec l'interclassement **utf8_general_ci**.  

Pour charger la structure et les données initiales de la base de données, importez le fichier _./scripts/burgerquiz.sql_ dans votre base de données.