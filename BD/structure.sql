CREATE TABLE utilisateur(
  id			serial	PRIMARY KEY,
  nom			text,
  prenom		text,
  login			text,
  password		text
);

CREATE TABLE candidat(
  id			serial PRIMARY KEY references utilisateur(id),
  addresse		text,
  telephone		text
);

CREATE TABLE candidature(
  id			serial	PRIMARY KEY,
  date_creation		timestamp,
  cv			text,
  lettre_motivation	text,
  etat			text,
  id_candidat		serial references candidat(id)
);

CREATE TABLE service_rh(
  id			serial PRIMARY KEY references utilisateur(id),
  localisation		text
);

CREATE TABLE directeur(
  id			serial PRIMARY KEY references utilisateur(id),
  localisation		text,
  service		text
  );

CREATE TABLE comite_entretien(
  id			serial PRIMARY KEY,
  membres		text
);

CREATE TABLE entretien(
  id			serial	PRIMARY KEY,
  date_entretien	timestamp,
  etat			text,
  id_candidature	serial	references candidature(id),
  id_comite_entretien	serial	references comite_entretien(id)
);

CREATE TABLE vote(
  id			serial	PRIMARY KEY references entretien(id),
  note			integer,
  commentaires		text
);

CREATE TABLE message(
  id			serial	PRIMARY KEY,
  date_creation		timestamp,
  sujet			text,
  contenu		text,
  id_candidat		serial	references candidat(id)
);

-- Association table

CREATE TABLE utilisateur_comite_entretien(
  id_utilisateur	serial	references utilisateur(id),
  id_comite_entretien	serial	references comite_entretien(id),
  PRIMARY KEY(id_utilisateur,id_comite_entretien)
);




