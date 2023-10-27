					Projet PO
					
Jules Cooper, Th�o Gu�rin

Prise en main :
	Voici quelques indications pour les contr�les et autres facilitant la prise en main du jeu. Pour les touches assign�es aux fonctions primaires on pourra regarder dans les contr�les de la page d�accueil.
Dans le menu principal on peut acc�der aux boutons � l�aide du clic, mais une fois dans une partie lanc� le menu pause ne peut �tre interagit qu�avec les fl�ches (haut et bas) sauf pour le son qui est � l�aide du clic.
Dans le menu Pause le bouton option ne fonctionne pas car nous n�avons pas eu le temps de l�implanter.

A la fin d�une partie, soit en mourant, ou en gagnant, il suffit d�appuyer sur la touche confirmer pour revenir au menu principal.
On peut aussi faire apparaitre une carte contenant les huit salles adjacentes � la courante en appuyant sur la touche �m�.

Touches de triches :
h -> soigne un point de vie
k -> tue tous les monstres d�une salle
p -> rend le joueur puissant
o -> donne 10 pi�ces d�or
l -> donne la super vitesse
j -> rend invincible


Explication de certain point int�ressant :
  - La g�n�ration des �tages :
	Un �tage est un tableau en deux dimensions de Room.
Pour g�n�rer les �tages on commence par mettre la salle de spawn, puis on rajoute autours des salles de monstres avec un certain pourcentage de ne rien placer puis � la fin on place la salle de boss en v�rifiant que la distance entre elle et la salle de spawn est bien sup�rieur ou �gal � 1.

  - Sauvegarde et charge de jeux : 
	Pour pouvoir sauvegarder et charger le jeu on a d� impl�menter la m�thode Serializable dans toutes les classes, cette m�thode permet de stocker des informations sur un tableau pouvant �tre d�coder pour recr�er une copie de l�objet. 
	On a rajout� deux fonctions saveGame et LoadGame dans une nouvelle librarie (Data) qui permette d��crire du texte (ici le tableau de bits correspondant � l�objet) sur un fichier, ou bien de lire le texte d�un fichier pour un extraire l�objet (ici la sauvegarde GameWorld) d�sir�.
 
  - D�placement du corps de Larry :
	Pour pouvoir d�placer le corps de Larry en suivant le mouvement de la t�te avec un d�lais, on a cr�� une liste de Vector2 contenant les positions du corps et de la t�te (exemple : {posT�te, posCorp1, �, posCorpX}), les positions sont actualis�es tous les dix cycle de jeux, avant d�actualis�e les positions dans la liste, le corps va prendre la position de la partie du corps d�avant, ou dans le cas du premier corps de la position de la t�te.
  - Attaque en forme de cercle :
	Pour cr�er une attaque en forme de cercle, on commence par placer un point sur le rayon, puis on effectue une rotation autour de la position de l�objet ayant cr��e la larme. La larme prendra en direction un vecteur �gal � la position initial moins la position de la larme, ce qui lui permet de s��loigner de l�objet.


Les am�liorations :
On a rajout� beaucoup de textures et animations pour certains objets.
On a aussi chang� la taille de la fen�tre pour avoir la place d'afficher l'inventaire et les informations (vie, bombes, argent et clef)
On a fait en sorte de pouvoir sauvegarder et charger la partie voire explication un peu plus haut. 	
Pour les musiques on a pris le code source de la page, https://www.geeksforgeeks.org/play-audio-file-using-java/, et on a modifier et supprimer les fonctions que l'on n'avait pas besoin. 	

Du c�t� technique les larmes peuvent se contrer entre elles si la larme tir�e et celle qui collisionne n�ont pas la m�me entit� qui l'a lanc�e.

On a associ� la touche F2 � la cr�ation d'une capture d'�cran de la fen�tre, cette capture d'�cran n'est disponible qu'une fois dans une partie.
On a rajout� une touche de triche �h� qui permet de r�cup�rer un point de vie au h�ros.
On a aussi mis une touche interaction utilis� pour ramasser et acheter un objet.
Avec la touche d�interaction, on a mis un inventaire qui permet de stocker jusqu�� deux objets, et qui sont compatible avec les objets passifs (lors ce qu�on jette un objet ses attributs disparaisse).
On a mis une console accessible en appuyant sur la touche t voir tout en bas pour voir les utilisations de la console.
On a aussi mis la possibilit� de modifier les contr�les des touches les plus utiles (pas la triche), qui sont ensuite enregistr� dans un fichier .txt.

On a aussi rajout� une carte accessible en appuyant sur la touche �m� par d�faut.


Modification des chiffres et fonctionnalit� : 
On a supprim� la fonctionnalit� d'acheter des c�urs au magasin
	
La touche pour pouvoir �tre invincible a �t� assign�e � la touche j, car i est d�j� utiliser par d�faut pour l'utilisation d'un item.
	
Modification des Librairies :
On a rajout� une fonction au stdDraw pour afficher une picture avec une couleur et une transparence au-dessus, cette fonction est picture(double x, double y, String filename, double scaledWidth, double scaledHeight,
			double degrees, Color color, int transparency).
	
Toujours dans StDraw, on a mis une fonction stop(), qui permet de fermer et d'arr�ter les fen�tres.
Toutes les class impl�mentes la m�thode Serializable pour permettre de sauvegarder et de charger les classes sur ou � partir d'un fichier .txt


Les monstres :
  - Araign�e :
L�araign�e se d�place en prenant un point destination au hasard et un nombre de d�placement, puis quand elle atteint la destination elle attend un certain nombre de temps, mais elle peut aussi continuer d�avancer tout de suite apr�s. 
  - Mouche :
La mouche bouge vers le joueur en lui tirant dessus, elle peut passer au-dessus des obstacles.
  - Double mouche :
	La double mouche bouge vers le joueur, sans tirer de larme, elle peut passer au-dessus des obstacles. Quand la double mouche est tu�e elle fait apparaitre deux mouche rouge simple qui fonctionne comme la mouche.
  - Clotty :
	Quand le joueur est assez proche de Clotty, celui-ci se d�place vers lui en tirant des larmes aux 4 points cardinaux.
  - Vis :
	Vis est un monstre qui attaque aux corps � corps, il ne fait que se d�placer vers le joueur pour le toucher.

Les boss :
  - Gurdy :
	Gurdy ne se d�place pas. Il attaque seulement � distance parmi deux possibilit�s. La premi�re une attaque en forme de cercle, et la deuxi�me une attaque en forme d��toile (dans les huit direction cardinal). *Voir plus haut pour l�explication de l�attaque en cercle*
  - Lamb :
	Lamb se d�place vers le joueur en attaquant � distance. Cette attaque a distance consiste en une larme unique tir�e vers le joueur quand Lamb � sa vie sup�rieur � la moiti� de sa vie initiale. Dans le cas ou sa vie est inf�rieur � la moiti� de sa vie initiale, alors il tirera un cercle de larme autour de lui.
  - Larry :
	Larry est une chenille, il se d�place donc comme une chenille, la t�te commence a boug� puis est suivie par le corps * voir explication de d�placement du corps de Larry plus haut*. En plus de ce d�placement unique il a la possibilit� de cr�er un caca � la fin de son corps. Ce caca peut �tre d�truit par le joueur en tirant dessus trois fois avec des larmes. La longueur diminue par pallier de vie perdue, et une fois la vie de Larry diminu� de moiti� celui-ci cr�er un autre Larry de la moiti� de sa taille, tout en diminuant sa taille de moiti�.
  - Loki :
	Ne bouge pas mais se t�l�porte de fa�on al�atoire dans la salle tout en tirant des larmes vers le joueur. Il fait aussi apparaitre entre une et trois mouches dans la Salle quand le nombre de mouche est plus petit ou �gal � deux.
  - Steven :
	Steven est un monstre en deux phase dans la premi�re il se d�place vers le joueur en lui tirant dessus, et dans la deuxi�me, il cr�er un autre monstre (Mini Steven) � sa position. Ce nouveau monstre Mini Steven se d�place rapidement vers le joueur, si le joueur r�ussi � toucher Mini Steven alors celui-ci s�arr�te un instant. Si c�est Mini Steven qui r�ussit � attaquer le joueur alors il recule et attend avant de r�attaquer.




Autres Notes : 
Le lag de la fen�tre ne vient pas de notre programme mais du StdDraw avec l'upscale de 2.

Les choix d�h�ritage, on a choisi de faire une super class objet pour tous les objets du jeu, elle est utile pour �viter la redondance des getters et setters � travers toutes les classes, elle permet aussi d�utiliser les m�thodes d�affichage, de collision et de savoir si la position est valide.
Cette super class global est ensuite divis� en deux cat�gories, les objets statiques et ceux mobile (nonStatic), les objets mobiles permettent d��viter les redondances relatives au mouvement, et impl�mente la m�thode d�animation ainsi que les m�thodes relatives au d�placement. La classe d�objet statique est utilis� pour bloquer le joueur ou lui faire des d�gat suivant la cat�gorie d�objet.

On a ensuite la classe de monstre qui est utilis� pour simplifi�e grandement la cr�ation de monstre, lors de la construction elle prend notamment en argument, la distance � partir de laquelle avancer vers le joueur, celle � partir de laquelle reculer. Elle permet m�me de construire un monstre ayant la capacit� de tir�. Toutes ses informations sont utilis�es dans la m�me updateGameObject(). La classe monstre apporte aussi les fonctions move(boolean) (avec le bool�en qui permet de sp�cifier si le monstre avance ou recule) et shoot() qui permet de tir� vers le joueur.

Console :
La console permet d�utiliser des commandes de triches en les utilisant des mots clef, voici la liste des mots clefs :
heal, killall, spawn <Monster> <coordoner_x> <coordonner_y>, set speed <value>, set health <value>
Pour le mot de clef spawn il requiert d�autre mot clef notamment le nom du monstre, les coordonner x et y ne sont pas obligatoire, par default le monstre appr�tera au coordonner du joueur. Voici un exemple d�utilisation de spawn : spawn fly 
Les arguments <coordonner_x>, <coordonner_y> de la commande spawn et l�argument <value> de la commande set speed doivent �tre indiquer en double entre 0 et 1 non compris. 
