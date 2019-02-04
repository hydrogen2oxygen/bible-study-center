package net.hydrogen2oxygen.biblestudycenter.dao;

import net.hydrogen2oxygen.biblestudycenter.domain.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {
}
