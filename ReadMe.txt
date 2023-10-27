					Projet PO
					
Jules Cooper, Théo Guérin

Prise en main :
	Voici quelques indications pour les contrôles et autres facilitant la prise en main du jeu. Pour les touches assignées aux fonctions primaires on pourra regarder dans les contrôles de la page d’accueil.
Dans le menu principal on peut accéder aux boutons à l’aide du clic, mais une fois dans une partie lancé le menu pause ne peut être interagit qu’avec les flèches (haut et bas) sauf pour le son qui est à l’aide du clic.
Dans le menu Pause le bouton option ne fonctionne pas car nous n’avons pas eu le temps de l’implanter.

A la fin d’une partie, soit en mourant, ou en gagnant, il suffit d’appuyer sur la touche confirmer pour revenir au menu principal.
On peut aussi faire apparaitre une carte contenant les huit salles adjacentes à la courante en appuyant sur la touche ‘m’.

Touches de triches :
h -> soigne un point de vie
k -> tue tous les monstres d’une salle
p -> rend le joueur puissant
o -> donne 10 pièces d’or
l -> donne la super vitesse
j -> rend invincible


Explication de certain point intéressant :
  - La génération des étages :
	Un étage est un tableau en deux dimensions de Room.
Pour générer les étages on commence par mettre la salle de spawn, puis on rajoute autours des salles de monstres avec un certain pourcentage de ne rien placer puis à la fin on place la salle de boss en vérifiant que la distance entre elle et la salle de spawn est bien supérieur ou égal à 1.

  - Sauvegarde et charge de jeux : 
	Pour pouvoir sauvegarder et charger le jeu on a dû implémenter la méthode Serializable dans toutes les classes, cette méthode permet de stocker des informations sur un tableau pouvant être décoder pour recréer une copie de l’objet. 
	On a rajouté deux fonctions saveGame et LoadGame dans une nouvelle librarie (Data) qui permette d’écrire du texte (ici le tableau de bits correspondant à l’objet) sur un fichier, ou bien de lire le texte d’un fichier pour un extraire l’objet (ici la sauvegarde GameWorld) désiré.
 
  - Déplacement du corps de Larry :
	Pour pouvoir déplacer le corps de Larry en suivant le mouvement de la tête avec un délais, on a créé une liste de Vector2 contenant les positions du corps et de la tête (exemple : {posTête, posCorp1, …, posCorpX}), les positions sont actualisées tous les dix cycle de jeux, avant d’actualisée les positions dans la liste, le corps va prendre la position de la partie du corps d’avant, ou dans le cas du premier corps de la position de la tête.
  - Attaque en forme de cercle :
	Pour créer une attaque en forme de cercle, on commence par placer un point sur le rayon, puis on effectue une rotation autour de la position de l’objet ayant créée la larme. La larme prendra en direction un vecteur égal à la position initial moins la position de la larme, ce qui lui permet de s’éloigner de l’objet.


Les améliorations :
On a rajouté beaucoup de textures et animations pour certains objets.
On a aussi changé la taille de la fenêtre pour avoir la place d'afficher l'inventaire et les informations (vie, bombes, argent et clef)
On a fait en sorte de pouvoir sauvegarder et charger la partie voire explication un peu plus haut. 	
Pour les musiques on a pris le code source de la page, https://www.geeksforgeeks.org/play-audio-file-using-java/, et on a modifier et supprimer les fonctions que l'on n'avait pas besoin. 	

Du côté technique les larmes peuvent se contrer entre elles si la larme tirée et celle qui collisionne n’ont pas la même entité qui l'a lancée.

On a associé la touche F2 à la création d'une capture d'écran de la fenêtre, cette capture d'écran n'est disponible qu'une fois dans une partie.
On a rajouté une touche de triche ‘h’ qui permet de récupérer un point de vie au héros.
On a aussi mis une touche interaction utilisé pour ramasser et acheter un objet.
Avec la touche d’interaction, on a mis un inventaire qui permet de stocker jusqu’à deux objets, et qui sont compatible avec les objets passifs (lors ce qu’on jette un objet ses attributs disparaisse).
On a mis une console accessible en appuyant sur la touche t voir tout en bas pour voir les utilisations de la console.
On a aussi mis la possibilité de modifier les contrôles des touches les plus utiles (pas la triche), qui sont ensuite enregistré dans un fichier .txt.

On a aussi rajouté une carte accessible en appuyant sur la touche ‘m’ par défaut.


Modification des chiffres et fonctionnalité : 
On a supprimé la fonctionnalité d'acheter des cœurs au magasin
	
La touche pour pouvoir être invincible a été assignée à la touche j, car i est déjà utiliser par défaut pour l'utilisation d'un item.
	
Modification des Librairies :
On a rajouté une fonction au stdDraw pour afficher une picture avec une couleur et une transparence au-dessus, cette fonction est picture(double x, double y, String filename, double scaledWidth, double scaledHeight,
			double degrees, Color color, int transparency).
	
Toujours dans StDraw, on a mis une fonction stop(), qui permet de fermer et d'arrêter les fenêtres.
Toutes les class implémentes la méthode Serializable pour permettre de sauvegarder et de charger les classes sur ou à partir d'un fichier .txt


Les monstres :
  - Araignée :
L’araignée se déplace en prenant un point destination au hasard et un nombre de déplacement, puis quand elle atteint la destination elle attend un certain nombre de temps, mais elle peut aussi continuer d’avancer tout de suite après. 
  - Mouche :
La mouche bouge vers le joueur en lui tirant dessus, elle peut passer au-dessus des obstacles.
  - Double mouche :
	La double mouche bouge vers le joueur, sans tirer de larme, elle peut passer au-dessus des obstacles. Quand la double mouche est tuée elle fait apparaitre deux mouche rouge simple qui fonctionne comme la mouche.
  - Clotty :
	Quand le joueur est assez proche de Clotty, celui-ci se déplace vers lui en tirant des larmes aux 4 points cardinaux.
  - Vis :
	Vis est un monstre qui attaque aux corps à corps, il ne fait que se déplacer vers le joueur pour le toucher.

Les boss :
  - Gurdy :
	Gurdy ne se déplace pas. Il attaque seulement à distance parmi deux possibilités. La première une attaque en forme de cercle, et la deuxième une attaque en forme d’étoile (dans les huit direction cardinal). *Voir plus haut pour l’explication de l’attaque en cercle*
  - Lamb :
	Lamb se déplace vers le joueur en attaquant à distance. Cette attaque a distance consiste en une larme unique tirée vers le joueur quand Lamb à sa vie supérieur à la moitié de sa vie initiale. Dans le cas ou sa vie est inférieur à la moitié de sa vie initiale, alors il tirera un cercle de larme autour de lui.
  - Larry :
	Larry est une chenille, il se déplace donc comme une chenille, la tête commence a bougé puis est suivie par le corps * voir explication de déplacement du corps de Larry plus haut*. En plus de ce déplacement unique il a la possibilité de créer un caca à la fin de son corps. Ce caca peut être détruit par le joueur en tirant dessus trois fois avec des larmes. La longueur diminue par pallier de vie perdue, et une fois la vie de Larry diminué de moitié celui-ci créer un autre Larry de la moitié de sa taille, tout en diminuant sa taille de moitié.
  - Loki :
	Ne bouge pas mais se téléporte de façon aléatoire dans la salle tout en tirant des larmes vers le joueur. Il fait aussi apparaitre entre une et trois mouches dans la Salle quand le nombre de mouche est plus petit ou égal à deux.
  - Steven :
	Steven est un monstre en deux phase dans la première il se déplace vers le joueur en lui tirant dessus, et dans la deuxième, il créer un autre monstre (Mini Steven) à sa position. Ce nouveau monstre Mini Steven se déplace rapidement vers le joueur, si le joueur réussi à toucher Mini Steven alors celui-ci s’arrête un instant. Si c’est Mini Steven qui réussit à attaquer le joueur alors il recule et attend avant de réattaquer.




Autres Notes : 
Le lag de la fenêtre ne vient pas de notre programme mais du StdDraw avec l'upscale de 2.

Les choix d’héritage, on a choisi de faire une super class objet pour tous les objets du jeu, elle est utile pour éviter la redondance des getters et setters à travers toutes les classes, elle permet aussi d’utiliser les méthodes d’affichage, de collision et de savoir si la position est valide.
Cette super class global est ensuite divisé en deux catégories, les objets statiques et ceux mobile (nonStatic), les objets mobiles permettent d’éviter les redondances relatives au mouvement, et implémente la méthode d’animation ainsi que les méthodes relatives au déplacement. La classe d’objet statique est utilisé pour bloquer le joueur ou lui faire des dégat suivant la catégorie d’objet.

On a ensuite la classe de monstre qui est utilisé pour simplifiée grandement la création de monstre, lors de la construction elle prend notamment en argument, la distance à partir de laquelle avancer vers le joueur, celle à partir de laquelle reculer. Elle permet même de construire un monstre ayant la capacité de tiré. Toutes ses informations sont utilisées dans la même updateGameObject(). La classe monstre apporte aussi les fonctions move(boolean) (avec le booléen qui permet de spécifier si le monstre avance ou recule) et shoot() qui permet de tiré vers le joueur.

Console :
La console permet d’utiliser des commandes de triches en les utilisant des mots clef, voici la liste des mots clefs :
heal, killall, spawn <Monster> <coordoner_x> <coordonner_y>, set speed <value>, set health <value>
Pour le mot de clef spawn il requiert d’autre mot clef notamment le nom du monstre, les coordonner x et y ne sont pas obligatoire, par default le monstre apprêtera au coordonner du joueur. Voici un exemple d’utilisation de spawn : spawn fly 
Les arguments <coordonner_x>, <coordonner_y> de la commande spawn et l’argument <value> de la commande set speed doivent être indiquer en double entre 0 et 1 non compris. 
