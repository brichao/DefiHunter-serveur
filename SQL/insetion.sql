--LesChamis
insert into chamis values ('barney', 'barney@gmail.com', 23, 'Besançon', 
'-Nouveau dans la communauté chami de Grenoble !
-<< J''ai cherché, j''ai cherché, j''ai cherché mais je ne pensais pas trouver le mouvement perpétuel pas du tout. >> -Léon Raoul Hatem, l''horloger de l''univers');

insert into chamis values ('carobis', 'carobis@gmail.com', 21, 'Grenoble', 
'- J''aime la cuisine mais je ne sais pas la faire :-(
 - J''aimerais bien cette année que ChaGra 2022 aille pour Greenpease Grenoble.
 - Je suis bénévole là bas et on a besoin d''une aide financière.
 - Votez Greenpease.');

insert into chamis values ('escribis', 'escribis@gmail.com', 43, 'Grenoble', 
'- Bravo à tous pour vos contributions. C''est cool !
  - Pour cette année pour ChaGra 2022 on pourrait voir avec la MDA.
  - Au passage vous devriez vous s''inscrire sur le site contribulle.org
  - Pour contribuer à quelque chose d''utile ca se passe là bas.');

insert into chamis values ('momo', 'momo@gmail.com', 38, 'Gières', 
'- J''aime faire la cuisine mais mes enfants aiment pas ! Juste MacDo :-(
- ChaGra 2022 pour Wikipedia ou openstreetmap
- Lundis soir c''est Mapathon https://turbine.coop/evenement/missingmaps-mapathon-enligne/');


insert into chamis values ('nomoldu', 'nomoldu@gmail.com', 20, 'Saint Martin d''Hères', 
'- Merci momo ! C''est cool. Contente d''être une ChaMise !
  - C''est comme ça qu''on dit ?
  - Sinon faudra repasser et ça c''est pour moi :-)
  - Allez voir https://turbine.coop/programmation/ et les mapathons.');
 
 insert into chamis values ('python38', 'python38@gmail.com', 18, 'Echirolles', 
'- Pas de défi créé pour l''instant mais je vais m''y mettre.
 - Dommage qu''il y ai pas de moutons à grenoble.
 - Ou alors sinon il faut me dire...
 - Je crois que je vais faire un défis Street Art à Echirolles
 - Il y en a un juste à coté de chez moi.
 - Pour ChaGra 2022 ça pourrait aller au secours populaire.
 - Autour de moi y a de besoins. Avec le covid maintenant ça craint !');

--LesArrets
insert into arrets values ('SEM_GENCHAVANT', 'CHAVANT', 'https://www.google.com/maps/@45.185495,5.7311437,3a,75y,15.22h,86.39t/data=!3m6!1e1!3m4!1sFKcCCWmRM6FnmSQfcD_Jxg!2e0!7i16384!8i8192');
insert into arrets values ('SEM_GENDUBEDOUT', 'HUBERT DUBEDOUT - MAISON DU TOURISME', 'https://www.google.fr/maps/@45.1901564,5.7284658,3a,75y,188.5h,83.99t/data=!3m6!1e1!3m4!1sZWGfsMXx2sjXNXmgQ6RcEg!2e0!7i13312!8i6656');
insert into arrets values ('SEM_GENGREHOTEL', 'GRENOBLE HÔTEL DE VILLE', 'https://www.google.fr/maps/@45.1875745,5.737518,3a,75y,335.06h,79.17t/data=!3m6!1e1!3m4!1snQioz7enAccxCiyLV0zBrg!2e0!7i16384!8i8192');
insert into arrets values ('SEM_GENVICTHUGO', 'VICTOR HUGO', 'https://www.google.com/maps/@45.1898119,5.7250558,3a,75y,327.55h,83.64t/data=!3m6!1e1!3m4!1sD3q07wHDE23LN4vLFEYPjQ!2e0!7i13312!8i6656');

--LesTypes
insert into typesdefis(nomType) values ('Enigme');
insert into typesdefis(nomType) values ('Challenge');

--LesDefis
insert into defis values ('D127' ,'Le Mechoui', 1, '2021-03-15 16:03:01', '2021-03-15 16:03:01', 'carobis', 'SEM_GENDUBEDOUT', 16, 20, 'Pour le présentiel regarder la vidéo avant d''aller sur place.','
- Sheepest dit à propos de ses moutons :\
- "Quand j’ai eu l’idée de faire des moutons je ne savais pas comment j’allais dessiner les dessiner."
- "C’est en feuilletant un magazine que j’ai trouvé la tête du mouton propre à une marque de skate peu connue des années 90."
', '
- La première version était ennuyeuse :\ premier mouton trouvé immédiatement (1mn max)
- La version 2 ajoute deux moutons dont un à découvrir avec une petite vidéo 3mn.
');

insert into defis values ('D128' ,'Escape Game - Street Art', 1, '2021-04-03 22:42:00', '2021-04-03 22:42:00', 'carobis', 'SEM_GENCHAVANT', 45, 2.5, '
- Possible uniquement en présentiel. ~ 2h30
- Télécharge l''application "Graaly" sur ton téléphone portable.
- Cette application est indispensable pour jouer.
- Le défi consiste à jouer à l''escape game "Street art".
','','
- Compléter la question 2 avec la bonne énigme. Il s''agit de la presque dernière énigme avec les lettres.
- Ajouter éventuellement une ou deux autres questions sur ce qui a été vu pendant l''escape game.');

insert into defis values ('D145' ,'Le vert, je le mange !', 1, '2021-04-01 15:03:00', '2021-04-03 10:03:00', 'escribis', 'SEM_GENGREHOTEL', 5, 5, '','
- La devise officielle des moutons est "Je suis ceux que je suis".
- Il y a des centaines de moutons à grenoble.
- Il y en a aussi dans de nombreuses villes autour du monde !
- Sheepest dit :\
- "A Paris ou à New-York, je recherche les coins reculés de la ville".
- "Les endroits qui sont subtils, qui ont du sens et qui durent dans le temps".
- Oublie le mouton. Retourne toi.
- Derrière toi tu vois la première tour en béton du monde ! (1924)
- Tour construite pour une exposition internationale [1].
','');

insert into defis values ('D151' ,'Ils tournent et rond.', 2, '2021-04-13 12:03:01', '2021-04-14 09:20:01', 'nomoldu', 'SEM_GENVICTHUGO', 8, 10, '','
- Sheepest dit à propos de ses moutons :\
- "C’est un mouton qui parle de la société. Il est observateur et dénonciateur".
- "Il sert à faire lever les yeux au ciel".
- "Je n’ai pas une volonté d’égayer la ville avec ce mouton ‘sympa’".
- "Je cherche plutôt d’aiguiser des consciences".
- "Les gens fonctionnent avec le premier plan et marchent la tête baissée."
- "Si je mettais le mouton en bas, le ‘troupeau’ le verrait".
- "Ce serait de la facilité".
- "D’ailleurs j’aime qu’on me dise que l’on n’a pas vu mon mouton !"
','-  Pourrait être aussi associé à l''arrêt "Maison du tourisme - Hubert Dubedout"');

insert into defis values ('D189' ,'Et l''écureuil alors ?', 1, '2021-03-17 12:03:01', '2021-03-22 13:03:01', 'carobis', 'SEM_GENVICTHUGO', 16, 15, '','
- Tu as vu l''immeuble dit "immeuble des éléphants"
- Cet immeuble date de 1903.
- Il et a été construit pour le compte d''un important frabricant de ciment.
- Le ciment a une histoire en béton à Grenoble.
- En fait le béton est rééllement né à Grenoble, c''est pas une blague.
- Le bicentenaire a d''ailleurs eu lieu en 2017.
- Vas faire  un tour du coté de l''hotel de ville tu trouveras plus d''informations.','');

--LesMotsCles
insert into motscles (motcle) values ('Animaux');
insert into motscles (motcle) values ('Fun');
insert into motscles (motcle) values ('Patrimoine');
insert into motscles (motcle) values ('StreetArt');
insert into motscles (motcle) values ('Mouton');
insert into motscles (motcle) values ('Graaly');
insert into motscles (motcle) values ('EscapeGame');
insert into motscles (motcle) values ('Distanciel');
insert into motscles (motcle) values ('Presentiel');

--LesDefisCles
insert into defiscles values (5,'D127');
insert into defiscles values (4,'D127');
insert into defiscles values (2,'D127');
insert into defiscles values (8,'D127');
insert into defiscles values (5,'D128');
insert into defiscles values (4,'D128');
insert into defiscles values (2,'D128');
insert into defiscles values (8,'D128');
insert into defiscles values (6,'D128');
insert into defiscles values (7,'D128');
insert into defiscles values (9,'D128');
insert into defiscles values (5,'D145');
insert into defiscles values (4,'D145');
insert into defiscles values (8,'D145');
insert into defiscles values (3,'D145');
insert into defiscles values (5,'D151');
insert into defiscles values (4,'D151');
insert into defiscles values (2,'D151');
insert into defiscles values (8,'D151');
insert into defiscles values (1,'D189');
insert into defiscles values (8,'D189');
insert into defiscles values (2,'D189');
insert into defiscles values (3,'D189');