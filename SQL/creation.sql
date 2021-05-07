DROP TABLE IF exists public.indices cascade;
DROP TABLE IF exists public.questions cascade;
DROP TABLE IF exists public.typesdefis cascade;
DROP TABLE IF exists public.defis cascade;
DROP TABLE IF exists public.arrets cascade;
DROP TABLE IF exists public.chamis cascade;
DROP TABLE IF exists public.blocstexte cascade;
DROP TABLE IF exists public.motscles cascade;
DROP TABLE IF exists public.visites cascade;


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

CREATE TABLE defis (
	defisid varchar,
	titre varchar,
	nomtype varchar,
	dateCreation timestamp,
	dateModification timestamp,
	auteur varchar,
	codeArret varchar,
	points integer,
	duree real,
	prologue varchar,
	epilogue varchar,
	commentaire varchar,
	CONSTRAINT defis_pk PRIMARY KEY (defisid),
	CONSTRAINT defis_fk1 FOREIGN KEY (auteur) REFERENCES chamis(pseudo),
	CONSTRAINT defis_fk2 FOREIGN KEY (codeArret) REFERENCES arrets(codeArret)
);

CREATE TABLE questions (
	questionsid SERIAL,
	defisid varchar(4),
	questionnum integer,
	description varchar,
	points integer ,
	secret varchar ,
	CONSTRAINT questions_pk PRIMARY KEY (questionsid, defisid),
	CONSTRAINT questions_fk FOREIGN KEY (defisid) REFERENCES defis(defisid)
);

CREATE TABLE indices (
	indicesid SERIAL,
	defisid varchar(4) ,
	indicenum integer,
	description varchar ,
	points int4 ,
	CONSTRAINT indice_pk PRIMARY KEY (indicesid, defisid),
	CONSTRAINT indice_fk FOREIGN KEY (defisid) REFERENCES defis(defisid)
);

create table blocstexte (
	blocstexteid SERIAL,
	questionsid integer,
	indicesid integer,
	texte varchar,
	defisid varchar,
	CONSTRAINT blocstexte_pk PRIMARY KEY (blocstexteid),
	CONSTRAINT blocstexte_fk1 FOREIGN KEY (defisid) REFERENCES defis(defisid),
	CONSTRAINT blocstexte_fk2 FOREIGN KEY (questionsid, defisid) REFERENCES questions(questionsid, defisid),
	CONSTRAINT blocstexte_fk3 FOREIGN KEY (indicesid, defisid) REFERENCES indices(indicesid, defisid)
);

create table motscles (
	defisid varchar,
	motcle varchar,
	CONSTRAINT motscles_pk PRIMARY KEY (defisid, motcle),
	CONSTRAINT motscles_fk FOREIGN KEY (defisid) REFERENCES defis(defisid)
);

CREATE TABLE visites (
	visitesid VARCHAR,
	defisid VARCHAR,
	visiteur VARCHAR,
	datevisite TIMESTAMP,
	modeDP VARCHAR,
	score INTEGER,
	temps INTEGER,
	status VARCHAR,
	commentaire VARCHAR,
	CONSTRAINT visite_pk PRIMARY KEY (visitesid),
	CONSTRAINT visite_fk1 FOREIGN KEY (defisid) REFERENCES defis(defisid),
	CONSTRAINT visite_fk2 FOREIGN KEY (visiteur) REFERENCES chamis(pseudo)
);



