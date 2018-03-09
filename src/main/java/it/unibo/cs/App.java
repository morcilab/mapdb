package it.unibo.cs;

import java.util.NavigableSet;
import java.util.concurrent.ConcurrentMap;

import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.Serializer;

public class App {

	public static final String DB_FILE = "file1.db";
	public static final String SIMPLE_MAP = "simpleMap";
	public static final String SIMPLE_SET = "simpleSet";

	public static void main(String[] args) {

		boolean onlyRead = false;

		if (args.length > 0) {
			onlyRead = Boolean.parseBoolean(args[0]);
		}

		if (!onlyRead) {
			createMap();
			createSet();
		}

		readMap();
		readSet();

	}

	private static void createMap() {
		// creo il db
		DB db = DBMaker.fileDB(DB_FILE).make();

		// creo una semplice mappa String -> String nel db
		ConcurrentMap<String, String> map = db.hashMap(SIMPLE_MAP, Serializer.STRING, Serializer.STRING).createOrOpen();

		// aggiungo gli elementi alla mappa
		map.put("key", "value");
		map.put("key1", "value1");

		// persisto la mappa su file system e chiudo la connessione al db
		db.close();
	}

	private static void readMap() {

		// creo un riferimento al db (nota è lo stesso comando per creare il db)
		DB db = DBMaker.fileDB(DB_FILE).make();

		// creao un riferimento alla mappa nel db(nota è lo stesso comando per creare la mappa)
		ConcurrentMap<String, String> map = db.hashMap(SIMPLE_MAP, Serializer.STRING, Serializer.STRING).createOrOpen();

		// recupero gli elemetni aggiunti nel db
		System.out.println("Elemento associato alla chiave key: " + map.getOrDefault("key", "not found"));
		System.out.println("Elemento associato alla chiave key1: " + map.getOrDefault("key1", "not found"));
		System.out.println("Elemento associato alla chiave key2: " + map.getOrDefault("key2", "not found"));

		// chiudo la connessione al db
		db.close();
	}

	private static void createSet() {

		// creo il db
		DB db = DBMaker.fileDB(DB_FILE).make();

		// Creo un insieme nel db
		NavigableSet<String> treeSet = db.treeSet(SIMPLE_SET).serializer(Serializer.STRING).createOrOpen();

		// Aggiungo degli elementi
		treeSet.add("Elemento 1");
		treeSet.add("Elemento 2");
		treeSet.add("Elemento 3");

		// Persisto il db sul file system
		db.close();
	}

	private static void readSet() {

		// creo un riferimento al db (è lo stesso comando per creare il db)
		DB db = DBMaker.fileDB(DB_FILE).make();

		// cremo un riferimento all'insieme di stringhe creato precedemente
		NavigableSet<String> treeSet = db.treeSet(SIMPLE_SET).serializer(Serializer.STRING).createOrOpen();

		System.out.println("Elementi contenuti nell'insieme:");
		for (String element : treeSet) {
			System.out.println(element);
		}

		db.close();
	}

}
