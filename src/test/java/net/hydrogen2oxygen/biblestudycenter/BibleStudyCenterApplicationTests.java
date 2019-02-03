package net.hydrogen2oxygen.biblestudycenter;

import net.hydrogen2oxygen.biblestudycenter.dao.TimelineRepository;
import net.hydrogen2oxygen.biblestudycenter.domain.TimeObject;
import net.hydrogen2oxygen.biblestudycenter.domain.Timeline;
import org.hibernate.Hibernate;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.context.WebServerApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Calendar;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class BibleStudyCenterApplicationTests {

	public static final String HTTP_LOCALHOST_8080_API_TIMELINE = "http://localhost:8080/api/timeline";
	private WebTestClient testClient;

	@Autowired
	private TimelineRepository timelineRepository;

	@Test
	public void testTimelineRest() {

		RouterFunction createTimeline = RouterFunctions.route(
				RequestPredicates.POST(HTTP_LOCALHOST_8080_API_TIMELINE),
				request -> ServerResponse.ok().build()
		);

		Timeline timeline = new Timeline();
		timeline.setName("Test");

		TimeObject timeObject = new TimeObject();
		timeObject.setName("Test Time");
		timeObject.setStart(Calendar.getInstance());
		timeObject.setEnd(Calendar.getInstance());
		timeline.getTimeObjectList().add(timeObject);

		timeline = timelineRepository.save(timeline);
		showAllTimelines();

		Assert.assertTrue(timeline.getTimeObjectList().size() > 0);

		timeline.setName("Test 2");
		timeline = timelineRepository.save(timeline);
		showAllTimelines();

		Assert.assertTrue(timeline.getTimeObjectList().size() > 0);

		timeline = timelineRepository.save(timeline);
		showAllTimelines();

		Assert.assertTrue(timeline.getTimeObjectList().size() > 0);

		System.err.println("----------------------");
		Optional<Timeline> timeline1 = timelineRepository.findById(timeline.getId());
		showTimeLine(timeline1.get());
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

