-- Utilisateurs

INSERT INTO utilisateur(nom,prenom,login,password)
VALUES ('Durand', 'Jean', 'jeand', 'jeand');

INSERT INTO utilisateur(nom,prenom,login,password)
VALUES ('Bilou', 'Loulou', 'louloub', 'louloub');

INSERT INTO utilisateur(nom,prenom,login,password)
VALUES ('Toto', 'Jeanot', 'jeanott', 'jeanott');

-- Roles

INSERT INTO candidat(id,addresse,telephone)
VALUES ('1', 'De champêtre à Grenoble', '0640521364');

INSERT INTO service_rh(id,localisation)
VALUES ('2', 'Marseille');

INSERT INTO directeur(id,localisation,service)
VALUES ('3', 'Lisbonne', 'Paye');

-- Objets

INSERT INTO candidature(date_creation, cv, lettre_motivation, id_candidat)
VALUES ('2014-08-25', 'Bonjour,Voici mon CV', 'Et Ici ma lettre de motiv', 1);