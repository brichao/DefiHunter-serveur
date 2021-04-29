DROP TABLE public.indices cascade;
DROP TABLE public.questions cascade;
DROP TABLE public.motsCles cascade;
DROP TABLE public.defiscles cascade;
DROP TABLE public.typesdefis cascade;
DROP TABLE public.defis cascade;
DROP TABLE public.arrets cascade;
DROP TABLE public.chamis cascade;


create table chamis (
	pseudo varchar,
	email varchar,
	age integer,
	ville varchar,
	description varchar,
	CONSTRAINT chamis_pkey PRIMARY KEY (pseudo)
);

create table arrets (
	codeArret varchar,
	nomArret varchar,
	streetmap varchar,
	CONSTRAINT arret_pk PRIMARY KEY (codeArret)
);

CREATE TABLE typesdefis (
	idType SERIAL,
	nomType varchar,
	CONSTRAINT types_pk PRIMARY KEY (idType)
);

CREATE TABLE defis (
	id varchar,
	titre varchar,
	idType integer,
	datedeCreation timestamp,
	datedeModification timestamp,
	auteur varchar,
	codeArret varchar,
	points integer,
	duree real,
	prologue varchar,
	epilogue varchar,
	commentaire varchar,
	CONSTRAINT defis_pk PRIMARY KEY (id),
	CONSTRAINT defis_fk1 FOREIGN KEY (auteur) REFERENCES chamis(pseudo),
	CONSTRAINT defis_fk2 FOREIGN KEY (codeArret) REFERENCES arrets(codeArret),
	CONSTRAINT defis_fk3 FOREIGN KEY (idType) REFERENCES typesdefis(idType)
);

create table motscles (
	idmotscles SERIAL,
	motcle varchar, 
	CONSTRAINT motscles_pk PRIMARY KEY (idmotscles)
);

create table defiscles (
	idmotscles integer,
	idDefis varchar, 
	CONSTRAINT defiscles_pk PRIMARY KEY (idmotscles, idDefis),
	CONSTRAINT defiscles_fk1 FOREIGN KEY (idmotscles) REFERENCES motscles(idmotscles),
	CONSTRAINT defiscles_fk2 FOREIGN KEY (idDefis) REFERENCES defis(id)
);

CREATE TABLE questions (
	labelQ varchar(3),
	idDefis varchar(4) ,
	description varchar,
	points int4 ,
	secret varchar ,
	CONSTRAINT questions_pk PRIMARY KEY (labelQ, idDefis),
	CONSTRAINT questions_fk FOREIGN KEY (idDefis) REFERENCES defis(id)
);

CREATE TABLE indices (
	labelI varchar(3),
	idDefis varchar(4) ,
	description varchar ,
	points int4 ,
	CONSTRAINT indice_pk PRIMARY KEY (labelI, idDefis),
	CONSTRAINT indice_fk FOREIGN KEY (idDefis) REFERENCES defis(id)
);

