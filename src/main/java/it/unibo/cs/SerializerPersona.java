package it.unibo.cs;

import java.io.IOException;
import java.io.Serializable;

import org.mapdb.DataInput2;
import org.mapdb.DataOutput2;
import org.mapdb.Serializer;

import it.unibo.cs.model.Persona;

public class SerializerPersona implements Serializer<Persona>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void serialize(DataOutput2 out, Persona value) throws IOException {
		out.writeUTF(value.getNome());
		out.writeUTF(value.getCognome());
		
	}

	@Override
	public Persona deserialize(DataInput2 input, int available) throws IOException {
		return new Persona(input.readUTF(),input.readUTF());
	}
}
