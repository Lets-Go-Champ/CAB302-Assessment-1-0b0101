package com.example.cab302assessment10b0101.model;

import java.util.List;

/**
 * The CollectionDAOInterface defines the contract for data access operations
 * related to Collection objects. It specifies methods for inserting new
 * collections, retrieving all collections, retrieving collections by user,
 * and getting the collection ID by user and collection name.
 */
public interface CollectionDAOInterface {

    /**
     * Inserts a new collection into the data store.
     *
     * @param collection The Collection object to be inserted.
     */
    void insert(Collection collection);

    /**
     * Retrieves all collections from the data store.
     *
     * @return A list of all Collection objects.
     */
    List<Collection> getAll();

    /**
     * Retrieves all collections for a specific user from the data store.
     *
     * @param user The user whose collections to retrieve.
     * @return A list of Collection objects owned by the specified user.
     */
    List<Collection> getCollectionsByUser(User user);

    /**
     * Retrieves the collection ID by user and collection name.
     *
     * @param user The user who owns the collection.
     * @param collectionName The name of the collection.
     * @return The collection ID if found, otherwise -1.
     */
    int getCollectionsIDByUserAndCollectionName(User user, String collectionName);

}
