# Partie back : API pour le projet de programmation Web

Ce repository supporte le code de notre API RESTful qui est appelée par notre front en Angular. Elle interprète les requêtes HTTP et fait passer les données par le format JSON. Elle est également interfacée avec une base de données pour permettre la persistance des données.


## Technologies utilisées

### SpringBoot

![SpringBoot logo](https://atomrace.com/blog/wp-content/uploads/2018/05/spring-boot-logo.png)

SpringBoot est un framework basé sur Java pouvant être utilisé pour créer des applications Spring sous forme de microservice et/ou des API RESTful dans le but d'exposer des données.

### MySQL
![enter image description here](https://cdn.discordapp.com/attachments/648455329016709121/789558919387152404/mysql_logo.png)
MySQL est la base de données que nous avons utilisé. MySQL désigne en réalité un SGBD relationnel complet, ce qui nous a permis de l'interfacer facilement avec SpringBoot pour permettre de sauvegarder nos objets.

## Prérequis de fonctionnement
Pour faire fonctionner notre API il y a quelques prérequis, notamment vis-à-vis de la configuration de MySQL :
- Ajout d'une base nommée "**sondagesdb**";
- Ajout d'un utilisateur "**sondageUser**" avec son mot de passe "**sondageUser**"
- Ajout de tous les droits sur la base "**sondagesdb**" pour l'utilisateur "**sondageUser**" ou ajouter tous les privilèges si cela ne suffit pas

Le 
