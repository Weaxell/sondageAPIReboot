
# Partie back : API pour le projet de programmation Web

Ce repository supporte le code de notre API RESTful qui est appelée par notre front en Angular. Elle interprète les requêtes HTTP et fait passer les données par le format JSON. Elle est également interfacée avec une base de données pour permettre la persistance des données.
Ce n'est pas avec cette API que nous gérons les sessions, tokens et comptes utilisateurs. La partie front de notre projet est connecté à un autre service d'authentification qui endosse ce rôle, son fonctionnement est détaillé sur la documentation de notre front : https://github.com/palpatoune/4A-Prog-Web.

## Description rapide de la structure des données
Les données transitant dans l'API sont en grande majorité :
### Sondages
Un sondage possède un titre, et une liste de choix possibles sous la forme d'une liste de chaîne de caractères.
Il peut également être de deux types : public ou privé, c'est à dire assigné à une salle privée.
### Salle de sondages
Les utilisateurs peuvent créer une salle de sondage privée, où seuls les utilisateurs ajoutés à cette salle pourront visualiser et voter sur les sondages créés dans cette même salle. Une salle est donc caractérisée principalement par un titre, une liste des identifiants utilisateur ayant accès à cette salle, ainsi qu'une liste des identifiants des sondages créés dans cette salle.
### Vote
Le vote est l'objet qui est généré lorsqu'un utilisateur vote sur un sondage. Il est donc représenté par l'identifiant du sondage sur lequel porte ce vote, l'identifiant de l'utilisateur qui a effectué ce vote, ainsi qu'une chaîne de caractères correspondant à l'option choisie par l'utilisateur lors de son vote parmi les réponses possibles dans le sondage.

## Technologies utilisées

### SpringBoot

![SpringBoot logo](https://atomrace.com/blog/wp-content/uploads/2018/05/spring-boot-logo.png)

SpringBoot est un framework basé sur Java pouvant être utilisé pour créer des applications Spring sous forme de microservice et/ou des API RESTful dans le but d'exposer des données.

### MySQL
![enter image description here](https://cdn.discordapp.com/attachments/648455329016709121/789558919387152404/mysql_logo.png)

MySQL est la base de données que nous avons utilisé. MySQL désigne en réalité un SGBD relationnel complet, ce qui nous a permis de l'interfacer facilement avec SpringBoot pour permettre de sauvegarder nos objets.

## Prérequis de fonctionnement
Pour faire fonctionner notre API il y a quelques prérequis, notamment vis-à-vis de la configuration de MySQL :
- Créer une instance de MySQL sur localhost (sous Windows avec WSL dans un sous-système Ubuntu par exemple)
- Ajout d'une base nommée "**sondagesdb**";
- Ajout d'un utilisateur "**sondageUser**" avec son mot de passe "**sondageUser**"
- Ajout de tous les droits sur la base "**sondagesdb**" pour l'utilisateur "**sondageUser**" ou ajouter tous les privilèges globaux si cela ne suffit pas
SpringBoot ne requiert pas de prérequis particulier, Maven s'occupera d'installer les dépendances nécessaires au fonctionnement de l'API.

## Documentation de l'API
Voici une documentation du mapping de notre API réalisée avec Swagger2 :
- Le contrôleur d'objet SalleSondage :

![enter image description here](https://cdn.discordapp.com/attachments/648455329016709121/789598093989249064/unknown.png)

- Le contrôleur d'objet Sondage :

![enter image description here](https://cdn.discordapp.com/attachments/648455329016709121/789598625105313852/unknown.png)

- Le contrôleur d'objet Vote :

![enter image description here](https://cdn.discordapp.com/attachments/648455329016709121/789598826049830922/unknown.png)

NB final : ce repository est la suite de celui-ci : https://github.com/Weaxell/sondageAPI
L'architecture de mon projet était cassée, j'ai préféré repartir d'un projet neuf en copiant les fichiers sources de l'ancien projet.
