<%@page import="eu.telecom_bretagne.recrutement.service.IServiceCandidats"%>
<%@page import="eu.telecom_bretagne.recrutement.service.IServiceCommon"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@page import="java.util.List" %>  

<%@page import="eu.telecom_bretagne.recrutement.front.GestionService" %>

<%@page import="eu.telecom_bretagne.recrutement.service.IServiceRH" %>
<%@page import="eu.telecom_bretagne.recrutement.data.model.Candidature" %>
<%@page import="eu.telecom_bretagne.recrutement.data.model.Candidat"%>
<%@page import="eu.telecom_bretagne.recrutement.data.model.Utilisateur"%>

<%
IServiceRH serviceRH = GestionService.getServiceRH();
IServiceCommon serviceCommon = GestionService.getServiceCommon();
IServiceCandidats serviceCandidats = GestionService.getServiceCandidats();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Voici une page de démo WEB</title>
</head>
<body>
On affiche ici tous les candidats<br />
<%
List <Candidat> candidats = serviceCommon.getListCandidats();

for(Candidat candidat : candidats) {
%>
Nom du candidat : <%= candidat.getUtilisateur().getNom() %><br />
Prenom du candidat : <%=candidat.getUtilisateur().getPrenom() %><br /><br />

<%
} 
%>
On affiche ici l'ensemble des infos sur les candidatures : <br />
<%
List <Candidature> candidatures = candidats.get(0).getCandidatures();

if(candidatures.isEmpty()) {
	out.print("Il n'y a aucune candidature dans la base de données");
}

for (Candidature candidature : candidatures) {
	Candidat candidat = candidature.getCandidat();
	Utilisateur utilisateur = candidat.getUtilisateur();
%>
Candidature n°<%= candidature.getId()%><br />
Nom : <%= utilisateur.getNom()%><br />
Prenom : <%= utilisateur.getPrenom()%><br />
CV : <br /> <%= candidature.getCv() %><br />
Lettre de motivation : <br /> <%= candidature.getLettreMotivation() %><br /><br/>
<%
}
%>
</body>
</html>