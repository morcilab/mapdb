package it.unibo.cs;

import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
import org.mapdb.Serializer;

import it.unibo.cs.model.Persona;

public class SerializzazionePersona {

	public static final String DB_FILE = "file2.db";
	public static final String PERSON_MAP = "personMap";

	public static void main(String[] args) {
		createMap();
		readMap();
	}

	private static void createMap() {
		// creo un riferimento al db (nota è lo stesso comando per creare il db)
		DB db = DBMaker.fileDB(DB_FILE).make();

		// creao un riferimento alla mappa nel db(nota è lo stesso comando per creare la
		// mappa)
		// il tipo HTreeMap è thread safe
		HTreeMap<String, Persona> map = db.hashMap(PERSON_MAP).keySerializer(Serializer.STRING)
				.valueSerializer(new SerializerPersona()).createOrOpen();

		// aggiungo delle persone alla mappa
		map.put("0", new Persona("Luigi", "Asprino"));
		map.put("1", new Persona("Davide Rossi", "Asprino"));

		// chiudo la connessione al db
		db.close();
	}
	
	private static void readMap() {
		// creo un riferimento al db (nota è lo stesso comando per creare il db)
		DB db = DBMaker.fileDB(DB_FILE).make();

		// creao un riferimento alla mappa nel db(nota è lo stesso comando per creare la
		// mappa)
		// il tipo HTreeMap è thread safe
		HTreeMap<String, Persona> map = db.hashMap(PERSON_MAP).keySerializer(Serializer.STRING)
				.valueSerializer(new SerializerPersona()).createOrOpen();

		// recupero delle persone dalla mappa
		System.out.println(map.get("0").getNome()+" "+map.get("0").getCognome());

		// chiudo la connessione al db
		db.close();
	}

}
