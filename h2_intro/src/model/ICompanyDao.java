package model;

import java.util.List;

/**
 * Data Access Object Pattern or DAO pattern is used to separate low level
 * data accessing API or operations from high level business services.
 * Following are the participants in Data Access Object Pattern.
 *
 * Data Access Object Interface - This interface defines the standard
 * operations to be performed on a model object(s).
 *
 * Data Access Object concrete class - This class implements above interface.
 * This class is responsible to get data from a data source which can be
 * database / xml or any other storage mechanism.
 *
 * Model Object or Value Object - This object is simple POJO containing
 * get/set methods to store data retrieved using DAO class.
 */
public interface ICompanyDao {

    List<Company> getAllCompanies();
    Company getCompany(int id);
    void updateCompany(Company company);
    void deleteCompany(Company company);
    void insertCompany(Company company);
    void done();
}
