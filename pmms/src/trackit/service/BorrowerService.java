/*
 * JCatalog Project
 */
package trackit.service;

import java.util.List;
import trackit.businessobjects.Borrower;

/**
 * The user business service interface.
 * <p>
 * It contains all user management related business logic.
 *
 * @author <a href="mailto:derek_shen@hotmail.com">Derek Y. Shen</a>
 */
public interface BorrowerService {
    public Borrower saveBorrower(Borrower accnt);
    public Borrower findBorrower(String id);
    public Object findBorrowerByName(String name);

    public List<Borrower> getAllBorrowersForAccount(String accountId);
}
