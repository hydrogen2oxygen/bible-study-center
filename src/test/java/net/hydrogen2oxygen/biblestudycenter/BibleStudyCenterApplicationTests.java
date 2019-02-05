package net.hydrogen2oxygen.biblestudycenter;

import net.hydrogen2oxygen.biblestudycenter.dao.BookRepository;
import net.hydrogen2oxygen.biblestudycenter.dao.TimelineRepository;
import net.hydrogen2oxygen.biblestudycenter.domain.*;
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
            String chapterValue = parts[1];
            String verseValue = parts[2];

            if (books.get(bookName) == null) {
                Book book = new Book();
                book.setName(bookName);
                books.put(bookName, bookRepository.save(book));
            }

            Book book = books.get(bookName);

            int chapterNumber = Integer.parseInt(removeLeadingZeros(chapterValue));

            if (book.getChapters().size() < chapterNumber) {

                Chapter chapter = new Chapter();
                chapter.setBookId(book.getId());
                chapter.setNumber(chapterNumber);
                book.getChapters().add(chapter);
                book = bookRepository.save(book);
                books.put(bookName, book);
            }

            int verseNumber = Integer.parseInt(removeLeadingZeros(verseValue));

            if (book.getChapters().get(chapterNumber - 1).getVerses().size() < verseNumber) {

                Verse verse = new Verse();
                verse.setChapterId(book.getChapters().get(chapterNumber - 1).getId());
                verse.setNumber(verseNumber);
                book.getChapters().get(chapterNumber - 1).getVerses().add(verse);
                book = bookRepository.save(book);
                books.put(bookName, book);
            }
        }

        Iterable<Book> booksIterable = bookRepository.findAll();

        int count = 0;

        for (Book book : booksIterable) {
            System.out.println(book.getName() + " - " + book.getChapters().size() + ", " + determineVerseNumbersInBook(book));
            count++;
        }

        Assert.assertEquals("The Holy Scriptures should have 66 books!", 66, count);

    }

    private int determineVerseNumbersInBook(Book book) {

        int count = 0;

        for (Chapter chapter : book.getChapters()) {
            count += chapter.getVerses().size();
        }

        return count;
    }

    private String removeLeadingZeros(String text) {

        while (text.startsWith("0")) {
            text = text.substring(1);
        }

        return text;
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

