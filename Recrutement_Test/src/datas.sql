INSERT INTO utilisateur(nom,prenom,login,password)
VALUES ('Durand', 'Jean', 'jeand', 'jeand');

INSERT INTO utilisateur(nom,prenom,login,password)
VALUES ('Bilou', 'Loulou', 'louloub', 'louloub');

INSERT INTO utilisateur(nom,prenom,login,password)
VALUES ('Toto', 'Jeanot', 'jeanott', 'jeanott');

INSERT INTO utilisateur(nom,prenom,login,password)
VALUES ('Candidat', 'Toto', 'totoc', 'totoc');

INSERT INTO candidat(id,addresse,telephone)
VALUES ('1', 'De champêtre à Grenoble', '0640521364');

INSERT INTO service_rh(id,localisation)
VALUES ('2', 'Marseille');

INSERT INTO directeur(id,localisation,service)
VALUES ('3', 'Lisbonne', 'Paye');

INSERT INTO candidat(id,addresse,telephone)
VALUES ('4', 'Ailleurs', '0231658945');

INSERT INTO candidature(date_creation, cv, lettre_motivation, etat, id_candidat)
VALUES ('2014-08-25', 'Bonjour,Voici mon CV', 'Et Ici ma lettre de motiv', 'cree', 1);

INSERT INTO candidature(date_creation, cv, lettre_motivation, etat, id_candidat)
VALUES ('2014-08-25', 'Bonjour,Voici mon CV 2', 'Et Ici ma lettre de motiv 2', 'valide', 1);

INSERT INTO candidature(date_creation, cv, lettre_motivation, etat, id_candidat)
VALUES ('2014-08-25', 'Bonjour,Voici mon CV 3', 'Et Ici ma lettre de motiv 3', 'valide', 4);

INSERT INTO candidature(date_creation, cv, lettre_motivation, etat, id_candidat)
VALUES ('2014-08-25', 'Bonjour,Voici mon CV 4', 'Et Ici ma lettre de motiv 4', 'valide', 4);

INSERT INTO candidature(date_creation, cv, lettre_motivation, etat, id_candidat)
VALUES ('2014-08-25', 'Bonjour,Voici mon CV 5', 'Et Ici ma lettre de motiv 5', 'valide', 1);

INSERT INTO comite_entretien(membres)
VALUES('');

INSERT INTO utilisateur_comite_entretien(id_utilisateur,id_comite_entretien)
VALUES(2, 1);

INSERT INTO utilisateur_comite_entretien(id_utilisateur,id_comite_entretien)
VALUES(3, 1);

INSERT INTO entretien(date_entretien, etat, id_candidature, id_comite_entretien)
VALUES ('2014-08-30', 'cree', 2, 1);

INSERT INTO entretien(date_entretien, etat, id_candidature, id_comite_entretien)
VALUES ('2014-08-30', 'valide', 3, 1);

INSERT INTO entretien(date_entretien, etat, id_candidature, id_comite_entretien)
VALUES ('2014-08-30', 'valide', 5, 1);

INSERT INTO vote(id, note, commentaires)
VALUES (3, 15, 'Un super commentaire')