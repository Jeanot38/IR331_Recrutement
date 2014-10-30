

-- Roles defined

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
) INHERITS (utilisateur);

CREATE TABLE service_rh(
  id			serial PRIMARY KEY references utilisateur(id),
  localisation		text
) INHERITS (utilisateur);

CREATE TABLE directeur(
  id			serial PRIMARY KEY references utilisateur(id),
  localisation		text,
  service		text
  ) INHERITS (utilisateur);

CREATE TABLE comite_entretien(
  id			serial PRIMARY KEY,
  membres		integer	references utilisateur(id)
);

-- Object cores

CREATE TABLE candidature(
  id			serial	PRIMARY KEY,
  date_creation		date,
  cv			text,
  lettre_motivation	text
);

CREATE TABLE vote(
  id			serial	PRIMARY KEY,
  note			integer,
  commentaires		text
);

CREATE TABLE entretien(
  id			serial	PRIMARY KEY,
  date_entretien	date,
  id_vote		integer	references vote(id)
);

CREATE TABLE message(
  id			serial	PRIMARY KEY,
  date_creation		date,
  sujet			text,
  contenu		text
);

-- Association table

CREATE TABLE candidat_candidature(
  id_candidat		integer	references candidat(id),
  id_candidature	integer	references candidature(id) UNIQUE
);

CREATE TABLE candidat_message(
  id_candidat		integer	references candidat(id),
  id_message		integer	references message(id) UNIQUE
);

CREATE TABLE candidature_entretien(
  id_candidature	integer references candidature(id),
  id_entretien		integer	references entretien(id) UNIQUE
);

CREATE TABLE comite_entretien_entretien(
  id_comite_entretien	integer references comite_entretien(id),
  id_entretien		integer references entretien(id) UNIQUE
);




