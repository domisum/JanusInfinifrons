package de.domisum.janusinfinifrons.storage.ondisk;

import de.domisum.janusinfinifrons.storage.Storage;
import de.domisum.janusinfinifrons.storage.ToStringSerializer;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ObjectOnDiskStorage<StorageItemT> implements Storage<StorageItemT>
{

	// REFERENCES
	private ToStringSerializer<StorageItemT> serializer;
	private Storage<String> stringStorage;


	// INIT
	public ObjectOnDiskStorage(ToStringSerializer<StorageItemT> serializer, File objectDirectory, String fileExtension)
	{
		this.serializer = serializer;
		stringStorage = new StringOnDiskStorage(objectDirectory, fileExtension);
	}


	// STORAGE
	@Override public StorageItemT fetch(String id)
	{
		return deserialize(stringStorage.fetch(id));
	}

	@Override public Collection<StorageItemT> fetchAll()
	{
		List<StorageItemT> items = new ArrayList<>();
		for(String cs : stringStorage.fetchAll())
			items.add(deserialize(cs));

		return items;
	}

	@Override public void store(StorageItemT item)
	{
		throw new UnsupportedOperationException();
	}

	@Override public boolean contains(String id)
	{
		throw new UnsupportedOperationException();
	}

	@Override public void remove(String id)
	{
		throw new UnsupportedOperationException();
	}


	// SERIALIZATION
	private StorageItemT deserialize(String objectString)
	{
		return serializer.deserialize(objectString);
	}

}
