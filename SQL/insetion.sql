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

--LesDefis
insert into defis values ('D127' ,'Le Mechoui', 'Enigme', '2021-03-15 16:03:01', '2021-03-15 16:03:01', 'carobis', 'SEM_GENDUBEDOUT', 16, 20, 'Pour le présentiel regarder la vidéo avant d''aller sur place.','
- Sheepest dit à propos de ses moutons :\
- "Quand j’ai eu l’idée de faire des moutons je ne savais pas comment j’allais dessiner les dessiner."
- "C’est en feuilletant un magazine que j’ai trouvé la tête du mouton propre à une marque de skate peu connue des années 90."
', '
- La première version était ennuyeuse :\ premier mouton trouvé immédiatement (1mn max)
- La version 2 ajoute deux moutons dont un à découvrir avec une petite vidéo 3mn.
');

insert into defis values ('D128' ,'Escape Game - Street Art', 'Enigme', '2021-04-03 22:42:00', '2021-04-03 22:42:00', 'carobis', 'SEM_GENCHAVANT', 45, 2.5, '
- Possible uniquement en présentiel. ~ 2h30
- Télécharge l''application "Graaly" sur ton téléphone portable.
- Cette application est indispensable pour jouer.
- Le défi consiste à jouer à l''escape game "Street art".
','','
- Compléter la question 2 avec la bonne énigme. Il s''agit de la presque dernière énigme avec les lettres.
- Ajouter éventuellement une ou deux autres questions sur ce qui a été vu pendant l''escape game.');

insert into defis values ('D145' ,'Le vert, je le mange !', 'Enigme', '2021-04-01 15:03:00', '2021-04-03 10:03:00', 'escribis', 'SEM_GENGREHOTEL', 5, 5, '','
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

insert into defis values ('D151' ,'Ils tournent et rond.', 'Challenge', '2021-04-13 12:03:01', '2021-04-14 09:20:01', 'nomoldu', 'SEM_GENVICTHUGO', 8, 10, '','
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

insert into defis values ('D189' ,'Et l''écureuil alors ?', 'Enigme', '2021-03-17 12:03:01', '2021-03-22 13:03:01', 'carobis', 'SEM_GENVICTHUGO', 16, 15, '','
- Tu as vu l''immeuble dit "immeuble des éléphants"
- Cet immeuble date de 1903.
- Il et a été construit pour le compte d''un important frabricant de ciment.
- Le ciment a une histoire en béton à Grenoble.
- En fait le béton est rééllement né à Grenoble, c''est pas une blague.
- Le bicentenaire a d''ailleurs eu lieu en 2017.
- Vas faire  un tour du coté de l''hotel de ville tu trouveras plus d''informations.','');

--LesMotsCles
insert into motscles values ('D127','Mouton');
insert into motscles values ('D127','StreetArt');
insert into motscles values ('D127','Fun');
insert into motscles values ('D127','Distanciel');
insert into motscles values ('D128','Mouton');
insert into motscles values ('D128','StreetArt');
insert into motscles values ('D128','Fun');
insert into motscles values ('D128','Distanciel');
insert into motscles values ('D128','Graaly');
insert into motscles values ('D128','EscapeGame');
insert into motscles values ('D128','Presentiel');
insert into motscles values ('D145','Mouton');
insert into motscles values ('D145','StreetArt');
insert into motscles values ('D145','Distanciel');
insert into motscles values ('D145','Patrimoine');
insert into motscles values ('D151','Mouton');
insert into motscles values ('D151','StreetArt');
insert into motscles values ('D151','Fun');
insert into motscles values ('D151','Distanciel');
insert into motscles values ('D189','Animaux');
insert into motscles values ('D189','Distanciel');
insert into motscles values ('D189','Fun');
insert into motscles values ('D189','Patrimoine');

--LesIndices
insert into indices (defisid, indicenum , description , points ) values ('D127', 1, '- Détourne toi des bonbons.
- Ce n''est pas bon pour les dents.', 1);
insert into indices (defisid, indicenum , description , points ) values ('D127', 2, '- Regarde dans la direction du bureau de tabac.
- Regarde en hauteur... Si tu ne le vois pas tu peux t''avancer dans cette direction.', 3);
insert into indices (defisid, indicenum , description , points ) values ('D127', 3, '- En fait il est environ à 25m du premier mouton à vol d''oiseaux.
- Mais si tu veux le voir il faudra prendre du recul...', 3);
insert into indices (defisid, indicenum , description , points ) values ('D127', 4, '- Place toi entre l''angle de monoprix et la maison du tourisme.', 3);
insert into indices (defisid, indicenum , description , points ) values ('D145', 1, '- Vas du coté de Belledonne.', 0);
insert into indices (defisid, indicenum , description , points ) values ('D145', 2, 'Grenoble est un ville verte. Donc avec des voitures vertes.', 2);
insert into indices (defisid, indicenum , description , points ) values ('D145', 3, 'A l''angle de l''orangerie, regarde l''autre angle.', 2);

--LesQuestions
insert into questions (defisid, questionnum , description , points, secret) values ('D127', 1, 'Quel numéro ?', 1, '26');
insert into questions (defisid, questionnum , description , points, secret) values ('D127', 2, 'Quelle est la couleur qui est juste sous le museau du mouton ?', 4, 'blanc');
insert into questions (defisid, questionnum , description , points, secret) values ('D127', 3, 'Quel est l''animal qui remplace le mouton ?', 7, 'dauphin');
insert into questions (defisid, questionnum , description , points, secret) values ('D127', 4, '', 4, '');
insert into questions (defisid, questionnum , description , points, secret) values ('D128', 1, '- Qu''a le renard entre les deux yeux ?', 3, '');
insert into questions (defisid, questionnum , description , points, secret) values ('D128', 2, '- Qu''y a t il à coté du troupeau de mouton ?', 2, '');
insert into questions (defisid, questionnum , description , points, secret) values ('D128', 3, '- Quel outil a-t-il été emprunté à un artiste par un autre artiste ?', 40, '');
insert into questions (defisid, questionnum , description , points, secret) values ('D145', 1, 'Combien de boucles sous son cou ?', 5, '');

--LesBlocsTexte
insert into blocstexte (indicesid, texte, defisid) values (1,'- Une amie t''a demandé de préparer un grand méchoui pour 120 personnes.
- On t''a dit qu''il y avait 4 moutons qui se courraient après autour de la maison du tourisme.
- 4 moutons juste dans un rayon de 100m.
- Rendez-vous à l''arrêt de bus "Hubert Dubedout - Maison du tourisme".
- Cherche le premier mouton.','D127');
insert into blocstexte (questionsid , texte, defisid) values (1,'- Ca commence bien. Ton premier mouton !','D127');
insert into blocstexte (indicesid, texte, defisid) values (2,'Un autre mouton te regarde, au loin. Sans bouger cherche le.','D127');
insert into blocstexte (questionsid, texte, defisid) values (2,'','D127');
insert into blocstexte (indicesid, texte, defisid) values (3,'- Maintenant regarde la vidéo de sheepest à l''oeuvre.
 - A la fin tu découvriras où est le troisième mouton...
 - https://www.youtube.com/watch?v=ZbA7aPTmlDY&t=207s','D127');
insert into blocstexte (indicesid, texte, defisid) values (4,'','D127');
insert into blocstexte (questionsid, texte, defisid) values (3,'- Zut ! Le mouton à disparu :-(','D127');
insert into blocstexte (questionsid, texte, defisid) values (4,'- Maintenant vas vers la poste la plus proche (très proche).
- Au dessus de la maison de la montagne tu trouveras un mouton montagnard.','D127');
insert into blocstexte (texte, defisid) values ('- Il y avait 4 moutons à l''origine. Un a disparu.
  - Si tu as besoin de faire un très grand méchoui tu peux aller chercher ailleurs.
  - Des moutons il y en plusieurs centaines à Grenoble partout.
  - Il suffit de les chercher...','D127');
insert into blocstexte (questionsid, texte, defisid) values (5,'- Vas du coté de l''oiseau tout plat.
  - Mais cherche aussi sur tes pas le renard tout plat.','D128');
insert into blocstexte (questionsid, texte, defisid) values (6,'- Prend la direction de la caserne.
  - Trouve un troupeau de moutons et tourne à droite.','D128');
insert into blocstexte (questionsid, texte, defisid) values (7,'- Ouvre l''application Graaly et choisi l''escape game "Street art" - bon jeu.','D128');
insert into blocstexte (indicesid, texte, defisid) values (5,'- Rendez vous à l''arrêt de bus "Grenoble - hôtel de ville".
- Passe par "l''orangerie".','D145');
insert into blocstexte (indicesid, texte, defisid) values (6,'- Les oranges te donneront de l''énergie.
- Mais surtout ne dépasse pas les bornes !
- Fatiguée ? T''as pas fait des bornes. Tu devrais avoir de l''énergie.
- Reste sur le parking pour te recharger les piles.','D145');
insert into blocstexte (indicesid, texte, defisid) values (7,'','D145');
insert into blocstexte (questionsid, texte, defisid) values (8,'- Cherche celui qui pourrait te donner matière à te protéger pendant l''hiver.
  - Il est là. Il te regarde de haut. Il t''observe.
  - Il bêle. "Je suis ceux que je suis. Et le vert, je le mange !"','D145');
insert into blocstexte (indicesid, texte, defisid) values (8,'Il tourne en rond pour le plaisir des petits. Cherche le.','D151');
insert into blocstexte (questionsid, texte, defisid) values (9,'- Si il n''y est pas, demande à quelqu''un où il est, quand il y est :-)
  - Cherche les deux moutons.. Cherche le.','D151');
insert into blocstexte (indicesid, texte, defisid) values (9,' - Arrivé.e à l''arrêt victor hugo ?
  - Peut être as tu besoin d''argent ? Mets toi du coté de la banque.
  - Un mouton te regarde ! Regarde le...','D189');
insert into blocstexte (questionsid, texte, defisid) values (10,' - Tu as vu le mouton ?','D189');
insert into blocstexte (questionsid, texte, defisid) values (11' - Suis le mouton comme font les moutons, tu veras des chamois !
  - Les chamoix sont en plein centre ville !','D189');
insert into blocstexte (questionsid, texte, defisid) values (12'  - Fait attention en regardant en haut.
  - Tu pourrais te faire écraser par des pachidermes.
  - Ils sont pas très loin.','D189');
insert into blocstexte (questionsid, texte, defisid) values (13'    - Tout à l''heure il y avait des écureils. Tu as vu ?','D189');








