INSERT INTO bundesliga.liga
(bundesliga.liga.id, bundesliga.liga.name)
SELECT bundesliga_daten.liga.Liga_Nr, CONCAT(bundesliga_daten.liga.Liga_Nr, '. Bundesliga')
FROM bundesliga_daten.liga;

INSERT INTO bundesliga.verein
(bundesliga.verein.id, bundesliga.verein.name, bundesliga.verein.liga)
SELECT bundesliga_daten.verein.v_id, bundesliga_daten.verein.Name, bundesliga_daten.verein.liga
FROM bundesliga_daten.verein;

INSERT INTO bundesliga.spieler
(bundesliga.spieler.id, bundesliga.spieler.verein, bundesliga.spieler.name, bundesliga.spieler.trikotnummer, bundesliga.spieler.heimatland, bundesliga.spieler.tore)
SELECT bundesliga_daten.spieler.spieler_id, bundesliga_daten.spieler.vereins_id, bundesliga_daten.spieler.spieler_name, bundesliga_daten.spieler.trikot_nr, bundesliga_daten.spieler.land, bundesliga_daten.spieler.tore
FROM bundesliga_daten.spieler;

INSERT INTO bundesliga.spiel
(bundesliga.spiel.id, bundesliga.spiel.datum, bundesliga.spiel.uhrzeit, bundesliga.spiel.gastgeber, bundesliga.spiel.gast, bundesliga.spiel.gastgeber_tore, bundesliga.spiel.gast_tore, bundesliga.spiel.spieltag)
SELECT bundesliga_daten.spiel.spiel_id, bundesliga_daten.spiel.datum, bundesliga_daten.spiel.uhrzeit, bundesliga_daten.spiel.heim, bundesliga_daten.spiel.gast, bundesliga_daten.spiel.tore_heim, bundesliga_daten.spiel.tore_gast, bundesliga_daten.spiel.spieltag
FROM bundesliga_daten.spiel;
