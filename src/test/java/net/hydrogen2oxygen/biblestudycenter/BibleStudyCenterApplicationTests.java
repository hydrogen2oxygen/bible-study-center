package net.hydrogen2oxygen.biblestudycenter;

import net.hydrogen2oxygen.biblestudycenter.dao.BookRepository;
import net.hydrogen2oxygen.biblestudycenter.dao.TimelineRepository;
import net.hydrogen2oxygen.biblestudycenter.domain.Book;
import net.hydrogen2oxygen.biblestudycenter.domain.TimeObject;
import net.hydrogen2oxygen.biblestudycenter.domain.Timeline;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.TreeMap;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class BibleStudyCenterApplicationTests {

    @Autowired
    private TimelineRepository timelineRepository;

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void testBookPersistence() throws Exception {
        File booksFile = new File(new File("").getAbsolutePath() + "/src/main/resources/data/bibleBooks.txt");
        List<String> lines = FileUtils.readLines(booksFile, "UTF-8");

        TreeMap<String, Book> books = new TreeMap<String, Book>();

        for (String line : lines) {
            String parts[] = line.split(",");

            String bookName = parts[0];
            String chapter = parts[1];
            String verse = parts[2];

            if (books.get(bookName) == null) {
                Book book = new Book();
                book.setName(bookName);
                books.put(bookName, book);
            }

            Book book = books.get(bookName);

            bookRepository.save(book);
        }

        Iterable<Book> booksIterable = bookRepository.findAll();

        int count = 0;

        for (Book book : booksIterable) {
            System.out.println(book);
            count++;
        }

        Assert.assertEquals("The Holy Scriptures should have 66 books!", 66, count);
    }

    @Test
    public void testTimelineRest() {

        Timeline timeline = new Timeline();
        timeline.setName("Test");
        timeline = timelineRepository.save(timeline);

        TimeObject timeObject = new TimeObject();
        timeObject.setTimelineId(timeline.getId());
        timeObject.setName("Test Time");
        timeObject.setStart(Calendar.getInstance());
        timeObject.setEnd(Calendar.getInstance());
        timeline.getTimeObjectList().add(timeObject);

        timeline = timelineRepository.save(timeline);
        showAllTimelines();

        System.err.println("----------------------");
        Optional<Timeline> timeline1 = timelineRepository.findById(timeline.getId());
        showTimeLine(timeline1.get());
        Assert.assertTrue(timeline1.get().getTimeObjectList().size() > 0);
    }

    private void showAllTimelines() {
        for (Timeline t : timelineRepository.findAll()) {
            showTimeLine(t);
        }
    }

    private void showTimeLine(Timeline t) {
        System.out.println(t);

        for (TimeObject timeObject : t.getTimeObjectList()) {
            System.out.println(timeObject);
        }
    }

}

