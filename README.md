DBSProjekt2014
==============


Java Version:  
PostgreSQL Version:

Termin| Datum
------|------
1. Iteration| TBA
2. Iteration| TBA
3. Iteration| TBA
4. Iteration| TBA

1. Anwendungsbeschreibung  
Es soll eine Datenbank fuer die Fußball Bundesliga realisiert werden. Die Datenbank speichert Vereine, Spiele, Spieler, und Ligen.
Eine Anwendung stellt vergangene Fußballergebnisse bereit. Spieler sind Vereinen zugeordnet. Vereine sind Ligen zugeordnet. Spiele finden immer zwischen einem Gastgeber und einem Gast statt.
Weiterhin wird die Datenbank fuer eine Data Mining Anwendung zur Ergebnisprognose genutzt.
Die folgenden Anfragen sollen durch die Anwendung beantwortet werden koennen:
	- An welchem Tag fand das erste Spiel in dieser Saison statt?
	- Welche Spieler haben in dieser Saison bereits mehr als fuenf Tore geschossen?
	- Zeige die Daten aller Spiele an, die am ersten Spieltag aller drei Ligen nach 17 Uhr begonnen haben.
	- Welche Spieler spielen fuer den Verein “FC Bayern Muenchen“? Gib auch die Trikotnummer und das Heimatland jedes Spielers sowie die Anzahl seiner Tore mit aus. Ordne die Ergebnisse aufsteigend nach der Trikotnummer.
	- Wie viele Spiele hat „Hannover 96“ bis heute gewonnen?
	- Gesucht sind Vereinsname, Spieler_ID, Trikotnummer und Name aller Spieler, die fuer den Verein spielen, der in dieser Saison die meisten Niederlagen erlitten hat (auch mehrere Vereine mit gleicher Anzahl moeglich).

2. Technologien  
Das Projekt wird unter ausschließlicher Verwendung von Java, JDBC und PostgreSQL durchgefuehrt (fuer die Web Oberflaeche JSP).
Andere relationale Datenbanken sowie die Verwendung von Frameworks wie Hibernate oder Oberflaechen wie AWT/Swing sind ausgeschlossen.
3. Teilaufgaben
	1. Voraussetzung:
		- Installation von PostgreSQL als Server.
		- Installation des Java JDK.
	2. Anlegen der Datenbank  
		- Legen Sie eine Datenbank mit dem Namen „bundesliga“ an.  
	3. Modellierung  
		- Entwerfen Sie auf Grundlage der Anwendungsbeschreibung und den Daten ein Datenbankschema in, aus der Vorlesung bekannten umgekehrten Chen-Notation mit (min, max) Erweiterung.
	4. Uebersetzen ins relationale Modell und SQL
		- Schreiben Sie die entsprechenden SQL-Queries zur Erstellung der Tabellen. Achten Sie auf eine gute Wahl von Attributeigenschaften wie NOT NULL, UNIQUE und Schluesseln.
	5. Datenimport
		- Importieren Sie die notwendigen Daten. Definieren Sie Transformationsprozesse zur Umwandlung der importierten Daten in das erstellte Relationenmodell: Definieren Sie einen Transformationsprozess ausschließlich unter Verwendung von SQL Befehlen.
		- Definieren Sie einen weiteren Transformationsprozess, in welchem Sie alle Umformungen in Java realisieren und nur durch SELECT und INSERT Statements auf die Datenbank zugreifen (nicht in Kombination :) ).
		- Die Daten koennen der Internetseite http://dbup2date.uni-bayreuth.de/bundesliga.html entnommen werden. Dort stehen CSV aber auch SQL Imports zur Verfuegung.
		- Es lohnt sich, sich naeher mit den Importfunktionen von Postgres zu beschaeftigen. So dauert ein Import z.B. sehr lange, wenn einzelne Statements ausgefuehrt werden oder Integritaetsbedingungen waehrend des Imports geprueft werden. Dies sollte deshalb erst nach dem Import erfolgen.
	6. Data Mining
		- Es soll ein Klassifikator gelernt werden, welcher ein kommendes Spielergebnis prognostiziert. Dazu muessen zunaechst Features generiert werden. Schreiben Sie eine Anwendung (Java), welche die folgenden Features generiert:
		- Tore der letzten 3 Spiele
		- Gegentore der letzten drei Spiele
		- Anzahl Niederlagen der letzten 5 Spielen
		- durchschnittliche Steigung der Tore der letzten 5 Spiele
		- Denken Sie sich mindestens 3 weitere Features aus und realisieren Sie diese
		- Nutzen Sie das Data Mining Werkzeug weka und waehlen Sie einen
		Klassifkationsalgorithmus, z.B. Naive Bayes. Trainieren Sie Ihren Klassifikator.
4. Weiteres
	1. Dokumentation  
Dokumentieren Sie im Verlauf des Projektes alle wichtigen Designentscheidungen und den Quellcode, so dass Sie bei der abschließenden Praesentation nachvollziehen koennen wie der Code funktioniert, was an dieser Stelle geschieht und welche Entscheidungen hier eventuell getroffen wurden. Benutzen Sie hierfuer fuer den Java-Code JavaDoc.
	2. Praesentation  
Bewertungsgrundlage sind die Zwischenpräsentationen in Anwesenheit eines Tutors.