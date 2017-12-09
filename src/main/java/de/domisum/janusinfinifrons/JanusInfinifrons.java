package de.domisum.janusinfinifrons;

import de.domisum.janusinfinifrons.component.ComponentSerializer;
import de.domisum.janusinfinifrons.component.JanusComponent;
import de.domisum.janusinfinifrons.credential.Credential;
import de.domisum.janusinfinifrons.credential.CredentialSerializer;
import de.domisum.janusinfinifrons.storage.InMemoryStorage;
import de.domisum.janusinfinifrons.storage.ondisk.ObjectOnDiskStorage;

import java.io.File;

public class JanusInfinifrons
{

	// CONSTANTS
	private static final File CREDENTIALS_DIR = new File("credentials");
	private static final File COMPONENTS_DIR = new File("components");

	// STORAGE
	private InMemoryStorage<Credential> credentialStorage;
	private InMemoryStorage<JanusComponent> componentStorage;


	// INIT
	public static void main(String[] args)
	{
		new JanusInfinifrons();
	}

	private JanusInfinifrons()
	{
		initStorage();
	}


	// STORAGE
	private void initStorage()
	{
		credentialStorage = new InMemoryStorage<>(
				new ObjectOnDiskStorage<>(new CredentialSerializer(), CREDENTIALS_DIR, "jns_cred"));
		credentialStorage.loadFromSource();

		componentStorage = new InMemoryStorage<>(
				new ObjectOnDiskStorage<>(new ComponentSerializer(), COMPONENTS_DIR, "jns_comp"));
		componentStorage.loadFromSource();
	}

}
