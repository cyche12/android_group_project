package algonquin.cst2335.android_group_project.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import algonquin.cst2335.android_group_project.Entity.Definition;
import algonquin.cst2335.android_group_project.Entity.SearchTerm;
/**
 * File Purpose: Interface for database operations related to the dictionary feature.
 * This includes operations like inserting and retrieving search terms and definitions.
 * Author: Piyalee Mangaraj
 * Lab Section: CST2335 012
 * Creation Date: 1st April 2024
 */
@Dao
public interface DictionaryDao {
    /**
     * Inserts a new search term into the database.
     *
     * @param searchTerm The search term to insert.
     * @return The row ID of the newly inserted search term.
     */
    @Insert
    long insertSearchTerm(SearchTerm searchTerm);
    /**
     * Inserts a new definition associated with a search term into the database.
     *
     * @param definition The definition to insert.
     */
    @Insert
    void insertDefinition(Definition definition);
    /**
     * Retrieves all search terms stored in the database.
     *
     * @return A list of all search terms.
     */
    @Query("SELECT * FROM search_terms")
    List<SearchTerm> getAllSearchTerms();
    /**
     * Retrieves a search term by its unique ID.
     *
     * @param id The ID of the search term to retrieve.
     * @return The search term with the specified ID, or null if not found.
     */
    @Query("SELECT * FROM search_terms WHERE id = :id")
    SearchTerm getSearchTermById(int id);
    /**
     * Retrieves all definitions associated with a specific search term ID.
     *
     * @param searchTermId The ID of the search term whose definitions are to be retrieved.
     * @return A list of definitions associated with the specified search term ID.
     */
    @Query("SELECT * FROM definitions WHERE searchTermId = :searchTermId")
    List<Definition> getDefinitionsForSearchTerm(int searchTermId);
    /**
     * Deletes a specific definition from the database.
     *
     * @param definition The definition to delete.
     */
    @Delete
    void deleteDefinition(Definition definition);
}

